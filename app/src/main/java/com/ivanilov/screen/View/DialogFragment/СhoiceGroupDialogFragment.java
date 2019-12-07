package com.ivanilov.screen.View.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.screen.Presenter.DialogFragmentPresenter.ChoiseGroupPresenter;
import com.ivanilov.screen.Presenter.KioskMode;
import com.ivanilov.screen.R;
import com.ivanilov.screen.View.Activity.SettingsActivity;

import java.util.ArrayList;
import java.util.List;


public class СhoiceGroupDialogFragment extends DialogFragment {

    ChoiseGroupPresenter choiseGroupPresenter = new ChoiseGroupPresenter();

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Alert);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_group_choice, null);

        KioskMode kioskMode = new KioskMode();
        kioskMode.hide(view);

        choiseGroupPresenter.attachView((SettingsActivity)getActivity());

        final CharSequence [] items = choiseGroupPresenter.getAllProductGroup().toArray(new CharSequence[choiseGroupPresenter.getAllProductGroup().size()]);
        boolean[] itemChecked = new boolean[items.length];
        final List<Integer> selectedItems = new ArrayList<>();

        builder.setTitle("Выберете группы товаров:");
        builder.setMultiChoiceItems(items, itemChecked, new  DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            }
        });

        builder.setView(view);

        view.findViewById(R.id.Settings_Group_Choice_Button_SaveText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> choseProductGrop = new ArrayList<>();
                for (Integer i : selectedItems){
                    choseProductGrop.add(items[i].toString());
                }
                choiseGroupPresenter.saveProductGroup(choseProductGrop, v.getContext());
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
