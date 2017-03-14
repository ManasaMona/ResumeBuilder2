package com.example.manasaa.resumebuilder.Other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.manasaa.resumebuilder.Activity.MainActivity;
import com.example.manasaa.resumebuilder.Activity.ResumeMainPage;

import java.util.HashMap;

public class SessionManager {
    private static final String TAG=SessionManager.class.getSimpleName();

    SharedPreferences pref;// Shared Preferences
    Editor editor; // Editor for Shared preferences
    Context _context;    // Context
    int PRIVATE_MODE = 0;   // Shared pref mode
    public static final String PREF_NAME = "LOGINPREF"; // Sharedpref file name
    private static final String IS_LOGIN = "IsLoggedIn"; // All Shared Preferences Keys
    public static final String KEY_USERID = "userID"; //USER   ID
    public static final String KEY_NAME = "name"; // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email"; // Email address (make variable public to access fromutside)
    public static final String KEY_PROFILEURL="profile_url";  //PROFILE URL

    public SessionManager(Context context){ // Constructor
        Log.d(TAG, "called SessionManager(Context context)");
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email,String profileurl){ //create login session
        Log.d(TAG, "called createLoginSession(");
        editor.putBoolean(IS_LOGIN, true); // Storing login value as TRUE
        editor.putString(KEY_NAME, name);// Storing name in pref
        editor.putString(KEY_EMAIL, email);// Storing email in pref
        editor.putString(KEY_PROFILEURL, profileurl);
        editor.commit();// commit changes
    }

    public void checkLogin(){ //Check login method wil check user login status//If false it will redirect user to login page// If false it will redirect user to login page
        Log.d(TAG, "called checkLogin()");
        if(this.isLoggedIn()){ // Check login status
            Log.d(TAG, this.isLoggedIn()+" called loged in");
            Intent intentToProfilePage=new Intent(_context,ResumeMainPage.class);
            intentToProfilePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentToProfilePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intentToProfilePage);
        }
    }

    public HashMap<String, String> getUserDetails(){ //Get stored session data
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null)); // user name
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));// user email id
        user.put(KEY_PROFILEURL, pref.getString(KEY_PROFILEURL,null));
        return user;
    }

    public boolean isLoggedIn(){//Quick check for login
        return pref.getBoolean(IS_LOGIN, false);
    }
}