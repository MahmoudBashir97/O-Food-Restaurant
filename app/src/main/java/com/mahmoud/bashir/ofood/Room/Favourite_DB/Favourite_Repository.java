package com.mahmoud.bashir.ofood.Room.Favourite_DB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;

import java.util.List;

public class Favourite_Repository {

    private Favourite_Dao favourite_dao;
    private LiveData<List<Favourite_Schema>> allFavourites;

    public Favourite_Repository (Application application){
        Favourite_Database database = Favourite_Database.getInstance(application);
        favourite_dao = database.dao();
        allFavourites = favourite_dao.getAllfavourites();
    }

    public Completable insert(Favourite_Schema schema){
        return favourite_dao.insert(schema);
    }
    public Completable delete(Favourite_Schema schema){
        return favourite_dao.delete(schema);
    }
    public  LiveData<List<Favourite_Schema>> getAllFavs(){
        return allFavourites;
    }
    public void Update(Favourite_Schema schema){
        favourite_dao.update(schema);
    };

/*
    public void insert (Favourite_Schema schema){
        new InsertFavAsyncTask(favourite_dao).execute(schema);
    }

    public void delete (Favourite_Schema schema){
        new DeleteFavAsyncTask(favourite_dao).execute(schema);
    }



    private static class InsertFavAsyncTask  extends AsyncTask<Favourite_Schema,Void,Void> {

        private Favourite_Dao favourite_dao;
        private InsertFavAsyncTask(Favourite_Dao favourite_dao){
            this.favourite_dao=favourite_dao;
        }

        @Override
        protected Void doInBackground(Favourite_Schema... favourite_schemas) {

            favourite_dao.insert(favourite_schemas[0]);
            return null;
        }
    }

    private static class DeleteFavAsyncTask extends AsyncTask<Favourite_Schema,Void,Void>{
        private Favourite_Dao favourite_dao;
        private DeleteFavAsyncTask(Favourite_Dao favourite_dao){
            this.favourite_dao=favourite_dao;
        }


        @Override
        protected Void doInBackground(Favourite_Schema... favourite_schemas) {
            favourite_dao.delete(favourite_schemas[0]);
            return null;
        }
    }*/


}

