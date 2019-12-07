package com.ivanilov.screen.Presenter.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ivanilov.screen.Model.TestDatabase;
import com.ivanilov.screen.Presenter.ActivityPresenter.ProductSelectionPresenter;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.ProductSelectionActivity;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.inventory.ProductItem;

public class AdapterProductSelection extends BaseAdapter {

    private ProductSelectionActivity view;
    private List<String> group;
    private List<String> price;
    private String param;
    ProductSelectionPresenter presenter = new ProductSelectionPresenter();
    private boolean add;


    public AdapterProductSelection(ProductSelectionActivity view, List<String> group, List<String> price, String param){
        this.view = view;
        this.group = group;
        this.param = param;
        this.price = price;
        add = presenter.checkAdd(view);

    }

    @Override
    public int getCount() {
        return group.size();
    }

    @Override
    public Object getItem(int position) {
        return group.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (param == null) {

            if (convertView == null) {
                convertView = LayoutInflater.from(view).inflate(R.layout.textview_group, parent, false);
            }

            final Button button = convertView.findViewById(R.id.group_item_button);
            button.setText(group.get(position));

            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    Button button1 = view.findViewById(R.id.ProductSelection_Button_Tittle);
                    button1.setText(group.get(position));
                    view.setListViewProduct(position);
                }
            });
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(view).inflate(R.layout.textview_product, parent, false);
            }
            final Button button = convertView.findViewById(R.id.group_item_button);

            final TextView textView = convertView.findViewById(R.id.product_item_cost);

            textView.setText(price.get(position)+" \u20BD");
            button.setText(group.get(position));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    button.setClickable(false);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.setClickable(true);

                        }
                    }, 1500);



                    putProductToBasketList(group.get(position), price.get(position), position);
                    if (add == true) {
                        presenter.showDialog(view, view.getIntent().<BasketItem>getParcelableArrayListExtra("BasketList"), position);
                    }




                }
            });

        }
            return convertView;
    }

    public void putProductToBasketList (String name, String price, Integer position){


        BasketItem basketItem = new BasketItem(
                name,
                price,
                getUuid(name, price, view),
                1,
                false
        );

        ArrayList<BasketItem> basketItes = new ArrayList<>();
        basketItes.add(basketItem);

        Intent intent = view.getIntent();

        if (add == true){
            intent.putParcelableArrayListExtra("ChoiceProduct", basketItes);
        }
        else {

            ArrayList<BasketItem> basketItems = intent.getParcelableArrayListExtra("BasketList");
        if (basketItems == null){
            ArrayList<BasketItem> basketItems1 = new ArrayList<>();
            basketItems1.add(basketItem);
            intent.putParcelableArrayListExtra("BasketList", basketItems1 );
        }
        else{
            basketItems.add(basketItem);
            intent.putParcelableArrayListExtra("BasketList", basketItems );

        }
            view.setCount();
        }

    }

    public String getUuid (String name, String price, ProductSelectionActivity view){
        TestDatabase testDatabase = new TestDatabase();

        String result ="";

        ArrayList<String> nameArray = new ArrayList<>();
        nameArray.add(name);
        ArrayList<ProductItem> products =testDatabase.getProductByName(view, nameArray);


        if (products.size()==1) { result = products.get(0).getUuid();}

        else {
            for (ProductItem i: products){
                ProductItem.Product a = (ProductItem.Product) i;
                if (a.getPrice().toString().equals(price)){
                    result = i.getUuid();
                    break;
                }
            }
        }
        return result;
    }

}
