package com.ivanilov.screen.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.ProductSelectionActivity;

import java.util.ArrayList;

public class AdapterAdd extends BaseAdapter {

    private ProductSelectionActivity view;
    private ArrayList<BasketItem> allAdd;
    private ArrayList<Boolean> checkItem = new ArrayList<>();


    public AdapterAdd(ProductSelectionActivity view, ArrayList<BasketItem> allAdd){
        this.view = view;
        this.allAdd = allAdd;
        for (int i = 0; i<allAdd.size(); i++){
            checkItem.add(false);
        }

    }

    @Override
    public int getCount() {
        return allAdd.size();
    }

    @Override
    public Object getItem(int position) {
        return allAdd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public ArrayList<Boolean>  getCheckItem() {
        return checkItem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(view).inflate(R.layout.item_basket_add, parent, false);
        }
        final CheckBox checkBox = convertView.findViewById(R.id.item_basket_add_name);

        final TextView textView = convertView.findViewById(R.id.item_basket_add_cost);

        checkBox.setText("+ " + allAdd.get(position).getName());
        checkBox.setChecked(checkItem.get(position));
        textView.setText(allAdd.get(position).getPrice()+" \u20BD");


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean b = checkItem.get(position);
                if(checkBox.isPressed() && b ==false){
                    checkItem.set(position, true);

                }
                if(checkBox.isPressed() && b == true){
                    checkItem.set(position, false);
                }

            }
        });

        return convertView;
    }
}
