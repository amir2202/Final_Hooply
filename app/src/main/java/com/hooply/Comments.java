package com.hooply;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;


@Entity(indices = {@Index(name = "userindex3",value={"user"}),@Index(name="commentpost",value={"post"})},tableName = "comments",foreignKeys = {@ForeignKey(
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
    public Comments(){};
    public Comments(String user, int postid, String content,String stamp){
        this.user_id = user;
        this.post_id = postid;
        this.content = content;
        this.stamp = stamp;
    }

    @ColumnInfo(name="user")
    @NonNull private String user_id;

    @ColumnInfo(name="post")
    private int post_id;

    @ColumnInfo(name="content")
    private String content;

    @ColumnInfo(name="stamp")
    @TypeConverters({Converters.class})
    @NonNull String stamp;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user) {
        this.user_id = user;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post) {
        this.post_id = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }
}
