package com.hooply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import java.io.InputStream;
import java.util.Random;

public class AddPost extends AppCompatActivity {

    private ImageView imageView;
    private ActionBar actionBar;
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Create a new Post");
        //assert getSupportActionBar() != null;
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView)findViewById(R.id.image_post);


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
                uploadPost(postString);
                finish();
            }
        });
    }

    public void uploadPost(String poststring){
        Random random = new Random();

        String testid = String.valueOf(random.nextInt(Integer.MAX_VALUE));
        boolean has = ExternalDb.hasPostId(testid);
        while(has == true){
            testid = String.valueOf(random.nextInt(Integer.MAX_VALUE));
            has = ExternalDb.hasPostId(testid);
        }
        Post mypost = new Post(Integer.valueOf(testid),GlobalVar.userid,poststring);
        ExternalDb.insertPost(mypost);
        if(imageView.getDrawable() == null){
            Toast.makeText(this, "Post uploaded without an image.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Post uploaded an with image.", Toast.LENGTH_SHORT).show();
            String img = bitmap.toString();
            uploadPicture(bitmap);
        }
    }

    public void uploadPicture(Bitmap picture){
        //TODO: update picture to database
    }

    public void imageButton(){
        ImageButton imageButton = (ImageButton) findViewById(R.id.picture_button);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchGalleryIntent();
            }
        });
    }

    public void launchGalleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            if(data == null){
                return;
            }
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            bitmap = convertImageViewToBitmap(imageView);
        }
    }

    private Bitmap convertImageViewToBitmap(ImageView v){
        Bitmap bm=((BitmapDrawable)v.getDrawable()).getBitmap();
        return bm;
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




}