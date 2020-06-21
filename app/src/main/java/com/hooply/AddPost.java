package com.hooply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hooply.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddPost extends AppCompatActivity {

    private ActionBar actionBar;
    final int REQUEST_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Create a new Post");
        assert getSupportActionBar() != null;   //null check
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        uploadButton();
        imageButton();
    }

    public void uploadButton(){
        Button upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText postText = (EditText) findViewById(R.id.post_content);
                String postString = postText.getText().toString();
                Log.d("HENLOMEN",postString);
                uploadPost();
                finish();
            }
        });
    }

    public void uploadPost(){
        Toast.makeText(this, "Post uploaded.", Toast.LENGTH_SHORT).show();
        //TODO: upload post to database
    }

    public void imageButton(){
        ImageButton imageButton = (ImageButton) findViewById(R.id.picture_button);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddPost.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddPost.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                    return;
                } else {
                    launchGalleryIntent();
                }
            }
        });
    }

    public void launchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    launchGalleryIntent();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && requestCode == RESULT_OK) {
            final ImageView imageView = findViewById(R.id.image);
            final List<Bitmap> bitmaps = new ArrayList<>();
            ClipData clipData = data.getClipData();

//            if(clipData != null){
//                Uri imageUri = clipData.getItemAt(0).getUri();
//                try {
//                    InputStream is = getContentResolver().openInputStream(imageUri);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    bitmaps.add(bitmap);
//                } catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }
//            }
            Uri imageUri = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                bitmaps.add(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmaps.get(0));
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("scale", true);
//        intent.putExtra("outputX", 256);
//        intent.putExtra("outputY", 256);
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, 1);
//    }
//
//    public static final int PICK_IMAGE = 1;
//
//    public void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                //Display an error
//                return;
//            }
//            InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
//            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
//        }
//    }





}