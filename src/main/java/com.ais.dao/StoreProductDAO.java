package com.ais.dao;

import com.ais.constants.Attribute;
import com.ais.model.StoreProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreProductDAO implements IDAO {

    private static final String CREATE = "INSERT INTO store_product (UPC, UPC_prom, id_product, selling_price, products_number, promotional_product) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_WITH_NO_UPC_PROM = "INSERT INTO store_product (UPC, id_product, selling_price, products_number, promotional_product) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BY_UPC = "SELECT * FROM store_product WHERE UPC=?";
    private static final String GET_ALL = "SELECT * FROM store_product ORDER BY UPC";
    private static final String UPDATE = "UPDATE store_product SET id_product=?, selling_price=?, products_number=?, promotional_product=? WHERE UPC=?";
    private static final String DELETE = "DELETE FROM store_product WHERE UPC=?";
    private static final String GET_ALL_BY_QUANTITY = "SELECT * FROM store_product ORDER BY products_number DESC";
    private static final String GET_ALL_PROMOTIONAL_BY_QUANTITY = "SELECT * FROM store_product WHERE promotional_product = true ORDER BY products_number";
    private static final String GET_ALL_PROMOTIONAL_BY_PRODUCT_NAME = "SELECT * FROM `store_product` JOIN `product` ON `store_product`.id_product = `product`.id_product WHERE promotional_product = true ORDER BY product_name";
    //private static final String GET_ALL_PROMOTIONAL_BY_PRODUCT_NAME = "SELECT UPC, UPC_prom, store_product.id_product, selling_price, products_number, promotional_product FROM store_product JOIN product ON store_product.id_product = product.id_product WHERE UPC =?";
    private static final String GET_ALL_NON_PROMOTIONAL_BY_QUANTITY = "SELECT * FROM store_product WHERE promotional_product = false ORDER BY products_number";
    private static final String GET_ALL_NON_PROMOTIONAL_BY_PRODUCT_NAME = "SELECT UPC, UPC_prom, store_product.id_product, product_name, characteristics, promotional_product, selling_price, products_number FROM store_product JOIN product ON store_product.id_product = product.id_product WHERE promotional_product = false ORDER BY product_name";
    private static final String GET_ALL_BY_PRODUCT_NAME = "SELECT UPC, UPC_prom, store_product.id_product, product_name, selling_price, products_number, promotional_product FROM store_product JOIN product ON store_product.id_product = product.id_product ORDER BY product_name";
    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertStoreProduct(StoreProduct storeProduct) {
        if (storeProduct.getUPCProm().equals("")) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_WITH_NO_UPC_PROM)) {
                preparedStatement.setString(1, storeProduct.getUPC());
                preparedStatement.setInt(2, storeProduct.getIdProduct());
                preparedStatement.setDouble(3, storeProduct.getSellingPrice());
                preparedStatement.setInt(4, storeProduct.getProductsNumber());
                preparedStatement.setBoolean(5, storeProduct.isPromotionalProduct());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
                preparedStatement.setString(1, storeProduct.getUPC());
                preparedStatement.setString(2, storeProduct.getUPCProm());
                preparedStatement.setInt(3, storeProduct.getIdProduct());
                preparedStatement.setDouble(4, storeProduct.getSellingPrice());
                preparedStatement.setInt(5, storeProduct.getProductsNumber());
                preparedStatement.setBoolean(6, storeProduct.isPromotionalProduct());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateStoreProduct(StoreProduct storeProduct) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setInt(1, storeProduct.getIdProduct());
            statement.setDouble(2, storeProduct.getSellingPrice());
            statement.setInt(3, storeProduct.getProductsNumber());
            statement.setBoolean(4, storeProduct.isPromotionalProduct());
            System.out.println(storeProduct.isPromotionalProduct());
            statement.setString(5, storeProduct.getUPC());
            rowUpdated = statement.executeUpdate() > 0;
        }
        System.out.println(rowUpdated);
        return rowUpdated;
    }

    public StoreProduct selectStoreProduct(String UPC){
        StoreProduct storeProduct = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_UPC)) {
            preparedStatement.setString(1, UPC);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProduct = new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct);
                System.out.println("+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProduct;
    }

    public List<StoreProduct> selectAllStoreProducts(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }

    public boolean deleteStoreProduct(String UPC) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setString(1, UPC);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<StoreProduct> selectAllStoreProductsByQuantity(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_QUANTITY)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }

    public List<StoreProduct> selectAllPromotionalByQuantity(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PROMOTIONAL_BY_QUANTITY)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }

    public List<StoreProduct> selectAllPromotionalByProductName(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PROMOTIONAL_BY_PRODUCT_NAME)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }

    public List<StoreProduct> selectAllNonPromotionalByQuantity(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_NON_PROMOTIONAL_BY_QUANTITY)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }

    public List<StoreProduct> selectAllNonPromotionalByProductName(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_NON_PROMOTIONAL_BY_PRODUCT_NAME)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }

    public List<StoreProduct> selectAllStoreProductsByProductName(){
        List<StoreProduct> storeProducts = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_PRODUCT_NAME)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String UPCProm = resultSet.getString(Attribute.UPC_PROM);
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                double sellingPrice = resultSet.getDouble(Attribute.STORE_PRODUCT_SELLING_PRICE);
                int productsNumber = resultSet.getInt(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
                boolean promotionalProduct = resultSet.getBoolean(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT);
                storeProducts.add(new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProducts;
    }
}