package com.example.biem.alamien.serivices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.biem.alamien.LoginActivity;
import com.example.biem.alamien.dashboardd;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID_USER = "ID_USER";
    public static final String NAMA_USER = "NAMA_USER";
    public static final String EMAIL_USER = "EMAIL_USER";
    public static final String JENJANG= "JENJANG";



    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences =context.getSharedPreferences( PREF_NAME, PRIVATE_MODE );
        editor = sharedPreferences.edit();

    }

    public void createSession(String id_user,String nama_user,String email_user,String jenjang){
        editor.putBoolean(LOGIN, true);
        editor.putString( "ID_USER", id_user );
        editor.putString("NAMA_USER",nama_user);
        editor.putString("EMAIL_USER", email_user);
        editor.putString("jenjang",jenjang);

        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean( LOGIN, false );
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent intent = new Intent( context, LoginActivity.class );
            context.startActivity( intent );
            ((dashboardd) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>(  );
        user.put( ID_USER, sharedPreferences.getString( ID_USER, null ) );
        user.put( NAMA_USER, sharedPreferences.getString( NAMA_USER, null ) );
        user.put( EMAIL_USER, sharedPreferences.getString( EMAIL_USER, null ) );
        user.put( JENJANG, sharedPreferences.getString( JENJANG, null ) );
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent( context, LoginActivity.class );
        context.startActivity( intent );
        ((dashboardd) context).finish();

    }
}