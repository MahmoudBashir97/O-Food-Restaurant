package com.mahmoud.bashir.ofood.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mahmoud.bashir.ofood.R;

@Database(entities = {Favourite_Schema.class}, version = 1,exportSchema = false)
public abstract class Favourite_Database extends RoomDatabase {

    private static Favourite_Database instance;
    public abstract Favourite_Dao dao();


    public static synchronized Favourite_Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Favourite_Database.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private Favourite_Dao favourite_dao;
        private PopulateDbAsyncTask(Favourite_Database db){
            favourite_dao=db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favourite_dao.insert(new Favourite_Schema("Lemon Ricotta Pancakes1","its a lemon pancake that you will always come to our restaurants to test it ", R.drawable.lemon_ricotta_pancakes));
            favourite_dao.insert(new Favourite_Schema("Lemon Ricotta Pancakes2","its a lemon pancake that you will always come to our restaurants to test it ", R.drawable.lemon_ricotta_pancakes));
            favourite_dao.insert(new Favourite_Schema("Lemon Ricotta Pancakes3","its a lemon pancake that you will always come to our restaurants to test it ", R.drawable.lemon_ricotta_pancakes));

            return null;
        }
    }
}
