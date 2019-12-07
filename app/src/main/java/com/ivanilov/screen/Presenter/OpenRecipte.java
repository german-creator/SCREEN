package com.ivanilov.screen.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.ivanilov.screen.Model.TestDatabase;
import com.ivanilov.screen.Presenter.Entity.ProductSelectionList;
import com.ivanilov.screen.View.Activity.BasketActivity;
import com.ivanilov.screen.View.Activity.WelcomeActivity;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ru.evotor.devices.commons.DeviceServiceConnector;
import ru.evotor.devices.commons.exception.DeviceServiceException;
import ru.evotor.devices.commons.printer.PrinterDocument;
import ru.evotor.devices.commons.printer.printable.IPrintable;
import ru.evotor.devices.commons.printer.printable.PrintableText;
import ru.evotor.framework.component.PaymentPerformer;
import ru.evotor.framework.component.PaymentPerformerApi;
import ru.evotor.framework.core.IntegrationActivity;
import ru.evotor.framework.core.IntegrationException;
import ru.evotor.framework.core.IntegrationManagerCallback;
import ru.evotor.framework.core.IntegrationManagerFuture;
import ru.evotor.framework.core.action.command.open_receipt_command.OpenSellReceiptCommand;
import ru.evotor.framework.core.action.event.receipt.changes.position.PositionAdd;
import ru.evotor.framework.inventory.ProductItem;
import ru.evotor.framework.receipt.Position;
import ru.evotor.framework.receipt.formation.api.ReceiptFormationCallback;
import ru.evotor.framework.receipt.formation.api.ReceiptFormationException;
import ru.evotor.framework.receipt.formation.api.SellApi;

public class OpenRecipte extends IntegrationActivity {

    PreferencesPrecenter preferencesPrecenter = new PreferencesPrecenter();
    List<String> mList = new ArrayList<>();

