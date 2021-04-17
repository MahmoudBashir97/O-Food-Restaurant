package com.mahmoud.bashir.ofood.Room.Favourite_DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface Favourite_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Favourite_Schema favouriteSchema);


    @Delete
    Completable delete(Favourite_Schema favouriteSchema);


    @Update
    void update(Favourite_Schema favouriteSchema);


    @Query("SELECT * FROM favourites_table ORDER BY name DESC")
    LiveData<List<Favourite_Schema >> getAllfavourites();
}
