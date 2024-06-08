package com.android.mywatchlist.models;

import java.util.ArrayList;

public class ItemSearchModel {
    private ArrayList<ItemSearchResultModel> results;
    private int page_no;
    private int total_pages;
    private int result_size;

    public ItemSearchModel() {
    }

    // Getters
    public ArrayList<ItemSearchResultModel> getResults(){
        return results;
    }
    public  int getPage_no() {
        return page_no;
    }
    public  int getTotal_pages() {
        return total_pages;
    }
    public int getResult_size() {
        return result_size;
    }

    // Setters
    public void setPage_no(int page) {
        this.page_no = page;
    }
    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
    public void setResult_size(int result_size) {
        this.result_size = result_size;
    }
}
