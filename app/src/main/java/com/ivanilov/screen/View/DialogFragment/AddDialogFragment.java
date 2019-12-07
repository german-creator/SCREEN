package com.ivanilov.screen.View.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.screen.Presenter.Adapter.AdapterAdd;
import com.ivanilov.screen.Presenter.Entity.BasketItem;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.ProductSelectionActivity;

import java.util.ArrayList;

public class AddDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.basket_add, null);
        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(view);
        final ArrayList<BasketItem> product = getActivity().getIntent().getParcelableArrayListExtra("ChoiceProduct");


        ArrayList<BasketItem> add = getArguments().getParcelableArrayList("addName");

        builder.setTitle(product.get(0).getName())

        .setView(view);

        final AdapterAdd adapterAdd = new AdapterAdd((ProductSelectionActivity) getActivity(), add);
        ListView listViewProduct = view.findViewById(R.id.Basket_add_ListView);
        listViewProduct.setAdapter(adapterAdd);

        view.findViewById(R.id.Basket_add_OK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                ArrayList<BasketItem> basketList = intent.getParcelableArrayListExtra("BasketList");
                ArrayList<BasketItem> add = getArguments().getParcelableArrayList("addName");

                ArrayList<Boolean> checkItem = adapterAdd.getCheckItem();

                if (basketList == null){
                    basketList = new ArrayList<>();

                }
                basketList.add(product.get(0));


                for (int i =0; i<add.size(); i++){
                    if (checkItem.get(i)==true) {
                        basketList.add(add.get(i));
                    }
                }

                intent.putParcelableArrayListExtra("BasketList", basketList);

                ((ProductSelectionActivity) getActivity()).setCount();

                dismiss();

            }
        });

        view.findViewById(R.id.Basket_add_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }


}
