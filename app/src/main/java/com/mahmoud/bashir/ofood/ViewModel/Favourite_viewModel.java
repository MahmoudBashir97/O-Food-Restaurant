package com.mahmoud.bashir.ofood.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Repository;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Schema;

import java.util.List;

public class Favourite_viewModel extends AndroidViewModel {
    private Favourite_Repository repository;
    private LiveData<List<Favourite_Schema>> allFav;

    public Favourite_viewModel(@NonNull Application application) {
        super(application);

        repository=new Favourite_Repository(application);
        allFav=repository.getAllFavs();
    }

    public void insert(Favourite_Schema schema){
        repository.insert(schema);
    }


    public void delete(Favourite_Schema schema){
        repository.delete(schema);
    }


    public LiveData<List<Favourite_Schema>> getAllFavs (){
        return allFav;
    }

}
