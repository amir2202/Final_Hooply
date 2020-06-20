package com.hooply;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "Posts")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="user")
    private int user;

    @ColumnInfo(name="content")
    private String content;

    @ColumnInfo(name="stamp")
    @TypeConverters({Converters.class})
    private Date timestamp;

    public int getId() {
        return id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }
}
