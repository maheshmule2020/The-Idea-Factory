package com.krishna.iparker.User;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {



    private static SharedPrefManager mInstance;

    private static Context mCtx;

    private static final String SHARED_PRF_NAME="mySharedPref";
    private static final String KEY_USERNAME="name";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_ID="userid";

    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id,String username,String email){

        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PRF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);
        editor.apply();


        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PRF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PRF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public  String getUserEmail(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PRF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }
    public  String getUserName(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PRF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);
    }

}
