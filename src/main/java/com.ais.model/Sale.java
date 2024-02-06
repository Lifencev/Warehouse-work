package com.ais.model;

public class Sale {

    private String UPC;
    private String checkNumber;
    private final int productNumber;
    private final double sellingPrice;

    public Sale(String UPC, String checkNumber, int productNumber, double sellingPrice) {
        this.UPC = UPC;
        this.checkNumber = checkNumber;
        this.productNumber = productNumber;
        this.sellingPrice = sellingPrice;
    }

    public Sale(int productNumber, double sellingPrice) {
        this.productNumber = productNumber;
        this.sellingPrice = sellingPrice;
    }

    public String getUPC() {
        return UPC;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
}