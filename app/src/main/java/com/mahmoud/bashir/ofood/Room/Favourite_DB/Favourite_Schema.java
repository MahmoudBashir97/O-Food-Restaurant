package com.mahmoud.bashir.ofood.Room.Favourite_DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "favourites_table")
public class Favourite_Schema {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String description;

    private int  imageURI;

    public Favourite_Schema(String name, String description, int imageURI) {
        this.name = name;
        this.description = description;
        this.imageURI = imageURI;
    }
    public  Favourite_Schema(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageURI() {
        return imageURI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURI(int imageURI) {
        this.imageURI = imageURI;
    }
}
