package com.hooply;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

@Entity(indices = {@Index(name = "userindex2",value={"user"})},tableName = "Posts",foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "user",
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
))
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="user")
    private int user;

    @ColumnInfo(name="content")
    private String content;

    private transient ArrayList<Comments> allcomments;

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

    public void addComment(Comments comment){ this.allcomments.add(comment);
    }

    public ArrayList<Comments> getAllComments(){
        this.allcomments = new ArrayList<Comments>();
        Comments example = new Comments();
        example.setContent("test comment");
        this.allcomments.add(example);
        this.allcomments.add(example);
        this.allcomments.add(example);
        return this.allcomments;
    }

}
