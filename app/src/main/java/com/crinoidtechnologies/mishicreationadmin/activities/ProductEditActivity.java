package com.crinoidtechnologies.mishicreationadmin.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.CurrentSession;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllCategoryFragment;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProductsFragment;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.Category;
import com.crinoidtechnologies.mishicreationadmin.models.Image;
import com.crinoidtechnologies.mishicreationadmin.models.InsertProductData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class ProductEditActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "ProductEditActivity";
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView ivProductEditImage;
    private AlertDialog.Builder pictureDialog;
    private TextView tvProductEditTotalPrice;
    private Button bChangeProductImage,bSaveProduct;
    private String totalPrice ,changeName,changePrice,changeSalePrice,changProductImage;
    private String  selectedCategory=" ";
    private String createNewProduct = " ";
    private String dowanloadImageUri;
    private int productImage;
    private Uri filePath;
    private Toolbar topToolBar;
    private ActionBar actionBar;
    private ProgressDialog pd;
    private EditText  etProductTitle, etProductPrice, etProductSalePrice;
    private Spinner categoryListSpinner;
    private ArrayAdapter<String> arrayCategoryListadapter;
    private ArrayList<AllCategoryDatum> allCategoryDatumArrayList=new ArrayList<>(  );
    private ArrayList<String> arrayListCategory=new ArrayList<>(  );
    private ArrayList<Integer> arrayListCategoryId=new ArrayList<>(  );
    public ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    private  int getCategoryId=0;
    private  int getProductCategoryId;
    private int getProductId;
    ArrayList<Category> categoryList;
    ArrayList<Image> imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_edit );
        initViews();
        initData();
        addCategoryList();
        setCategoryListSpinner();

    }
    public void addCategoryList()
    {
        for(int i=0; i<CurrentSession.getCI().getLocalData().getCategoryList().size(); i++)
        {
            arrayListCategory.add(  CurrentSession.getCI().getLocalData().getCategoryList().get( i ).getName());
            arrayListCategoryId.add( CurrentSession.getCI().getLocalData().getCategoryList().get( i ).getId() );
            Log.d( TAG, "addCategoryList: "+CurrentSession.getCI().getLocalData().getCategoryList().get( i )
            .getId());

        }

        Log.d( TAG, "addCategoryList: "+arrayListCategory );

    }



    private void setCategoryListSpinner() {

        arrayCategoryListadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayListCategory );
        arrayCategoryListadapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        categoryListSpinner.setAdapter( arrayCategoryListadapter );
