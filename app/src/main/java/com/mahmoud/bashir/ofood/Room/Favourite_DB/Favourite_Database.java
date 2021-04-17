package com.mahmoud.bashir.ofood.Room.Favourite_DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mahmoud.bashir.ofood.R;

@Database(entities = {Favourite_Schema.class}, version = 2,exportSchema = false)
public abstract class Favourite_Database extends RoomDatabase {

    private static Favourite_Database instance;
    public abstract Favourite_Dao dao();


    public static synchronized Favourite_Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Favourite_Database.class, "favourites_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
