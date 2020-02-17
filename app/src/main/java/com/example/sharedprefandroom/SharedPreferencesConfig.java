package com.example.sharedprefandroom;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preferences), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(Boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference), status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference), false);
        return status;
    }

    public void writeLoginUsername(String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.login_name_preference), username);
        editor.commit();
    }

    public String readLoginUsername(){
        String username = "";
        username = sharedPreferences.getString(context.getResources().getString(R.string.login_name_preference), "");
        return username;
    }
}
