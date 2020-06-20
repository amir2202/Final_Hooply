package com.hooply;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Posts")
public class Posts {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
