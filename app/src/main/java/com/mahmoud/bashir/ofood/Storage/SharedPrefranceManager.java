package com.mahmoud.bashir.ofood.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefranceManager {
    Context context;
    private static final String SHARED_PREF_USER = "user_O_Food";

    private static SharedPrefranceManager sharedPrefranceManager;

    private SharedPrefranceManager(Context context) {
        this.context = context;
    }

    public synchronized static SharedPrefranceManager getInastance(Context context){
        if (sharedPrefranceManager == null){
            sharedPrefranceManager = new SharedPrefranceManager(context);
        }
        return sharedPrefranceManager;
    }


    //--------------- user -------------//
    public void saveUser(String username,String useremail,String userPhone) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.putString("userName", username);
        editor.putString("userEmail", useremail);
        editor.putString("userPhone", userPhone);


        editor.putBoolean("userLogged", true);

        editor.apply();
    }

    public void save_Popular_Data(int Pop_Image,String Pop_Name,String Pop_Desc) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.putString("Pop_Image", ""+Pop_Image);
        editor.putString("Pop_Name", Pop_Name);
        editor.putString("Pop_Desc", Pop_Desc);


        editor.putBoolean("userLogged", true);

        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("userLogged", false);
    }



    public void save_Add_to_cart_Marker(boolean added) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.putString("Checked", ""+added);
        editor.putBoolean("userLogged", true);

        editor.apply();
    }

    public void save_Add_to_cart_ID(int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.putInt("id", id);
        editor.putBoolean("userLogged", true);

        editor.apply();
    }


    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }

    public String getUserPhone() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("userPhone", "");
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("userEmail", "");
    }




    public String getPop_Image() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Pop_Image", "");
    }

    public String getPop_Name() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Pop_Name", "");
    }

    public String getPop_Desc() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Pop_Desc", "");
    }

    public String getChecked() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Checked", "");
    }

    public int getID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1);
    }


    public void clearUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userLogged", false);
        editor.clear();
        editor.apply();
    }
}
