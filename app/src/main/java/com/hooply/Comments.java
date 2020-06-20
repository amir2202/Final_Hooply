package com.hooply;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;


@Entity(indices = {@Index(name = "userindex3",value={"user"})},tableName = "comments",foreignKeys = {@ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "user",
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
),
        @ForeignKey(
        entity = Post.class,
        parentColumns = "id",
        childColumns = "post",
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
)},primaryKeys = {"user", "stamp"} )
public class Comments {
    @ColumnInfo(name="user")
    @NonNull private String user;

    @ColumnInfo(name="post")
    private int post;

    @ColumnInfo(name="content")
    private String content;

    @ColumnInfo(name="stamp")
    @TypeConverters({Converters.class})
    @NonNull Date stamp;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
