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

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("userLogged", false);
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

    public void clearUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userLogged", false);
        editor.clear();
        editor.apply();
    }
}
