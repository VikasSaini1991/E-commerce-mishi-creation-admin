package com.crinoidtechnologies.mishicreationadmin.activities;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class CategoryEditActivity extends AppCompatActivity implements View.OnClickListener {
    AlertDialog.Builder pictureDialog;
ImageView ivChangeImage;
EditText etChangeTitle;
Button bChangeImage;
    private Uri filePath;
String TAG="CategoryEditActivity";
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category_edit );
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle( "Category Edit" );
//        ((AppCompatActivity)getApplicationContext()).getSupportActionBar().setTitle( "Category Edit" );
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        initViews();
        requestPermission();

    }

    private void initViews() {
        ivChangeImage=findViewById( R.id.image_change );
        etChangeTitle=findViewById( R.id.title_change );
        bChangeImage=findViewById( R.id.b_category_edit_change_image );
        Intent intent=getIntent();
       String changeTtitle=intent.getExtras().getString( "title" );
       int changeImage=intent.getExtras().getInt( "CategoryImage" );
       ivChangeImage.setImageResource( changeImage );
       etChangeTitle.setText( changeTtitle );
       bChangeImage.setOnClickListener( this );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

        }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
//            openFilePicker();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.equals( bChangeImage ))
        {
            openGallaryAndCamera();
        }
    }

    private void openGallaryAndCamera() {
        pictureDialog = new AlertDialog.Builder( this );
        pictureDialog.setTitle( "Select Action" );
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera","Cancel"};
        pictureDialog.setItems( pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
//                        takePhotoFromCamera();
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
        Intent cameraIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult( cameraIntent, CAMERA );

    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult( galleryIntent, PICK_IMAGE_REQUEST );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            filePath = data.getData();
            Log.d( TAG, "onActivityResult: filepath gallary"+filePath );

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), filePath );
                Log.d( TAG, "onActivityResult: gallery pic"+bitmap );
                ivChangeImage.setImageBitmap( bitmap );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA & resultCode == RESULT_OK
        ) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get( "data" );
            Log.d( TAG, "onActivityResult: camer pic"+thumbnail );
// CALL THIS METHOD TO GET THE URI FROM THE BITMAP
//            Uri tempUri = getImageUri( thumbnail);
//            Log.d( TAG, "onActivityResult: temp Uri"+tempUri );
            // CALL THIS METHOD TO GET THE ACTUAL PATH
//            File finalFile = new File(getRealPathFromURI(tempUri));
//            Log.d( TAG, "onActivityResult: finalfile"+finalFile );
//            filePath= Uri.fromFile( finalFile );
            ivChangeImage.setImageBitmap( thumbnail );
            Toast.makeText( CategoryEditActivity.this, "Image Saved!", Toast.LENGTH_SHORT ).show();
        }

    }
    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, null, null);
        Log.d( TAG, "getImageUri: "+path );
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                Log.d( TAG, "getRealPathFromURI: "+path );
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );

    }
}
