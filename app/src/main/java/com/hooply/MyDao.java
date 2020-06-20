package com.hooply;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface MyDao
{
    @Insert
    public void addUser(User user);

    @Delete
    public void deleteUser(User user);

}
