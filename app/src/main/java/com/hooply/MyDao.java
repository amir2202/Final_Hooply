package com.hooply;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface MyDao
{
    @Insert
    public void addUser(User user);

    @Insert
    public void addPost(Post post);

    @Insert
    public void addComment(Comments comment);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM user WHERE id IN (:id)")
    List<User> userIdExists(String id);

    @Query("SELECT * FROM user")
    List<User> allLocalUsers();

    @Query("SELECT * FROM posts LIMIT 1")
    List<Post> getPosts();

    @Query("SELECT * FROM comments WHERE post = :id")
    List<Comments> getCommentsofPost(int id);

    @RawQuery
    List<Post> getRawQuery(SupportSQLiteQuery query);
}
