package com.mahmoud.bashir.ofood.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Dao;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Database;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Repository;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Schema;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Favourite_viewModel extends AndroidViewModel {
    private LiveData<List<Favourite_Schema>> allFav;
    Favourite_Repository  repo;
    Favourite_Database database;
    public Favourite_viewModel(@NonNull Application application,Favourite_Repository repository) {
        super(application);
       this.repo = repository;
    }

    public Completable insert(Favourite_Schema schema){
        return repo.insert(schema);
    }

    public void Update(Favourite_Schema schema){
        repo.Update(schema);
    }

    public Completable delete(Favourite_Schema schema){
        return repo.delete(schema);
    }


    public LiveData<List<Favourite_Schema>>getAllFavs (){
        allFav = repo.getAllFavs();
        return allFav;
    }
}