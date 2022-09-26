package com.henzmontera.cap102_plantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    //User
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String UNAME = "NAME";
    public static final String UEMAIL = "EMAIL";
    public static final String UIMAGE = "IMAGE";
    public static final String UID = "ID";

    //Guest
    public static final String GNAME = "NAME";
    public static final String GID = "ID";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    //Create Session
    public void createUserSession(String name, String email, String id, String image){
        editor.putBoolean(LOGIN, true);
        editor.putString(UNAME, name);
        editor.putString(UEMAIL, email);
        editor.putString(UIMAGE, image);
        editor.putString(UID, id);
        editor.apply();
    }

    public void updateUserProfilePicture(String image){
        editor.putString(UIMAGE, image);
        editor.apply();
    }

    public void createGuestSession(String name, String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(GNAME, name);
        editor.putString(GID, id);
        editor.apply();
    }

    //Is Logged In
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        // If Not, Stop session
        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(UNAME, sharedPreferences.getString(UNAME, null));
        user.put(UEMAIL, sharedPreferences.getString(UEMAIL, null));
        user.put(UIMAGE, sharedPreferences.getString(UIMAGE, null));
        user.put(UID, sharedPreferences.getString(UID, null));
        return user;
    }

    public HashMap<String, String> getGuestDetails(){
        HashMap<String, String> guest = new HashMap<>();
        guest.put(GNAME, sharedPreferences.getString(GNAME, null));
        guest.put(GID, sharedPreferences.getString(GID, null));
        return guest;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }
}
