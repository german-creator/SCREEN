package com.ivanilov.screen.Presenter.DialogFragmentPresenter;

import android.content.Context;

import com.ivanilov.screen.Model.TestDatabase;
import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.View.Activity.SettingsActivity;

import java.util.List;

public class ChoiseAddPresenter {
    private SettingsActivity view;
    private PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();
    private TestDatabase testDatabase = new TestDatabase();

    public void attachView (SettingsActivity settingsActivity){
        view = settingsActivity;
    }

    public void detachView (){
        view = null;
    }


    public List<String>  getAllProductGroup (){

        List<String> s = testDatabase.getGroupName(view);

        return s;

    }

    public void saveProductGroup(String choiseGroup, Context context){

        preferencesPrecenter.setStringPreferences(context,"ChoiseAdd", choiseGroup);
    }

}
