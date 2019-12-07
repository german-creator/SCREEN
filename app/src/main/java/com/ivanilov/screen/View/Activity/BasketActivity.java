package com.ivanilov.screen.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanilov.screen.Presenter.ActivityPresenter.BasketPresenter;
import com.ivanilov.screen.Presenter.Adapter.BasketAdapter;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.DialogFragment.PaymentProof;

import java.util.ArrayList;

import ru.evotor.devices.commons.DeviceServiceConnector;

public class BasketActivity extends AppCompatActivity {

    ListView listViewBasket;
    TextView sum;
    BasketPresenter presenter = new BasketPresenter();
    Intent intent;
    Button buttoncash;
    Button buttocard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        runKioskMode();

        new Thread(new Runnable() {
            public void run() {
                DeviceServiceConnector.startInitConnections(getApplicationContext());
            }
        }).start();

        sum = findViewById(R.id.Basket_TextView_Sum);

        intent = getIntent();

        ArrayList<BasketItem> basketItems = intent.getParcelableArrayListExtra("BasketList");

        presenter.runAdapter(this, basketItems);
    }

    @Override
    public void onResume() {
        super.onResume();


        final Intent intent = this.getIntent();

        findViewById(R.id.Basket_Button_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.back(BasketActivity.this);
            }
        });

        buttoncash = findViewById(R.id.Basket_Button_PayCash);
        buttocard = findViewById(R.id.Basket_Button_PayCard);
        buttoncash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttoncash.setClickable(false);
                buttocard.setClickable(false);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttoncash.setClickable(true);
                        buttocard.setClickable(true);


                    }
                }, 1500);

                presenter.openRecipte(BasketActivity.this, sum.getText().toString(), true);

                intent.putExtra("sum", sum.getText().toString());
                intent.putExtra("type", "наличными?");
                intent.putExtra("boleanType", true);

                PaymentProof paymentProof = new PaymentProof();
                paymentProof.show(getFragmentManager(), "Тэг?, ");


            }
        });

        buttocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttoncash.setClickable(false);
                buttocard.setClickable(false);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttoncash.setClickable(true);
                        buttocard.setClickable(true);


                    }
                }, 1500);

                presenter.openRecipte(BasketActivity.this, sum.getText().toString(), false);

                intent.putExtra("sum", sum.getText().toString());
                intent.putExtra("type", "картой?");
                intent.putExtra("boleanType", false);

                PaymentProof paymentProof = new PaymentProof();
                paymentProof.show(getFragmentManager(), "Тэг?, ");
            }
        });

    }

    public void runAdapter (BasketAdapter basketAdapter){
        listViewBasket = findViewById(R.id.Basket_ListView_Product);
        listViewBasket.setAdapter(basketAdapter);
    }


    public void openProductSelectionActivity () {
        ArrayList<BasketItem> basketItems = getIntent().getParcelableArrayListExtra("BasketList");

        Intent intent = new Intent(this, ProductSelectionActivity.class);
        intent.putParcelableArrayListExtra("BasketList", basketItems);
        intent.putParcelableArrayListExtra("allProduct", getIntent().getParcelableArrayListExtra("allProduct"));
        startActivity(intent);

    }

    public void setCost (String s){
        sum.setText(s);
    }


    public void runKioskMode(){
        getSupportActionBar().hide();
        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(findViewById(android.R.id.content));
    }

}
