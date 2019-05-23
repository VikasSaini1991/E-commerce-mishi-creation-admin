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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;
import com.crinoidtechnologies.mishicreationadmin.utils.UtilityMethods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class CategoryEditActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "CategoryEditActivity";
    private AlertDialog.Builder pictureDialog;
    private ImageView ivChangeImage;
    private EditText etChangeTitle;
    private Button bChangeImage, bSaveCategory;
    private Uri filePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category_edit );
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        initViews();
        requestPermission();

    }

    private void initViews() {
        ivChangeImage = findViewById( R.id.image_change );
        etChangeTitle = findViewById( R.id.et_title_change );
        bChangeImage = findViewById( R.id.b_category_edit_change_image );
        bSaveCategory=findViewById( R.id.b_save_category );
        Intent intent = getIntent();
        String changeTtitle = intent.getExtras().getString( Constants.TITLE );
        int changeImage = intent.getExtras().getInt( Constants.IMAGE );
        ivChangeImage.setImageResource( changeImage );
        etChangeTitle.setText( changeTtitle );
        bChangeImage.setOnClickListener( this );
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
            //filePath= Uri.fromFile( finalFile );
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
//    private void uploadImage() {
//        if (filePath != null) {
//            Log.d( TAG, "uploadImage: "+filePath );
//            final ProgressDialog progressDialog = new ProgressDialog( this );
//            progressDialog.setTitle( "Uploading..." );
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child( "images/" + UUID.randomUUID().toString() );
//            ref.putFile( filePath )
//                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText( Main24Activity.this, "Uploaded", Toast.LENGTH_SHORT ).show();
//                        }
//                    } )
//                    .addOnFailureListener( new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText( Main24Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT ).show();
//                        }
//                    } )
//                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage( "Uploaded " + (int) progress + "%" );
//                        }
//                    } );
//        }
//    }
}
