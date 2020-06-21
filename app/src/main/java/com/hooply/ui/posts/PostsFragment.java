package com.hooply.ui.posts;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hooply.Comments;
import com.hooply.ExternalDb;
import com.hooply.GlobalVar;
import com.hooply.MainActivity;
import com.hooply.Parser;
import com.hooply.Post;
import com.hooply.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostsFragment extends Fragment {

   // private PostsViewModel postsViewModel;
    public View v;
    public TextView comment1;
    public TextView comment2;
    public TextView comment3;
    public TextView[] commentBoxes;
    public TextView displayBox;
    public int commentIndex = 0;
    public int postIndex = 0;
    ImageView imagebox;

    List<Post> allposts;
    List<Comments> allcomments;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allposts = ExternalDb.getPosts(3);
        allcomments = new ArrayList<Comments>();
        Log.d("post",allposts.get(0).getContent());
//        postsViewModel =
////                ViewModelProviders.of(this).get(PostsViewModel.class);
////        View root = inflater.inflate(R.layout.fragment_posts, container, false);
////        final TextView textView = root.findViewById(R.id.text_posts);
////        postsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });

        v = inflater.inflate(R.layout.fragment_posts, container, false);
        displayBox = (TextView) v.findViewById(R.id.uniqueid);
        imagebox = (ImageView) v.findViewById(R.id.imagestuff);

        commentBoxes= new TextView[]{(TextView) v.findViewById(R.id.comment1), (TextView) v.findViewById(R.id.comment2), (TextView) v.findViewById(R.id.comment2)};
        this.setPost(allposts.get(postIndex));
        Button prev = (Button) v.findViewById(R.id.prevcomment);
        if(GlobalVar.currentPost == 0){
            GlobalVar.setEnabled(prev,false);
        }
        Button nextcomment = (Button) v.findViewById(R.id.nextcomments);

        return inflater.inflate(R.layout.fragment_posts, container, false);
        //return root;
    }


    public void setComments(Comments[] comments){
       /* TextView comment = (TextView) v.findViewById(R.id.comment1);
        comment.setText("comm1");

        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView comment = (TextView) v.findViewById(R.id.comment1);
                Log.d("asdasd",String.valueOf(comment.getText()));
                comment.setText("comm1");
                Log.d("asadasdasd",String.valueOf(comment.getText()));
                LayoutInflater inflater = (LayoutInflater) MainActivity.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.activity_main, null);
                view.invalidate();
            }
        });
        */

    }


    public void setPost(Post post){


        Pattern pattern = Pattern.compile("(@IMG\\[.*\\])");
        Matcher matcher = pattern.matcher(post.getContent());
        if(matcher.find())
        {
            int startindex = matcher.start();
            int endindex = matcher.end();
            String base64 = post.getContent().substring(startindex +5,endindex);
            Bitmap imagine = Parser.convert64toImg(base64);
            imagebox.setImageBitmap(imagine);
            allcomments = post.getAllComments();
            Comments[] showing = new Comments[3];
            for(int i = commentIndex; i < commentIndex +3; i++){
                showing[i] = allcomments.get(i);
            }
            this.setComments(showing);
        }
        else{
            displayBox.setText(post.getContent());
            displayBox.setText(post.getContent());
            allcomments = post.getAllComments();
            Comments[] showing = new Comments[3];

            for(int i = commentIndex; i < commentIndex +3; i++){
                showing[i] = allcomments.get(i);

            }
            Log.d("thepost",post.getContent());
            Log.d("firstcomm",showing[0].getContent());
            this.setComments(showing);


        }


    }


}