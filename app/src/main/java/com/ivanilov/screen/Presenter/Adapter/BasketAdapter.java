package com.ivanilov.screen.Presenter.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ivanilov.screen.Presenter.ActivityPresenter.BasketPresenter;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.BasketActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getColor;

public class BasketAdapter extends BaseAdapter {

    private BasketActivity view;
    private ArrayList<BasketItem> basketList;
    private BasketPresenter basketPresenter = new BasketPresenter();
    Intent intent;
    DecimalFormat f = new DecimalFormat("##.00");




    public BasketAdapter(BasketActivity view, ArrayList<BasketItem> basketList) {
        this.view = view;
        this.basketList = basketList;
        intent = view.getIntent();

    }

    @Override
    public int getCount() {
        return basketList.size();
    }

    @Override
    public BasketItem getItem(int position) {
        return basketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView =  LayoutInflater.from(view).inflate(R.layout.activity_basket_item, parent, false);
        }

        final BasketItem item = getItem(position);

        final TextView textViewName  = convertView.findViewById(R.id.Basket_Item_TextView_NameProduct);
        final TextView textViewAmount = convertView.findViewById(R.id.Basket_Item_TextView_Count);
        final TextView textViewCost = convertView.findViewById(R.id.Basket_Item_TextView_Cost);
        ImageButton buttonPlus = convertView.findViewById(R.id.Basket_Item_Button_Plus);
        ImageButton buttonMinus = convertView.findViewById(R.id.Basket_Item_Button_Minus);
        RelativeLayout relativeLayout = convertView.findViewById(R.id.Basket_Item_Relative_Amount);
        LinearLayout linearLayout = convertView.findViewById(R.id.Basket_Item_Linear);

        if(item.getAdd() == true){
            toAddStyle(textViewName, relativeLayout, linearLayout, textViewCost);
            convertView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 80));
        }
        else {
            convertView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 100));
            toUsualStyle(textViewName, relativeLayout, linearLayout, textViewCost);
        }


        textViewName.setText(item.getName());
        textViewAmount.setText(String.valueOf(item.getAmount()));

        if (item.getPrice().equals("0")){  textViewCost.setText(0 +" \u20BD");}

        else{
                Double cost = (Double.valueOf(item.getAmount()*Double.valueOf(item.getPrice())));
                textViewCost.setText((f.format(cost)) +" \u20BD");

            }



        basketPresenter.setCost(view, basketList);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item.setAmount(item.getAmount() +1);

                basketList.set(position, item);
                setAmountAdd(basketList, position);


            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getAmount()==1){
                    deleteAdd(basketList, position);
                }
                else {
                    item.setAmount(item.getAmount()- 1);
                    basketList.set(position, item);
                    setAmountAdd(basketList, position);
                }
            }
        });

        return convertView;
    }

    public void toAddStyle (TextView textViewName, RelativeLayout relativeLayout, LinearLayout linearLayout, TextView textViewCost){
        int color = getColor(view, R.color.colorPrimaryLight);
        textViewCost.setBackgroundColor(color);
        linearLayout.setBackgroundColor(color);
        relativeLayout.setBackgroundColor(color);
        textViewName.setTextSize(14);
        textViewCost.setTextSize(14);
        textViewName.setPadding(60,0,0,0);

    }

    public void toUsualStyle (TextView textViewName, RelativeLayout relativeLayout, LinearLayout linearLayout, TextView textViewCost){
        int color = getColor(view, R.color.backgroundcolor);
        textViewCost.setBackgroundColor(color);
        linearLayout.setBackgroundColor(color);
        relativeLayout.setBackgroundColor(color);
        textViewName.setTextSize(16);
        textViewCost.setTextSize(16);
        textViewName.setPadding(5,0,0,0);

    }

    public void deleteAdd (ArrayList<BasketItem> basketItems, int position){
        basketList.remove(position);


        for (int i=position; i<basketItems.size();){

            if(basketList.get(i).getAdd() == true){
                basketList.remove(i);
            }
            else break;
        }
        basketPresenter.setCost(view, basketList);
        intent.putParcelableArrayListExtra("BasketList", basketList);
        basketPresenter.runAdapter(view, basketList);

    }

    public void setAmountAdd (ArrayList<BasketItem> basketItems, int position){

        if (basketList.get(position).getAdd() == true){

        }

        else {
            for (int i=position+1; i<basketItems.size(); i++){
                if(basketList.get(i).getAdd() == true){
                    basketList.get(i).setAmount(basketList.get(i-1).getAmount());
                }
                else break;
            }

        }


        basketPresenter.setCost(view, basketList);
        intent.putParcelableArrayListExtra("BasketList", basketList);
        basketPresenter.runAdapter(view, basketList);
    }

}