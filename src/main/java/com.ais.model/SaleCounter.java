package com.ais.model;

public class SaleCounter {
    private String UPC;
    private int count;
    public SaleCounter(String UPC, int count){
        this.UPC=UPC;
        this.count=count;
    }
    public String getUPC() {
        return UPC;
    }
    public int getCount(){
        return count;
    }
}
