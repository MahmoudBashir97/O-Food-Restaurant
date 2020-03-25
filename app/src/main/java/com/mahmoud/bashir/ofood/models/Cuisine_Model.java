package com.mahmoud.bashir.ofood.models;

public class Cuisine_Model {
    int imageURI;
    String nameCuisine;

    public Cuisine_Model(int imageURI, String nameCuisine) {
        this.imageURI = imageURI;
        this.nameCuisine = nameCuisine;
    }

    public int getImageURI() {
        return imageURI;
    }

    public void setImageURI(int imageURI) {
        this.imageURI = imageURI;
    }

    public String getNameCuisine() {
        return nameCuisine;
    }

    public void setNameCuisine(String nameCuisine) {
        this.nameCuisine = nameCuisine;
    }
}
