package com.ivanilov.screen.Presenter.ActivityPresenter;

import android.content.Intent;

import com.ivanilov.screen.Model.TestDatabase;
import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.Presenter.Entity.ProductSelectionList;
import com.ivanilov.screen.View.Activity.PinCodeActivity;
import com.ivanilov.screen.View.Activity.WelcomeActivity;

import java.util.ArrayList;

public class WelcomePresenter {

    private WelcomeActivity view;
    private PreferencesPrecenter preferencesPrecenter;
    TestDatabase testDatabase = new TestDatabase();

    public void attachView (WelcomeActivity welcomeActivity){
        view = welcomeActivity;
    }

    public void detachView (){
        view = null;
    }


    public void viewIsReady() {

        preferencesPrecenter = new PreferencesPrecenter();

        String s = preferencesPrecenter.getStringPreferences(view, "HelloText");


        if (s != null)  {
            s = s.replace("\"", "");
            view.setTextTextViewWelcome(s);

        }

    }

    public void createBasketItem (){

        ArrayList<String> groupName = preferencesPrecenter.getArrayStringPreferences(view,"ChoiseGroup");

        if (groupName==null) {
            Intent intent = new Intent(view.getApplicationContext(), PinCodeActivity.class);
            view.startActivity(intent);
        }
        else {

            ArrayList<ProductSelectionList> basketItemsList = new ArrayList<>();

            for (String i : groupName) {

                ArrayList<String> name = new ArrayList<>();
                name.addAll(testDatabase.getProductByGroupName(i, view));

                ArrayList<String> price = new ArrayList<>();
                price.addAll(testDatabase.getPriseProductByGroupName(i, view));

                ArrayList<Integer> amount = new ArrayList<>();
                for (int j = 0; j < name.size(); j++) {
                    amount.add(1);
                }
                ProductSelectionList productSelectionList = new ProductSelectionList(
                        i,
                        name,
                        price,
                        amount
                );

                basketItemsList.add(productSelectionList);
            }

            Intent intent = view.getIntent();
            intent.putParcelableArrayListExtra("allProduct", basketItemsList);

        }
    }

}
