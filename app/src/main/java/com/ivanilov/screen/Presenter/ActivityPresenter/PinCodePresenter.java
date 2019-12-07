package com.ivanilov.screen.Presenter.ActivityPresenter;

import android.content.Intent;
import android.widget.Toast;

import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.View.Activity.PinCodeActivity;
import com.ivanilov.screen.View.Activity.SettingsActivity;
import com.ivanilov.screen.View.Activity.WelcomeActivity;

public class PinCodePresenter {

    private PinCodeActivity view;
    private PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();

    public void attachView (PinCodeActivity pinCodeActivity){
        view = pinCodeActivity;
    }

    public void detachView (){
        view = null;
    }

    public void checkPincodeExistence (){
        String password = preferencesPrecenter.getStringPreferences(view, "Password");
        if (password == null){
            view.modeCreatePin();
        }
        else {
            view.modeEnterPin();
        }


    }
    public void savePincodeAndOpenSettings (String s){
        preferencesPrecenter.setStringPreferences(view, "Password", s);
        openSetting();

    }

    public void checkPincodeAndOpen (String s){
        String password = preferencesPrecenter.getStringPreferences(view, "Password");
        if (password.equals(s)){
            openSetting();
        }
        else {
            toast("Неверный пароль");
        }
    }

    public void openSetting (){
        Intent intent = new Intent(view, SettingsActivity.class);
        view.startActivity(intent);
    }

    public void openWelcome (){
        Intent intent = new Intent(view, WelcomeActivity.class);
        view.startActivity(intent);
    }

    public void toast (String s){
        Toast toast = Toast.makeText(view, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public Boolean checkPincode (String s){
        String password = preferencesPrecenter.getStringPreferences(view, "Password");
        Boolean result = false;
        if (password.equals(s)){
            result = true;
        }
        else {
            toast("Неверный пароль");
            ;
        }
        return result;
    }


}
