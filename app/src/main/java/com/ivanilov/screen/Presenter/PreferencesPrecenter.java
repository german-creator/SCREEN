package com.ivanilov.screen.Presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PreferencesPrecenter {


    public String getStringPreferences (Context context, String key){

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String result = myPreferences.getString(key, null);

        return result;

    }

    public ArrayList<String> getArrayStringPreferences (Context context, String key){

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        final  ArrayList<String> result = gson.fromJson(myPreferences.getString(key, null), ArrayList.class);

        return result;

    }

    public void setStringPreferences (Context context, String key, String value){

        SharedPreferences myPreferences = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putString(key, value);

        myEditor.commit();

    }

    public void setArrayStringPreferences (Context context, String key, List<String> s){

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        Gson gson = new Gson();


        myEditor.putString(key,gson.toJson(s));

        myEditor.commit();

    }

    public void deletPrefferences (Context context, String key){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.remove(key);
        myEditor.commit();
    }


    public void setArrayIntPreferences (Context context, String key, ArrayList<Integer> s){

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        Gson gson = new Gson();

        String a = gson.toJson(s);

        myEditor.putString(key,gson.toJson(s));

        myEditor.commit();

    }

    public ArrayList<Integer> getArrayIntegerPreferences (Context context, String key){

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();

        final  ArrayList<Integer> result = gson.fromJson(myPreferences.getString(key, null), new TypeToken<ArrayList<Integer>>(){}.getType());

        return result;

    }

    public Boolean contains (Context context, String key){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (myPreferences.contains(key)) return true;
        else return false;

    }
}
