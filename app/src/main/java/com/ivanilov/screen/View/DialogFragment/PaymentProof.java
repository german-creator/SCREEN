package com.ivanilov.screen.View.DialogFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.Presenter.OpenRecipte;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.BasketActivity;

public class PaymentProof extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_paymentproff, null);

        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(view);

        final Activity activity = getActivity();

        builder.setView(view);

        TextView textView = view.findViewById(R.id.PaymentProf_TextView);
        textView.setText("Оплатить " + activity.getIntent().getExtras().getString("type"));

        view.findViewById(R.id.PaymentProf_Button_Ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( activity.getIntent().getExtras().getBoolean("boleanType") == true){
                    OpenRecipte openRecipte = new OpenRecipte();
                    openRecipte.payCash((BasketActivity) activity);
                }
                else {
                    OpenRecipte openRecipte = new OpenRecipte();
                    openRecipte.payCard((BasketActivity) activity);
                }

                dismiss();

            }
        });

        view.findViewById(R.id.PaymentProf_Button_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(500 , 300);

    }
}
