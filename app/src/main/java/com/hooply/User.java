package com.hooply;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity
public class User {
    @PrimaryKey
    @NonNull private String userid;


    @ColumnInfo(name="name")
    private String fullName;

    //@ColumnInfo(name="timestamp")
    //private Timestamp stamp;

    public String getUserid() {
        return userid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
