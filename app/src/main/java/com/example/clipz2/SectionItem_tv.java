package com.example.clipz2;


import com.example.clipz2.Class.SingleItem_tv;

import java.util.ArrayList;

public class SectionItem_tv {

    private String headerTitle;
    private ArrayList<SingleItem_tv> singleItemList_tv;

    public SectionItem_tv() {
    }

    public SectionItem_tv(String headerTitle, ArrayList<SingleItem_tv> singleItem_tvs) {
        this.headerTitle = headerTitle;
        this.singleItemList_tv = singleItem_tvs;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItem_tv> getSingleItemList_tv() {
        return singleItemList_tv;
    }

    public void setSingleItemList_tv(ArrayList<SingleItem_tv> singleItemList_tv) {
        this.singleItemList_tv = singleItemList_tv;
    }
}
