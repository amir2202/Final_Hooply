package com.hooply;

import android.graphics.Color;
import android.widget.Button;

public class GlobalVar {
    public static int currentPost = 0;
    public static void setEnabled(Button button,boolean bool){
        if(bool == false){
            button.setEnabled(false);
            button.setBackgroundColor(Color.parseColor("#000000"));
        }
        else{
            button.setEnabled(true);
            button.setBackgroundResource(android.R.drawable.btn_default);
        }


    }
}
