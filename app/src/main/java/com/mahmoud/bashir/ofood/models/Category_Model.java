package com.mahmoud.bashir.ofood.models;

public class Category_Model {
    private int imageURI;
    private String Cat_name;

    public Category_Model(int imageURI, String cat_name) {
        this.imageURI = imageURI;
        Cat_name = cat_name;
    }

    public int getImageURI() {
        return imageURI;
    }

    public void setImageURI(int imageURI) {
        this.imageURI = imageURI;
    }

    public String getCat_name() {
        return Cat_name;
    }

    public void setCat_name(String cat_name) {
        Cat_name = cat_name;
    }
}
