package com.carlos.cc.gesact.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesService {

    private static PreferencesService instance = null;
    private final SharedPreferences preferences;

    private PreferencesService(Context context){
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesService getInstance(Context context){
        if(instance == null){
            instance = new PreferencesService(context);
        }
        return instance;
    }

    public String getProperty(String property){
        return preferences.getString(property, "");
    }

    public void setProperty(String property, String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(property, value);
        editor.commit();
    }

    public void removeProperty(String property){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(property);
        editor.commit();
    }


}
