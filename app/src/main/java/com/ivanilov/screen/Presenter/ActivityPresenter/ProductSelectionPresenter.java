package com.ivanilov.screen.Presenter.ActivityPresenter;

import android.os.Bundle;
import android.widget.Toast;

import com.ivanilov.screen.Model.TestDatabase;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.Presenter.PreferencesPrecenter;
import com.ivanilov.screen.View.Activity.ProductSelectionActivity;
import com.ivanilov.screen.View.DialogFragment.AddDialogFragment;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.inventory.ProductItem;

public class ProductSelectionPresenter {

    private ProductSelectionActivity view;
    private PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();
    TestDatabase testDatabase = new TestDatabase();


    public void attachView (final ProductSelectionActivity productSelectionActivity){
        view = productSelectionActivity;

        crateAddList();

    }

    public void detachView (){

        view = null;
    }

    public boolean checkAdd (ProductSelectionActivity view){
        final List <String> productAdd = testDatabase.getProductByGroupName(preferencesPrecenter.getStringPreferences(view, "ChoiseAdd"), view);

        if (productAdd.size() == 0){
            return false;
        }
        else return true;
    }



    public Boolean checkBasket (){

        if (view.getIntent().getParcelableArrayListExtra("BasketList") == null) {
            Toast toast = Toast.makeText(view, "Выберете товары", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        else return true;
    }

    public List<String> getPrice (List<String> nameProduct){
        List<String> result = new ArrayList<>();

        ArrayList <String> name = new ArrayList<>(nameProduct);
        ArrayList<ProductItem> productItem = testDatabase.getProductByName(view, name);

        for (ProductItem i: productItem){
            result.add(((ProductItem.Product) i).getPrice().toString());
        }

        return result;
    }

    public void showDialog (ProductSelectionActivity view, ArrayList<BasketItem> basketList, Integer position) {

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("basketList", basketList);
        bundle.putInt("position", position);
        ArrayList<BasketItem> addListToFragment = view.getIntent().getParcelableArrayListExtra("AllAdd");
        bundle.putParcelableArrayList("addName", addListToFragment);

        AddDialogFragment addDialogFragment = new AddDialogFragment();
        addDialogFragment.setArguments(bundle);
        addDialogFragment.show(view.getSupportFragmentManager(), "Тэг?");
    }

    public void crateAddList (){

        ArrayList<BasketItem> basketItems = new ArrayList<>();
        String s  = preferencesPrecenter.getStringPreferences(view, "ChoiseAdd");

        List<ProductItem> products = testDatabase.getProducItemtByGroupName(s, view);

        for (int i =0; i<products.size(); i++){
            ProductItem.Product product = (ProductItem.Product) products.get(i);
            basketItems.add(new BasketItem(
                    product.getName(),
                    product.getPrice().toString(),
                    product.getUuid(),
                    1,
                    true
            ));
        }

        view.getIntent().putParcelableArrayListExtra("AllAdd", basketItems);


    }




}
