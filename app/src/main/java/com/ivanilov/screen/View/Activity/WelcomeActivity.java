package com.ivanilov.screen.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanilov.screen.Presenter.ActivityPresenter.WelcomePresenter;
import com.ivanilov.screen.Presenter.Entity.ProductSelectionList;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity implements View.OnTouchListener {

    WelcomePresenter presenter = new WelcomePresenter();
    TextView textViewWelcome;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        presenter.attachView(this);
        presenter.viewIsReady();

        findViewById(R.id.Welcome_Layout).setOnTouchListener(this);

    }

    @Override
    protected void onResume () {
        super.onResume();
        startKioskMode();

        findViewById(R.id.Welcome_Button_toSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPinCodeActivity();
            }
        });

        thread = new Thread (new Runnable() {
            public void run() {
                presenter.createBasketItem();
            }
        });

        thread.setPriority(10);

        ArrayList<ProductSelectionList> item = getIntent().getParcelableArrayListExtra("allProduct");
        if (item == null){
            thread.start();
        }

    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        presenter.detachView();
    }


    public void setTextTextViewWelcome (String s){
        textViewWelcome = findViewById(R.id.Welcome_textView_WelcomeText);
        textViewWelcome.setText(s);

    }

    public void openPinCodeActivity () {
        Intent intent = new Intent(getApplicationContext(), PinCodeActivity.class);
        startActivity(intent);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                Intent intent = new Intent(this, ProductSelectionActivity.class);
                try {
                    thread.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<ProductSelectionList> item = getIntent().getParcelableArrayListExtra("allProduct");
                intent.putParcelableArrayListExtra("allProduct", item);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void startKioskMode(){
        getSupportActionBar().hide();
        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(findViewById(android.R.id.content));

    }


}