    public void openReceipt(final BasketActivity view, ArrayList<String> uuidList, ArrayList<Integer> amount, String sum, boolean type) {

        final List<PositionAdd> positionAddList = createPositionAddList(view, uuidList, amount);

        printPreСheck(view, positionAddList, sum, type);

        soundSignal(view);

        new OpenSellReceiptCommand(positionAddList, null).process(view, new IntegrationManagerCallback() {
            @Override
            public void run(IntegrationManagerFuture future) {
                try {
                    IntegrationManagerFuture.Result result = future.getResult();
                    if (result.getType() == IntegrationManagerFuture.Result.Type.OK) {
                    }
                } catch (IntegrationException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void payCash (final BasketActivity view){

        List<PaymentPerformer> paymentPerformers = PaymentPerformerApi.INSTANCE.getAllPaymentPerformers(view.getPackageManager());

        for (PaymentPerformer p:paymentPerformers){
            if (p.getPaymentSystem().getPaymentSystemId().equals("ru.evotor.paymentSystem.cash.base")){
                SellApi.moveCurrentReceiptDraftToPaymentStage(view, p, new ReceiptFormationCallback() {

                    @Override
                    public void onSuccess() {
                        ArrayList<ProductSelectionList> productSelectionLists = view.getIntent().getParcelableArrayListExtra("allProduct");
                        Intent welcome = new Intent(view, WelcomeActivity.class);
                        welcome.putParcelableArrayListExtra("allProduct", productSelectionLists);
                        view.startActivity(welcome);
                    }

                    @Override
                    public void onError(@NotNull ReceiptFormationException e) {
                        Toast.makeText(view, "Ошибка при совершении оплаты", Toast.LENGTH_SHORT).show();

                        ArrayList<ProductSelectionList> productSelectionLists = view.getIntent().getParcelableArrayListExtra("allProduct");
                        Intent bassket = new Intent(view, BasketActivity.class);
                        bassket.putParcelableArrayListExtra("allProduct", productSelectionLists);
                        view.startActivity(bassket);

                    }
                });

            }
        }

    }
    public void payCard (final BasketActivity view){

        List<PaymentPerformer> paymentPerformers = PaymentPerformerApi.INSTANCE.getAllPaymentPerformers(view.getPackageManager());

        for (PaymentPerformer p : paymentPerformers) {
            if (p.getPaymentSystem().getPaymentSystemId().equals("ru.evotor.paymentSystem.cashless.base")) {
                SellApi.moveCurrentReceiptDraftToPaymentStage(view, p, new ReceiptFormationCallback() {

                    @Override
                    public void onSuccess() {
                        ArrayList<ProductSelectionList> productSelectionLists = view.getIntent().getParcelableArrayListExtra("allProduct");
                        Intent welcome = new Intent(view, WelcomeActivity.class);
                        welcome.putParcelableArrayListExtra("allProduct", productSelectionLists);
                        view.startActivity(welcome);
                    }

                    @Override
                    public void onError(@NotNull ReceiptFormationException e) {
                        Toast.makeText(view, "Ошибка при совершении оплаты", Toast.LENGTH_SHORT).show();

                        ArrayList<ProductSelectionList> productSelectionLists = view.getIntent().getParcelableArrayListExtra("allProduct");
                        Intent bassket = new Intent(view, BasketActivity.class);
                        bassket.putParcelableArrayListExtra("allProduct", productSelectionLists);
                        view.startActivity(bassket);

                    }

                });

            }
        }

    }

    public  List<PositionAdd> createPositionAddList (Activity view, ArrayList<String> uuidList, ArrayList<Integer> amount){

        List<PositionAdd> positionAddList = new ArrayList<>();

        TestDatabase testDatabase = new TestDatabase();
        ArrayList<ProductItem> productItems =  testDatabase.getProductByUuid(view, uuidList);
        List<ProductItem> addItems = testDatabase.getProducItemtByGroupName(preferencesPrecenter.getStringPreferences(view, "ChoiseAdd"), view);

        ArrayList<Boolean> trueAdd = new ArrayList<>();

        for (int i=0; i<productItems.size(); i++){
            trueAdd.add(true);
            for (int j=0; j<addItems.size(); j++){
                if (productItems.get(i).getName().equals(addItems.get(j).getName())){
                    trueAdd.set(i,false);
                }
            }
        }

        List <Position> subPositionToBeAdded = new ArrayList<>();


        for (int i = productItems.size()-1; i>=0; i--){

            ProductItem.Product  product = (ProductItem.Product) productItems.get(i);

            ArrayList<Position> positionToBeAdded = new ArrayList<>();

            if (trueAdd.get(i)==true){
                if (subPositionToBeAdded.isEmpty()){
                    positionToBeAdded.add(Position.Builder.newInstance(product, BigDecimal.valueOf(amount.get(i))).build());
                    positionAddList.add(new PositionAdd(positionToBeAdded.get(0)));
                }
                else{
                    positionToBeAdded.add(Position.Builder.newInstance(product, BigDecimal.valueOf(amount.get(i))).setSubPositions(subPositionToBeAdded).build());
                    subPositionToBeAdded.clear();
                    positionAddList.add(new PositionAdd(positionToBeAdded.get(0)));
                }
            }

            else{
                subPositionToBeAdded.add(Position.Builder.newInstance(product, BigDecimal.valueOf(amount.get(i))).build());
            }

        }

        Collections.reverse(positionAddList);
        return positionAddList;
    }

    public void printPreСheck (final Activity view, List<PositionAdd> positionAddList, String sum, boolean type){

        mList = recipteToArray(positionAddList);

        String [] s = getTitlePreCheck(view);

        mList.add(0, " ");
        mList.add(1, " ");
        mList.add(2, s[0]);
        mList.add(3, " ");
        mList.add(4, s[1]);
        mList.add(5, " ");
        mList.add(6, " ");

        char [] array = new char[16-sum.length()];
        Arrays.fill( array, ' ' );
        if (type==true)  sum = "Итого, НАЛИЧНЫМИ" +String.copyValueOf(array) + sum;
        else             sum = "Итого, ПО КАРТЕ." +String.copyValueOf(array) + sum;

        mList.add(" ");
        mList.add(sum);
        mList.add(" ");
        mList.add(" ");
        mList.add(" ");


        if (mList.size() > 0) {
            final List<IPrintable> pList = new ArrayList<>();
            for (String item : mList) {
                //Печать текста
                pList.add(new PrintableText(item));
            }

            new Thread() {
                    @Override
                    public void run() {
                        try {
                            DeviceServiceConnector.getPrinterService().printDocument(
                                    ru.evotor.devices.commons.Constants.DEFAULT_DEVICE_INDEX,
                                    new PrinterDocument(pList.toArray(new IPrintable[pList.size()])));

                        } catch (DeviceServiceException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
        }
    }

    public ArrayList<String> recipteToArray (List <PositionAdd> positions){

        ArrayList<String> mList = new ArrayList<>();

        for (PositionAdd i:positions){

            String name20 = i.getPosition().getName();
            if (name20.length() >= 20){
                name20 = name20.substring(0,20);
            }
            else {
                char [] array = new char[20-name20.length()];
                Arrays.fill( array, ' ' );
                name20 = name20+ String.copyValueOf(array);
            }

            String cost7 = i.getPosition().getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();

            cost7 = String.valueOf(Double.valueOf(cost7)*i.getPosition().getQuantity().setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue());

            if (cost7.length() >= 10){
                cost7 = cost7.substring(0,10);
            }
            else {
                char [] array = new char[7-cost7.length()];
                Arrays.fill( array, ' ' );
                cost7 = String.copyValueOf(array) + cost7;

            }

            String quantity = i.getPosition().getQuantity().setScale(0, BigDecimal.ROUND_HALF_EVEN).toString();

            if (quantity.length() >= 2){
                quantity.substring(0,2);
            }
            else {
                quantity = " " + quantity;
            }

            String positionRecipt = "- "+ name20  +quantity+ "x" + cost7 ;
            mList.add(positionRecipt);

            List<Position> j = i.getPosition().getSubPositions();
            if (j != null)  {
                mList = subPositionToArray(j, mList);
            }
        }
        return mList;
    }

    public ArrayList<String> subPositionToArray (List<Position> positions, ArrayList<String> mList) {

        for (Position i:positions){


            String name15 = i.getName();
            if (name15.length() >= 15){
                name15 = name15.substring(0,15);
            }
            else {
                char [] array = new char[15-name15.length()];
                Arrays.fill( array, ' ' );
                name15 = name15+ String.copyValueOf(array);
            }

            String cost7 = i.getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();

            cost7 = String.valueOf(Double.valueOf(cost7)*i.getQuantity().setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue());


            if (cost7.length() >= 10){
                cost7.substring(0,15);
            }
            else {
                char [] array = new char[7-cost7.length()];
                Arrays.fill( array, ' ' );
                cost7 = String.copyValueOf(array) + cost7;

            }

            String quantity = i.getQuantity().setScale(0, BigDecimal.ROUND_HALF_EVEN).toString();

            if (quantity.length() >= 2){
                quantity.substring(0,2);
            }
            else {
                quantity = "." + quantity;
            }

            String positionRecipt = "       "+ name15 + quantity +"x" +cost7 ;
            mList.add(positionRecipt);
        }
        return mList;
    }


    public String []  getTitlePreCheck (Activity view){
        int lineNumber;
        String lineTime;

        Date currentTime = Calendar.getInstance().getTime();


        lineTime ="          " + currentTime.getDate() + " " + getMonthName(currentTime.getMonth()) + ", " + currentTime.getHours() +":" + currentTime.getMinutes();

        if(preferencesPrecenter.contains(view, "NumberPreCheck") == false){
            lineNumber = 1;
            preferencesPrecenter.setStringPreferences(view, "NumberPreCheck", String.valueOf(lineNumber));
            preferencesPrecenter.setStringPreferences(view, "DatePreCheck", String.valueOf(currentTime.getDay()));
        }
        else {
            String datePreCheck = preferencesPrecenter.getStringPreferences(view,"DatePreCheck");
            String numberPreCheck = preferencesPrecenter.getStringPreferences(view,"NumberPreCheck");

            if (Integer.valueOf(datePreCheck) == currentTime.getDay()-1){
                lineNumber = 1;
                preferencesPrecenter.setStringPreferences(view, "NumberPreCheck", String.valueOf(lineNumber));
                preferencesPrecenter.setStringPreferences(view, "DatePreCheck", String.valueOf(currentTime.getDay()));

            } else {
                lineNumber = Integer.valueOf(numberPreCheck)+1;
                preferencesPrecenter.setStringPreferences(view, "NumberPreCheck", String.valueOf(lineNumber));
                preferencesPrecenter.setStringPreferences(view, "DatePreCheck", String.valueOf(currentTime.getDay()));
            }
        }

        String[] result = new String[2];
        result[0]="            Пречек №" + lineNumber;
        result[1]=lineTime;

        return result;


    }

    private static String getMonthName (int month) {

        String monthName = null;
        switch (month) {
            case 0:
                monthName = "Января";
                break;
            case 1:
                monthName = "Февраля";
                break;
            case 2:
                monthName = "Марта";
                break;
            case 3:
                monthName = "Апреля";
                break;
            case 4:
                monthName = "Мая";
                break;
            case 5:
                monthName = "Июня";
                break;
            case 6:
                monthName = "Июля";
                break;
            case 7:
                monthName = "Августа";
                break;
            case 8:
                monthName = "Сентября";
                break;
            case 9:
                monthName = "Октября";
                break;
            case 10:
                monthName = "Ноября";
                break;
            case 11:
                monthName = "Декабря";
                break;
        }

        return monthName;
    }

    public void soundSignal (BasketActivity basketActivity) {

        int i = 0;
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(basketActivity, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
