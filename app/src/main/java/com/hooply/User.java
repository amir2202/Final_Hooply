package com.hooply;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(indices = {@Index(name = "userindex",value={"userid"})})
public class User {
    @PrimaryKey
    @NonNull private String userid;


    @ColumnInfo(name="name")
    private String fullName;



    @ColumnInfo(name="stamp")
    @TypeConverters({Converters.class})
    private Date stamp;

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

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
