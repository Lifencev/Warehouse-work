package com.ais.model;

public class StoreProduct {

    private String UPC;
    private final String UPCProm;
    private final int idProduct;
    private final double sellingPrice;
    private final int productsNumber;
    private final boolean promotionalProduct;

    public StoreProduct(String UPC, String UPCProm, int idProduct,
                        double sellingPrice, int productsNumber, boolean promotionalProduct) {
        this.UPC = UPC;
        this.UPCProm = UPCProm;
        this.idProduct = idProduct;
        this.sellingPrice = sellingPrice;
        this.productsNumber = productsNumber;
        this.promotionalProduct = promotionalProduct;
    }

    public StoreProduct(String UPCProm, int idProduct, double sellingPrice, int productsNumber, boolean promotionalProduct) {
        this.UPCProm = UPCProm;
        this.idProduct = idProduct;
        this.sellingPrice = sellingPrice;
        this.productsNumber = productsNumber;
        this.promotionalProduct = promotionalProduct;
    }

    public String getUPC() {
        return UPC;
    }

    public String getUPCProm() {
        return UPCProm;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getProductsNumber() {
        return productsNumber;
    }

    public boolean isPromotionalProduct() {
        return promotionalProduct;
    }
}