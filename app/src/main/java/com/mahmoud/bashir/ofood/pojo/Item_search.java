package com.mahmoud.bashir.ofood.pojo;

public class Item_search {
    String Text1;
    String Text2;

    public Item_search(String text1, String text2) {
        Text1 = text1;
        Text2 = text2;

    }

    public String getText1() {
        return Text1;
    }

    public void setText1(String text1) {
        Text1 = text1;
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
    }
}
