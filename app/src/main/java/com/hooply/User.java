package com.hooply;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(indices = {@Index(name = "userindex",value={"id"})})
public class User {

    public User(String id, String name,String stamp){
        this.id = id;
        this.name = name;
        this.stamp = stamp;
    }

    @PrimaryKey
    @ColumnInfo(name="id")
    @NonNull private String id;


    @ColumnInfo(name="name")
    private String name;


    @ColumnInfo(name="stamp")
    private String stamp;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
