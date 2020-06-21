package com.hooply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Posting extends AppCompatActivity {

    public View v;
    public TextView comment1;
    public TextView comment2;
    public TextView comment3;
    public TextView[] commentBoxes;
    public TextView displayBox;
    public int commentIndex = 0;
    public int postIndex = 0;
    public Post currentpost;
    ImageView imagebox;
    public TextView commentinput;

    List<Post> allposts;
    List<Comments> allcomments;

    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postingactivity);
         comment1 = (TextView) findViewById(R.id.comment1);
         comment2 = (TextView) findViewById(R.id.comment2);
         comment3 = (TextView) findViewById(R.id.comment3);
         commentinput = (EditText) findViewById(R.id.owncomment);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Posts");

        allposts = ExternalDb.getPosts(1,postIndex);


        allcomments = new ArrayList<Comments>();
        displayBox = (TextView) findViewById(R.id.uniqueid);
        imagebox = (ImageView) findViewById(R.id.imagestuff);
        commentBoxes= new TextView[]{(TextView)findViewById(R.id.comment1), (TextView) findViewById(R.id.comment2), (TextView) findViewById(R.id.comment2)};

        this.setPost(allposts.get(postIndex));
    }

    public void updateComments() {
        Comments[] showing = new Comments[3];
        int normalindex = 0;
        for (int i = commentIndex; i < commentIndex + 3; i++) {
            try {
                showing[normalindex] = allcomments.get(i);
                normalindex++;
            } catch (Exception e) {
                break;
            }
            this.setComments(showing);
        }
        this.setComments(showing);
    }

    public void buttonHandler(View view) {
        ((Button) findViewById(R.id.prevcomment)).setEnabled(true);

        if(this.commentIndex + 6 >= this.allcomments.size()){
            ((Button) findViewById(R.id.nextcomments)).setEnabled(false);
        }

        this.commentIndex = this.commentIndex + 3;
        updateComments();
    }

    public void setFirst(Comments comments) {
        TextView comment = (TextView) findViewById(R.id.comment1);
        comment.setText(comments.getContent());
        //remember alcoments
        allcomments.add(0, comments);
    }

    public void prevHandler(View view) {
        ((Button) findViewById(R.id.nextcomments)).setEnabled(true);

        if(this.commentIndex <= 3){
            ((Button) findViewById(R.id.prevcomment)).setEnabled(false);
        }

        this.commentIndex = this.commentIndex - 3;
        updateComments();
    }

    public void setPost(Post post) {
        // Get the post's comments
        allcomments = post.getAllComments();
        currentpost = post;
        Pattern pattern = Pattern.compile("(@IMG\\[.*\\])");
        Matcher matcher = pattern.matcher(post.getContent());

        // Load the post
        if(matcher.find()) {
            int startindex = matcher.start();
            int endindex = matcher.end();

            String base64 = post.getContent().substring(startindex +5,endindex);
            Log.d("base64",base64);
            Bitmap imagine = Parser.convert64toImg(base64);
            imagebox.setImageBitmap(imagine);

        } else {
            displayBox.setText(post.getContent());
            displayBox.setText(post.getContent());

        }

        // Always disable the previous comments button when a new post is loaded
        ((Button) findViewById(R.id.prevcomment)).setEnabled(false);


        // Check if there are any comments
        if (allcomments.size() != 0) {
            Comments[] showing = new Comments[3];
            int ownindex = 0;
            for(int i = commentIndex; i < commentIndex +3; i++){
                showing[ownindex] = allcomments.get(i);
                ownindex++;
            }
            this.setComments(showing);
        // If no comments, disable the next comments button
        } else {
            comment1.setText("");
            comment2.setText("");
            comment3.setText("");
            ((Button) findViewById(R.id.nextcomments)).setEnabled(false);
        }
    }
    public void addComment(View view) {
        String input = commentinput.getText().toString();
        int time = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(time);
        String ts =  tsTemp.toString();
        Comments comment = new Comments(GlobalVar.userid,currentpost.getId(),input,ts);
        ExternalDb.insertComment(comment);
        this.setFirst(comment);
    }

    public void setComments(Comments[] comments) {

        comment1.setText(comments[0].getContent());
        comment2.setText(comments[1].getContent());
        comment3.setText(comments[2].getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_post:
                Intent i = new  Intent(Posting.this, AddPost.class);
                startActivity(i);
                return true;
        }
        return false;
    }


    public void nextPost(View view) {
        postIndex++;
        List<Post> oneel = ExternalDb.getPosts(1,postIndex);
        this.allposts.add(oneel.get(0));
        this.setPost(allposts.get(postIndex));
    }
}