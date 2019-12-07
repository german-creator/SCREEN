package com.ivanilov.screen.Presenter.ActivityPresenter;

import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.View.Activity.SettingsActivity;

import java.util.ArrayList;

public class SettingsPresenter {
    PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();

    public void deleteAddGroup (SettingsActivity view){
        preferencesPrecenter.deletPrefferences(view, "ChoiseAdd");

    }

    public void deletAddfromGroup (SettingsActivity view){
        ArrayList<String> selectedGroup = preferencesPrecenter.getArrayStringPreferences(view,"ChoiseGroup");
        String selectedAdd = preferencesPrecenter.getStringPreferences(view,"ChoiseAdd");

        ArrayList<String> newSelectedGroup = new ArrayList<String>();
        for (String i:selectedGroup){
            if (i.equals(selectedAdd)){
            }
            else newSelectedGroup.add(i);
        }
        preferencesPrecenter.setArrayStringPreferences(view, "ChoiseGroup", newSelectedGroup);

    }

    public void setSwitchValue(SettingsActivity view){
        if (preferencesPrecenter.getStringPreferences(view,"ChoiseAdd") != null){
            view.setaSwitch();
        }

    }

}
