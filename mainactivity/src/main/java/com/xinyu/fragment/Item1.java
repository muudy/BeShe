package com.xinyu.fragment;

import java.io.Serializable;
import java.util.UUID;

public class Item1 implements Serializable {
    private UUID mId;
    //private String mTitle;
    private String mMoney;
    private String mDate;

    public Item1(String money, String date) {
        mId = UUID.randomUUID();
        //mTitle = title;
        mMoney = money;
        mDate = date;
    }

    public Item1(UUID id, String money, String date) {
        mId = id;
        //mTitle = title;
        mMoney = money;
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }



    public String getMoney() {
        return mMoney;
    }

    public void setMoney(String money) {
        mMoney = money;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "Item1{" +
                ", mMoney='" + mMoney + '\'' +
                ", mDate='" + mDate + '\'' +
                '}';
    }
}
