package com.ivanilov.screen.Presenter.DialogFragmentPresenter;

import android.content.Context;

import com.google.gson.Gson;
import com.ivanilov.screen.Model.TestDatabase;
import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.View.Activity.SettingsActivity;

import java.util.List;

public class ChoiseGroupPresenter {
    private SettingsActivity view;
    private PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();
    private TestDatabase testDatabase = new TestDatabase();

    public void attachView (SettingsActivity settingsActivity){
        view = settingsActivity;
    }

    public void detachView (){
        view = null;
    }

    public List<String> getAllProductGroup (){

        List<String> s = testDatabase.getGroupName(view);

        return s;

    }

    public void saveProductGroup(List<String> choiseGroup, Context context) {
        Gson gson = new Gson();

        String result = gson.toJson(choiseGroup);

        preferencesPrecenter.setStringPreferences(context,"ChoiseGroup", result);
    }
}
