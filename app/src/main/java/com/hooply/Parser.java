package com.hooply;


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


}
