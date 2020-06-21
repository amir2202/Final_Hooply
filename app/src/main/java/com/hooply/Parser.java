package com.hooply;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    static Gson gson;
    public static List<User> parseUsers(String json, MyDao da){
        gson = new Gson();
        List<User> all = new ArrayList<User>();
        User[] items = gson.fromJson(json, User[].class);
        for(User user: items)
        {
            if(da.userIdExists(user.getId()).size() == 0){
                all.add(user);
            }
        }

        return all;
    }

    public static List<Post> parsePost(String json, MyDao da){
        gson = new Gson();
        List<Post> all = new ArrayList<Post>();
        Post[] items = gson.fromJson(json, Post[].class);
        for(Post pos: items)
        {
            all.add(pos);
        }

        return all;
    }

    public static List<Comments> parseComments(String json, MyDao da){
        gson = new Gson();
        List<Comments> all = new ArrayList<Comments>();
        Comments[] items = gson.fromJson(json, Comments[].class);
        for(Comments com: items)
        {
            all.add(com);
        }

        return all;
    }

    public static Bitmap convert64toImg(String base64){
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return decodedImage;
    }


}
