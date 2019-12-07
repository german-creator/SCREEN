package com.ivanilov.screen.Presenter.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class BasketItem implements Parcelable {

    private String name;
    private String price;
    private String uuid;
    private int amount;
    private boolean add;

    public BasketItem (String name, String price, String uuid, int amount, Boolean add) {
        this.name = name;
        this.price = price;
        this.uuid = uuid;
        this.amount = amount;
        this.add = add;
    }

    public  String getName (){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }


    public String getPrice (){
        return price;
    }

    public void setPrice (String price){
        this.price = price;
    }

    public String getUuid (){
        return uuid;
    }

    public void setUuid (String uuid){ this.uuid = uuid; }

    public int getAmount (){
        return amount;
    }

    public void setAdd(boolean add){
        this.add = add;
    }

    public boolean getAdd (){
        return add;
    }

    public void setAmount (int amount){
        this.amount = amount;
    }

    protected BasketItem(Parcel in) {
        name = in.readString();
        price = in.readString();
        uuid = in.readString();
        amount = in.readInt();
        add = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(uuid);
        dest.writeInt(amount);
        dest.writeByte((byte) (add ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BasketItem> CREATOR = new Parcelable.Creator<BasketItem>() {
        @Override
        public BasketItem createFromParcel(Parcel in) {
            return new BasketItem(in);
        }

        @Override
        public BasketItem[] newArray(int size) {
            return new BasketItem[size];
        }
    };
}