package com.ivanilov.screen.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanilov.screen.Presenter.ActivityPresenter.ProductSelectionPresenter;
import com.ivanilov.screen.Presenter.Adapter.AdapterProductSelection;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.Presenter.Entity.ProductSelectionList;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;

import java.util.ArrayList;

public class ProductSelectionActivity extends AppCompatActivity {

    ProductSelectionPresenter presenter = new ProductSelectionPresenter();
    ListView listViewGroup;
    ListView listViewProduct;
    KioskMode kioskMode = new KioskMode();
    TextView textViewCount;
    int count;
    ArrayList<String> groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_selection);

        runKioskMode();

        presenter.attachView(this);

        textViewCount = findViewById(R.id.ProductSelection_TextView_Count);
        count=0;
        setCount();
    }

    @Override
    public void onResume() {
        super.onResume();

        setListViewGroup();

        findViewById(R.id.ProductSelection_Button_ToBasket).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openBasketActivity();
            }
        });

        findViewById(R.id.ProductSelection_Button_toBasket2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBasketActivity();
            }
        });

        findViewById(R.id.ProductSelection_Button_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomeActivity();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void setListViewGroup() {

        groupName = new ArrayList<>();

        ArrayList<ProductSelectionList> items= getIntent().getParcelableArrayListExtra("allProduct");
        for (ProductSelectionList i: items){
            groupName.add(i.getGroupNameName());
        }
        final AdapterProductSelection adapterProductSelection = new AdapterProductSelection(this, groupName, null, null);
        listViewGroup = findViewById(R.id.ProductSelection_ListView_Group);
        listViewGroup.setAdapter(adapterProductSelection);
    }

    public void setListViewProduct(final int position) {

        final Intent intent = getIntent();
        ProductSelectionList w = (ProductSelectionList) intent.getParcelableArrayListExtra("allProduct").get(position);
        final AdapterProductSelection adapterProductSelection = new AdapterProductSelection(this, w.getName(), w.getPrice(), "dg");
        listViewProduct = findViewById(R.id.ProductSelection_ListView_Product);
        listViewProduct.setAdapter(adapterProductSelection);

    }

    public void openWelcomeActivity() {
        presenter.detachView();
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        intent.putParcelableArrayListExtra("allProduct", getIntent().getParcelableArrayListExtra("allProduct"));
        startActivity(intent);

    }

    public void openBasketActivity() {

        ArrayList<BasketItem> basketItems = getIntent().getParcelableArrayListExtra("BasketList");

        if (presenter.checkBasket() == true) {
            Intent basket = new Intent(getApplicationContext(), BasketActivity.class);
            basket.putParcelableArrayListExtra("BasketList", basketItems);
            basket.putParcelableArrayListExtra("allProduct", getIntent().getParcelableArrayListExtra("allProduct"));
            startActivity(basket);
        }
    }

    public void runKioskMode(){
        getSupportActionBar().hide();
        kioskMode.hide(findViewById(android.R.id.content));
    }


    public void setCount (){

        if (count == 0){
            try {
                ArrayList<BasketItem> basketItem = getIntent().getParcelableArrayListExtra("BasketList");
                for (BasketItem a: basketItem){
                    if (a.getAdd() != true) count = count + a.getAmount();

                }

            } catch (Exception e){}

            if (count!=0) textViewCount.setText(String.valueOf(count));
            count++;
        }

        else {
            textViewCount.setText(String.valueOf(count));
            count++;
        }
    }

}
