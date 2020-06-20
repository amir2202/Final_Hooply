package com.hooply;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Comments {
    @PrimaryKey
    private int commentid;

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }
}
