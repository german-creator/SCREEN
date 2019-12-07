package com.ivanilov.screen.Presenter.ActivityPresenter;

import com.ivanilov.screen.Presenter.Adapter.BasketAdapter;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.Presenter.OpenRecipte;
import com.ivanilov.screen.View.Activity.BasketActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class BasketPresenter {

    public void runAdapter (BasketActivity view, ArrayList<BasketItem> basketList) {

        BasketAdapter basketAdapter = new BasketAdapter(view, basketList);

        view.runAdapter(basketAdapter);

    }



    public void back (BasketActivity view){
        view.openProductSelectionActivity();
    }



    public void setCost (BasketActivity view, ArrayList<BasketItem> items){

        double sum = 0;
        for (int i = 0; i< items.size(); i++) {
            sum = sum + Double.valueOf(items.get(i).getPrice()) * items.get(i).getAmount();
        }
        DecimalFormat f = new DecimalFormat("##.00");
        String result = f.format(sum) +" \u20BD";

        view.setCost(result);


    }

    public void openRecipte (BasketActivity view, String sum, boolean type){

        ArrayList<Integer> amountList = new ArrayList<>();
        ArrayList<String> selectedUuid  = new ArrayList<>();

        ArrayList<BasketItem> basketItems = view.getIntent().getParcelableArrayListExtra("BasketList");

        for(BasketItem i: basketItems){
            int a = Integer.valueOf(i.getAmount());
            amountList.add(a);
            selectedUuid.add(i.getUuid());

        }
        OpenRecipte openRecipte = new OpenRecipte();
        openRecipte.openReceipt(view, selectedUuid, amountList, sum, type);

    }

}