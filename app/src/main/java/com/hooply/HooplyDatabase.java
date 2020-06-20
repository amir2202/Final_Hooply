package com.hooply;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class,Comments.class,Post.class},version = 1)
@TypeConverters({Converters.class})
public abstract class HooplyDatabase extends RoomDatabase
{
    public abstract MyDao myDao();
}