//        arrayCategoryListadapter.notifyDataSetChanged();
        categoryListSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCategory= (String) parent.getItemAtPosition( position );
                getCategoryId=   arrayListCategoryId.get( position );
                Log.d( TAG, "onItemSelected: "+selectedCategory );
                Log.d( TAG, "onItemSelected: "+getCategoryId );


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }

    private void initData() {
        Intent intent = getIntent();
        totalPrice = intent.getExtras().getString( Constants.TOTAL_PRICE );
        productImage = intent.getExtras().getInt( Constants.IMAGE );
        ivProductEditImage.setImageResource( productImage );
        tvProductEditTotalPrice.setText( totalPrice );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d( TAG, "initViews: if" );
            changeName=bundle.getString( Constants.PRODUCT_NAME );
            changePrice=bundle.getString( Constants.TOTAL_PRICE );
            changeSalePrice=bundle.getString( Constants.SALE_PRICE );
            changProductImage=bundle.getString( Constants.IMAGE );
            getProductCategoryId=bundle.getInt( Constants.PRODUCT_CATEGORY_ID );
            getProductId=bundle.getInt( Constants.PRODUCT_ID );

            etProductTitle.setText( changeName );
            etProductPrice.setText( changePrice );
            etProductSalePrice.setText(changeSalePrice  );
            Picasso.with( getApplicationContext() ).load( changProductImage ).into( ivProductEditImage );


            Log.d( TAG, "initViews: all edit data" + "product id"+getProductId +" productName"+changeName +"price"+changePrice +" sale price"+changeSalePrice +" product image"+changProductImage +" Category Id "+getProductCategoryId);

        } else {
            Log.d( TAG, "initViews: else" );
        }
    }

    private void initViews() {

        progressDialog = new ProgressDialog( this );
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        categoryList = new ArrayList<>();
        imageList = new ArrayList<>();
        categoryListSpinner=findViewById( R.id.spinner_products );
        topToolBar = findViewById( R.id.toolbar );
        etProductTitle = findViewById( R.id.et_product_name );
        etProductPrice = findViewById( R.id.et_product_price );
        etProductSalePrice = findViewById( R.id.et_product_sale_price );


        bChangeProductImage = findViewById( R.id.b_change_product_image );
        bSaveProduct=findViewById( R.id.b_product_save );
        ivProductEditImage = findViewById( R.id.iv_product_image );
        tvProductEditTotalPrice = findViewById( R.id.et_product_price );

        Bundle bundle = getIntent().getExtras();
        createNewProduct = bundle.getString( Constants.CREATE_NEW_PRODUCT );
        Log.d( TAG, "initViews: create new product" + createNewProduct );
        if (createNewProduct != null) {

            if (createNewProduct.equals( Constants.CREATE_NEW_PRODUCT )) {
                Log.d( TAG, "initViews: if" );
                topToolBar.setTitle( R.string.create_new_product );
                bChangeProductImage.setText( R.string.upload );

            }
        } else {
            Log.d( TAG, "initViews: else" );
            topToolBar.setTitle( R.string.product_edit );
            bChangeProductImage.setText( R.string.change );

        }


        setSupportActionBar( topToolBar );
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        bChangeProductImage.setOnClickListener( this );
        bSaveProduct.setOnClickListener( this );


    }


    private void openGallaryAndCamera() {
        pictureDialog = new AlertDialog.Builder( this );
        pictureDialog.setTitle( R.string.select_action );
        pictureDialog.setItems( Constants.pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                    case 3:
                        cancelDialog();
                        break;
                }

            }
        } );
        pictureDialog.show();
    }

    private void cancelDialog() {
        pictureDialog.setCancelable( true );
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        if (intent.resolveActivity( getPackageManager() ) != null) {
            startActivityForResult( intent, CAMERA );

        } else {

        }
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        if (galleryIntent.resolveActivity( getPackageManager() ) != null) {
            startActivityForResult( galleryIntent, Constants.PICK_IMAGE_REQUEST );
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == Constants.PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), filePath );
                ivProductEditImage.setImageBitmap( bitmap );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA & resultCode == RESULT_OK
        ) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get( Constants.DATA );
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri( thumbnail );
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File( getRealPathFromURI( tempUri ) );
            filePath= Uri.fromFile( finalFile );
            Log.d( TAG, "onActivityResult: camera"+filePath );
            ivProductEditImage.setImageBitmap( thumbnail );
            Toast.makeText( ProductEditActivity.this, R.string.image_saved, Toast.LENGTH_SHORT ).show();
        }


    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress( Bitmap.CompressFormat.JPEG, 100, bytes );
        String path = MediaStore.Images.Media.insertImage( getContentResolver(), inImage, null, null );
        return Uri.parse( path );
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query( uri, null, null, null, null );
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                path = cursor.getString( idx );
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );

    }

    @Override
    public void onClick(View v) {
        if (v.equals( bChangeProductImage )) {
            openGallaryAndCamera();
        }
        if(v.equals( bSaveProduct ))
        {
            uploadImage();

        }

    }


    private void createProduct() {
//        Log.d( TAG, "createProduct: "+selectedCategory );
        Log.d( TAG, "createProduct: 1"+etProductSalePrice.getText().toString() );
        Log.d( TAG, "createProduct: 2"+etProductPrice.getText().toString() );

        Toast.makeText( ProductEditActivity.this, R.string.sucessfully_upload, Toast.LENGTH_SHORT ).show();
        progressDialog.setMessage(getString(R.string.creating_product));
        progressDialog.show();

        // add data to the list , sample data
        categoryList.add( new Category( getCategoryId) );
        imageList.add( new Image( dowanloadImageUri ) );
        Log.d( TAG, "createProduct: "+selectedCategory );

        ServerController.getInstance().createProductCall( new InsertProductData( etProductTitle.getText().toString(),
                "simple",etProductSalePrice.getText().toString() , etProductPrice.getText().toString(), "A newly fashion arise on ", "New Trend"
                , categoryList, imageList ), new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {
                progressDialog.dismiss();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllProductsFragment() ).commit();
                Log.d( TAG, "onSuccess: ( CREATE PRODUCT API )-( PRODCUT NAME ): " + dataJson.getName() );
                Log.d( TAG, "onSuccess: regular price"+dataJson.getRegularPrice() );
                Log.d( TAG, "onSuccess: price"+dataJson.getPrice() );
                Log.d( TAG, "onSuccess: ( CREATE PRODUT API )-( PRODCUT IMAGE ): " + dataJson.getImages().get( 0 ).getSrc() );
            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d( TAG, "onFailure: ( CREATE PRODCUT API )-( FAILURE ) " );
                progressDialog.dismiss();

            }
        } );

    }
    private void uploadImage() {
        Log.d( TAG, "uploadImage: " );
        if (filePath != null) {
            Log.d( TAG, "uploadImage: " + filePath );
            progressDialog.setTitle( R.string.uploading );
            progressDialog.show();

            StorageReference ref = storageReference.child( "images/" + UUID.randomUUID().toString() );
            ref.putFile( filePath )
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Log.d( TAG, "onSuccess: 1 " + taskSnapshot.getMetadata().getPath() );
//                            Log.d( TAG, "onSuccess: "+taskSnapshot.getMetadata().getName() );

                            storageReference.child( taskSnapshot.getMetadata().getPath() ).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
//                                    Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                                    dowanloadImageUri = uri.toString();
                                    if (createNewProduct != null) {
                                        if (createNewProduct.equals( Constants.CREATE_NEW_PRODUCT )) {
                                            createProduct();
                                            Log.d( TAG, "onSuccess: "+dowanloadImageUri );
                                        }

                                    } else {
                                        updateProduct();
                                    }


                                    Log.d( TAG, "onSuccess: 3" + uri.toString() );
//                                    generatedFilePath = downloadUri.toString(); /// The string(file link) that you need
                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            } );
                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText( ProductEditActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    } )
                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage( "Uploaded " + (int) progress + "%" );
                        }
                    } );
        }
    }
    private void updateProduct() {

        progressDialog.setMessage(getString(R.string.updating_product));
        progressDialog.show();
        categoryList.add( new Category( getCategoryId) );
        imageList.add( new Image( dowanloadImageUri ) );

        ServerController.getInstance().updateProductCall(getProductId, new InsertProductData(etProductTitle.getText().toString(), "simple",
                etProductPrice.getText().toString(), etProductSalePrice.getText().toString(), null,null, categoryList, imageList), new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {

                progressDialog.dismiss();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllProductsFragment() ).commit();
                Log.d(TAG, "onSuccess: ( UPDATE PRODUCT API )-( UPDATE PRODUCT DESCRIPTION ): " + dataJson.getDescription() );
                Log.d(TAG, "onSuccess: ( UPDATE PRODUCT API )-( UPDATE PRODUCT IMAGE ): " + dataJson.getImages().get(0).getSrc());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: (UPDATE PRODUCT API )-(FAILURE) ");
                progressDialog.dismiss();

            }
        });

    }
}

