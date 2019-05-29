package com.crinoidtechnologies.mishicreationadmin.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllCategoryFragment;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.Image;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;
import com.crinoidtechnologies.mishicreationadmin.utils.UtilityMethods;

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

public class CategoryEditActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "CategoryEditActivity";
    private String dowanloadImageUri, getImageUri;
    private String createNewCategory = "";
    private String categoryEdit = "";
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    AlertDialog.Builder pictureDialog;
    private ImageView ivChangeImage;
    private EditText etChangeTitle;
    private Button bChangeImage, bSaveCategory, bDeleteCategory;
    private String changeImage, changeTtitle;
    private Toolbar topToolBar;
    private ActionBar actionBar;
    private int getId;
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category_edit );
        initViews();
        requestPermission();

    }

    private void initViews() {
        progressDialog = new ProgressDialog( this );
        topToolBar = findViewById( R.id.toolbar );
        ivChangeImage = findViewById( R.id.image_change );
        etChangeTitle = findViewById( R.id.et_title_change );
        bChangeImage = findViewById( R.id.b_category_edit_change_image );
        bSaveCategory = findViewById( R.id.b_save_category );
        bDeleteCategory = findViewById( R.id.b_delete_category );
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        bSaveCategory.setOnClickListener( this );
        Bundle bundle = getIntent().getExtras();
        createNewCategory = bundle.getString( Constants.CREATE_NEW_CATEGORY );
        categoryEdit = bundle.getString( Constants.CATEGORY_EDIT );

        if (bundle != null) {
            Log.d( TAG, "initViews: if" );
            changeTtitle = bundle.getString( Constants.TITLE );
            changeImage = bundle.getString( Constants.IMAGE );
            getId = (int) bundle.getInt( String.valueOf( Constants.CATEGORY_ID ) );
            Log.d( TAG, "initViews: " + getId );

        } else {
            Log.d( TAG, "initViews: else" );
        }
        setSupportActionBar( topToolBar );
        actionBar = getSupportActionBar();
        if (createNewCategory != null) {
            if (createNewCategory.equals( Constants.CREATE_NEW_CATEGORY )) {
                actionBar.setTitle( R.string.create_new_category );
                bChangeImage.setText( R.string.upload );
                bDeleteCategory.setVisibility( View.GONE );

            }
        } else {

            actionBar.setTitle( R.string.category_edit );
            bChangeImage.setText( R.string.change );
            bDeleteCategory.setVisibility( View.VISIBLE );
        }
        actionBar.setDisplayHomeAsUpEnabled( true );
        etChangeTitle.setText( changeTtitle );
        Picasso.with( getApplicationContext() ).load( changeImage ).into( ivChangeImage );
        bChangeImage.setOnClickListener( this );
        bDeleteCategory.setOnClickListener( this );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_WRITE_PERMISSION );
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals( bChangeImage )) {

            openGallaryAndCamera();


        }
        if (v.equals( bSaveCategory )) {
            Log.d( TAG, "onClick: save Button " );
            uploadImage();
        }
        if (v.equals( bDeleteCategory )) {

            deleteCategory();
            Log.d( TAG, "onClick: " + getId );

        }
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
                Log.d( TAG, "onActivityResult: " + bitmap );
                ivChangeImage.setImageBitmap( bitmap );
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
            filePath = Uri.fromFile( finalFile );
            Log.d( TAG, "onActivityResult: " + finalFile );
            ivChangeImage.setImageBitmap( thumbnail );
            Toast.makeText( CategoryEditActivity.this, R.string.image_saved, Toast.LENGTH_SHORT ).show();
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
                                    if (createNewCategory != null) {
                                        if (createNewCategory.equals( Constants.CREATE_NEW_CATEGORY )) {
                                            createCategory();
                                        }

                                    } else {
                                        updateCategory();
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
                            Toast.makeText( CategoryEditActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT ).show();
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

    private void createCategory() {
        Toast.makeText( CategoryEditActivity.this, R.string.sucessfully_upload, Toast.LENGTH_SHORT ).show();
        progressDialog.setTitle( R.string.save_sucessfully );
        progressDialog.show();


        ServerController.getInstance().createCategoryCall( new InsertCategoryData( etChangeTitle.getText().toString(), new Image( dowanloadImageUri ), null ), new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                progressDialog.dismiss();

                Log.d( TAG, "onSuccess: ( CREATE CATEGOTY API )-( CATEGORY NAME ): " + dataJson.getName() );
                Log.d( TAG, "onSuccess:1 " + etChangeTitle.getText().toString() );
                Log.d( TAG, "onSuccess:2 " + dowanloadImageUri );

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllCategoryFragment() ).commit();

//                Log.d(TAG, "onSuccess: ( CREATE CATEGOTY API )-( CATEGORY IMAGE ): " + dataJson.getImage().getSrc());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d( TAG, "onFailure: ( CREATE CATEGOTY API )-( FAILURE ) " );
                progressDialog.dismiss();

            }
        } );


    }

    private void deleteCategory() {

        Toast.makeText( CategoryEditActivity.this, R.string.delete_sucessfully, Toast.LENGTH_SHORT ).show();
        progressDialog.setMessage( getString( R.string.deleting_category ) );
        progressDialog.show();

        ServerController.getInstance().deleteCategoryCall( getId, true, new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                progressDialog.dismiss();
                Log.d( TAG, "onSuccess: ( DELETE CATEGORY API )-( DELETE CATEGORY NAME ): " + dataJson.getName() );
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllCategoryFragment() ).commit();


            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d( TAG, "onFailure: (DELETE CATEGORY API )-(FAILURE) " );
                progressDialog.dismiss();

            }
        } );
    }

    private void updateCategory() {
        Toast.makeText( CategoryEditActivity.this, R.string.update_sucessfully, Toast.LENGTH_SHORT ).show();
        progressDialog.setTitle( R.string.update_sucessfully );
        progressDialog.show();


        ServerController.getInstance().updateCategoryCall( getId,
                new InsertCategoryData( etChangeTitle.getText().toString(), new Image( dowanloadImageUri ), "A relevant Product" ), new ServerRequestCallback<AllCategoryDatum>() {
                    @Override
                    public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                        progressDialog.dismiss();
                        Log.d( TAG, "onSuccess: ( UPDATE CATEGORY API )-( UPDATE CATEGORY DESCRIPTION ): " + dataJson.getDescription() );
//                        Log.d(TAG, "onSuccess: ( UPDATE CATEGORY API )-( UPDATE CATEGORY IMAGE ): " + dataJson.getImage().getSrc());

                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllCategoryFragment() ).commit();
                    }

                    @Override
                    public void onFailure(ServerRequest request, Error error) {

                        Log.d( TAG, "onFailure: (UPDATE CATEGORY API )-(FAILURE) " );
                        progressDialog.dismiss();

                    }
                }
        );

    }

}


