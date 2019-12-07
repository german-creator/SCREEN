package com.ivanilov.screen.View.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.screen.Presenter.DialogFragmentPresenter.ChoiseAddPresenter;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.SettingsActivity;

import java.util.ArrayList;
import java.util.List;


public class СhoiceAddDialogFragment extends DialogFragment {

    ChoiseAddPresenter choiseAddPresenter = new ChoiseAddPresenter();

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_group_choice, null);

        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(view);

        choiseAddPresenter.attachView((SettingsActivity)getActivity());

        final int[] mSelectedItems = new int[1];
        final List<String> a = (ArrayList<String>) choiseAddPresenter.getAllProductGroup();
        final CharSequence [] item = a.toArray(new CharSequence[a.size()]);

        builder.setTitle("Выберете добавки:");
        builder.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectedItems[0] = which;
            }
        });

        builder.setView(view);


        view.findViewById(R.id.Settings_Group_Choice_Button_SaveText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = a.get(0);
                choiseAddPresenter.saveProductGroup(item[mSelectedItems[0]].toString(), v.getContext());
                dismiss();

            }
        });

        view.findViewById(R.id.Settings_Group_Choice_Button_Сancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}
