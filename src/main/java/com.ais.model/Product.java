package com.ais.model;

public class Product {

    private int idProduct;
    private final int categoryNumber;
    private final String productName;
    private final String characteristics;

    public Product(int idProduct, int categoryNumber, String productName, String characteristics) {
        this.idProduct = idProduct;
        this.categoryNumber = categoryNumber;
        this.productName = productName;
        this.characteristics = characteristics;
    }

    public Product(int categoryNumber, String productName, String characteristics) {
        this.categoryNumber = categoryNumber;
        this.productName = productName;
        this.characteristics = characteristics;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public String getProductName() {
        return productName;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public int getIdProduct() {
        return idProduct;
    }
}