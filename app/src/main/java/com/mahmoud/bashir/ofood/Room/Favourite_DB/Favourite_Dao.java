package com.mahmoud.bashir.ofood.Room.Favourite_DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Favourite_Dao {

    @Insert
    void insert(Favourite_Schema favouriteSchema);


    @Delete
    void delete(Favourite_Schema favouriteSchema);


    /*@Update
    void update(Favourite_Schema favouriteSchema);

    @Query("DELETE FROM favourites")
    void deleteAllNotes();*/

    @Query("SELECT * FROM favourites ORDER BY name DESC")
    LiveData<List<Favourite_Schema >> getAllfavourites();
}
