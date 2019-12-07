package com.ivanilov.screen.Presenter.DialogFragmentPresenter;

import android.content.Context;

import com.google.gson.Gson;
import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.View.Activity.SettingsActivity;


public class HelloTextPresenter {
    private SettingsActivity view;
    private PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();


    public void saveHelloText(String helloText, Context context){

        Gson gson = new Gson();

        String result = gson.toJson(helloText);

        preferencesPrecenter.setStringPreferences(context,"HelloText", result);
    }




}
