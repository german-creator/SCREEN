package com.ivanilov.screen.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanilov.screen.Presenter.ActivityPresenter.SettingsPresenter;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.DialogFragment.HelloTextDialogFragment;
import com.ivanilov.screen.View.DialogFragment.СhoiceAddDialogFragment;
import com.ivanilov.screen.View.DialogFragment.СhoiceGroupDialogFragment;

public class SettingsActivity extends AppCompatActivity {

    Switch aSwitch;
    SettingsPresenter settingsPresenter = new SettingsPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        getSupportActionBar().hide();
        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(findViewById(android.R.id.content));

    }

    @Override
    public void onResume() {
        super.onResume();

        final SettingsActivity view = this;
        findViewById(R.id.Settings_Button_SelectGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChoiceGroupDialogFragment();
            }
        });


        findViewById(R.id.Settings_Button_HelloText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelloTextDialogFragment();
            }
        });

        findViewById(R.id.Settings_Button_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aSwitch.isChecked()) settingsPresenter.deleteAddGroup(view);
                if (aSwitch.isChecked()) settingsPresenter.deletAddfromGroup(view);
                openWelcomeActivity();
            }
        });

        findViewById(R.id.settings_Button_Password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPinCodectivity();
            }
        });

        findViewById(R.id.Settings_Button_Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aSwitch.isChecked()) settingsPresenter.deleteAddGroup(view);
                if (aSwitch.isChecked()) settingsPresenter.deletAddfromGroup(view);

              collapse();

            }
        });

        aSwitch = findViewById(R.id.Settings_Switch_Add);
        settingsPresenter.setSwitchValue(this);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    openChoiceAddDialogFragment();
                }
            }
        });

    }

    public void openChoiceGroupDialogFragment(){
        СhoiceGroupDialogFragment choiceDialogFragment = new СhoiceGroupDialogFragment();
        choiceDialogFragment.show(getSupportFragmentManager(), "Тэг?");
    }
    public void openChoiceAddDialogFragment(){
        СhoiceAddDialogFragment choiceDialogFragment = new СhoiceAddDialogFragment();
        choiceDialogFragment.show(getSupportFragmentManager(), "Тэг?");
    }
    public void openHelloTextDialogFragment(){
        HelloTextDialogFragment helloTextDialogFragment = new HelloTextDialogFragment();
        helloTextDialogFragment.show(getFragmentManager(), "Тэг?");
    }
    public void openWelcomeActivity () {

        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);

        startActivity(intent);

    }

    public void openPinCodectivity () {

        Intent intent = new Intent(getApplicationContext(), PinCodeActivity.class);
        intent.putExtra("True", 1);
        startActivity(intent);

    }

    public void setaSwitch (){
        aSwitch.setChecked(true);

    }


    public void collapse (){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);

    }


}

