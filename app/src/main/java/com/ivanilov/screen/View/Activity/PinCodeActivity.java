package com.ivanilov.screen.View.Activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanilov.screen.Presenter.ActivityPresenter.PinCodePresenter;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;

public class PinCodeActivity extends AppCompatActivity {

    EditText enterPin;
    EditText createPin;
    EditText repeatPin;

    TextView textEnterPin;
    TextView textCreatePin;
    TextView textRepeatPin;

    ImageButton cancel;
    Button ok;
    KioskMode kioskMode = new KioskMode();

    PinCodePresenter pinCodePresenter = new PinCodePresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        getSupportActionBar().hide();
        kioskMode.hide(findViewById(android.R.id.content));

        enterPin =findViewById(R.id.PinCode_EditText_EnterPin);
        createPin =findViewById(R.id.PinCode_EditText_CreatePin);
        repeatPin =findViewById(R.id.PinCode_EditText_RepeatPin);

        enterPin.setGravity(Gravity.LEFT);
        createPin.setGravity(Gravity.LEFT);
        repeatPin.setGravity(Gravity.LEFT);

        textEnterPin =findViewById(R.id.PinCode_TextView_EnterPin);
        textCreatePin =findViewById(R.id.PinCode_TextView_CreatePin);
        textRepeatPin =findViewById(R.id.PinCode_TextView_RepeatPin);

        cancel = findViewById(R.id.PinCode_Button_Back);
        ok = findViewById(R.id.PinCode_Button_Next);

        pinCodePresenter.attachView(this);

        if (getIntent().getIntExtra("True", 2) == 1){
            modeChangePin();
        }
        else {
            pinCodePresenter.checkPincodeExistence();
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
    }

    public void modeEnterPin (){
        createPin.setVisibility(View.GONE);
        repeatPin.setVisibility(View.GONE);
        textCreatePin.setVisibility(View.GONE);
        textRepeatPin.setVisibility(View.GONE);

        enterPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                kioskMode.hide(findViewById(android.R.id.content));
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    ok.callOnClick();
                }
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCodePresenter.openWelcome();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCodePresenter.checkPincodeAndOpen(enterPin.getText().toString());
            }
        });

        pincodeButton(enterPin);

    }

    public void modeCreatePin (){
        enterPin.setVisibility(View.GONE);
        textEnterPin.setVisibility(View.GONE);

        createPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodeButton(createPin);
            }
        });

        repeatPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodeButton(repeatPin);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCodePresenter.openWelcome();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createPin.getText().toString().equals(repeatPin.getText().toString()) && createPin.getText().toString().length() == 4){
                    pinCodePresenter.savePincodeAndOpenSettings(repeatPin.getText().toString());
                }
                else pinCodePresenter.toast("Введите четырехзначные отдинаковые пароли");
            }
        });
    }

    public void modeChangePin (){
        textEnterPin.setText("Введите старый пароль");
        textCreatePin.setText("Придумайте новый пароль");


        repeatPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodeButton(repeatPin);
            }
        });

        enterPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodeButton(enterPin);
            }
        });

        createPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodeButton(createPin);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCodePresenter.openSetting();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinCodePresenter.checkPincode(enterPin.getText().toString()) == true){
                    if (createPin.getText().toString().equals(repeatPin.getText().toString()) && createPin.getText().toString().length() == 4) {
                        pinCodePresenter.savePincodeAndOpenSettings(createPin.getText().toString());
                    }
                    else pinCodePresenter.toast("Введите четырехзначные отдинаковые пароли");

                }
                else pinCodePresenter.toast("Неверный пароль");
            }
        });


    }

    public void pincodeButton (final EditText editText){

        findViewById(R.id.PinCode_Button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"1");
            }
        });
        findViewById(R.id.PinCode_Button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"2");
            }
        });
        findViewById(R.id.PinCode_Button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"3");
            }
        });
        findViewById(R.id.PinCode_Button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"4");
            }
        });
        findViewById(R.id.PinCode_Button_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"5");
            }
        });
        findViewById(R.id.PinCode_Button_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"6");
            }
        });
        findViewById(R.id.PinCode_Button_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"7");
            }
        });
        findViewById(R.id.PinCode_Button_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"8");
            }
        });
        findViewById(R.id.PinCode_Button_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"9");
            }
        });
        findViewById(R.id.PinCode_Button_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+"0");
            }
        });
        findViewById(R.id.PinCode_Button__).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
            }
        });

    }

}
