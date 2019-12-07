package com.ivanilov.screen.Presenter.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProductSelectionList implements Parcelable {

    private String groupName;
    private ArrayList <String> name;
    private ArrayList <String>  price;
    private ArrayList <Integer> amount;

    public ProductSelectionList(String groupName, ArrayList <String>  name, ArrayList <String>  price, ArrayList <Integer> amount){
        this.groupName = groupName;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }


    protected ProductSelectionList(Parcel in) {
        groupName = in.readString();
        name = in.createStringArrayList();
        price = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeStringList(name);
        dest.writeStringList(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductSelectionList> CREATOR = new Creator<ProductSelectionList>() {
        @Override
        public ProductSelectionList createFromParcel(Parcel in) {
            return new ProductSelectionList(in);
        }

        @Override
        public ProductSelectionList[] newArray(int size) {
            return new ProductSelectionList[size];
        }
    };

    public String getGroupNameName (){
        return groupName;
    }

    public void setGroupName (String groupName){
        this.groupName = groupName;
    }

    public ArrayList <String> getName (){
        return name;
    }

    public void setName (ArrayList <String> name){
        this.name = name;
    }


    public ArrayList <String> getPrice (){
        return price;
    }

    public void setPrice (ArrayList <String> price){
        this.price = price;
    }

    public ArrayList <Integer> getAmount (){
        return amount;
    }

    public void setAmount (ArrayList <Integer> amount){
        this.amount = amount;
    }

}
