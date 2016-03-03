package com.polimi.jgc.findamate.util;

/**
 * Created by JGC on 23/02/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.polimi.jgc.findamate.activity.LoginActivity;
import com.polimi.jgc.findamate.model.Defaults;

public class UserSessionManager {

    SharedPreferences pref;
    Editor editor;
    Context _context;

    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(Defaults.PREFER_NAME, Defaults.PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String email, String interests){
        editor.putBoolean(Defaults.IS_USER_LOGIN, true);
        editor.putString(Defaults.KEY_NAME, name);
        editor.putString(Defaults.KEY_INTERESTS_FORMATED, interests);
        editor.putString(Defaults.KEY_EMAIL, email);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            return false;
        }
        return true;
    }

    public void setInterests (String interests) {
        editor.putString(Defaults.KEY_INTERESTS_FORMATED, interests);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(Defaults.KEY_NAME, pref.getString(Defaults.KEY_NAME, null));
        user.put(Defaults.KEY_EMAIL, pref.getString(Defaults.KEY_EMAIL, null));
        user.put(Defaults.KEY_INTERESTS_FORMATED, pref.getString(Defaults.KEY_INTERESTS_FORMATED, null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(Defaults.IS_USER_LOGIN, false);
    }
}
