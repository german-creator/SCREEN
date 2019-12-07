package com.ivanilov.screen.View.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ivanilov.screen.Presenter.DialogFragmentPresenter.HelloTextPresenter;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;

public class HelloTextDialogFragment extends DialogFragment {
    HelloTextPresenter helloTextPresenter = new HelloTextPresenter();

    private EditText editTextHello;
    private Button buttonSave;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sethellotext, null);
        editTextHello = view.findViewById(R.id.Settings_Hello_EditText_HelloText);

        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(view);
        buttonSave = view.findViewById(R.id.Settings_Hello_Button_SaveText);

        builder.setView(view);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextHello.getText() != null) {
                            String s = editTextHello.getText().toString();
                            helloTextPresenter.saveHelloText(s, getActivity());
                            dismiss();
                }
            }
        });

        view.findViewById(R.id.Settings_Hello_Button_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




        return builder.create();
    }
}
