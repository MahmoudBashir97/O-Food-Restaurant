package com.mahmoud.bashir.ofood.models;

public class Popular_Model {

    int imageURI;
    String namePop;
    String descPop;

    public Popular_Model(int imageURI, String namePop, String descPop) {
        this.imageURI = imageURI;
        this.namePop = namePop;
        this.descPop = descPop;
    }

    public int getImageURI() {
        return imageURI;
    }

    public void setImageURI(int imageURI) {
        this.imageURI = imageURI;
    }

    public String getNamePop() {
        return namePop;
    }

    public void setNamePop(String namePop) {
        this.namePop = namePop;
    }

    public String getDescPop() {
        return descPop;
    }

    public void setDescPop(String descPop) {
        this.descPop = descPop;
    }
}
