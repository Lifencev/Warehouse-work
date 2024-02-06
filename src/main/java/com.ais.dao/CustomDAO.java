package com.ais.dao;

import com.ais.constants.Attribute;
import com.ais.model.SaleCounter;
import com.ais.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomDAO implements IDAO{
    private static final String COUNT_ALL_EMPL_TOUCHED = "SELECT sale.UPC, COUNT(sale.UPC) AS times_sold " +
            "FROM sale " +
            "GROUP BY sale.UPC " +
            "HAVING sale.check_number IN (" +
            "SELECT check_number " +
            "FROM `check` " +
            "WHERE check.id_employee IN (" +
            "SELECT id_employee " +
            "FROM `employee` " +
            "WHERE empl_surname = ? " +
            "))";
    private static final String SELECT_ALL_WITH_PRODUCT = "SELECT * " +
            "FROM Product " +
            "WHERE id_product IN(" +
            "SELECT id_product " +
            "FROM Store_product " +
            "WHERE id_product NOT IN (" +
            "SELECT id_product " +
            "FROM Store_Product INNER JOIN Sale ON Store_Product.UPC = Sale.UPC " +
            "WHERE check_number NOT IN( " +
            "SELECT check_number " +
            "FROM Sale " +
            "WHERE UPC IN (" +
            "SELECT UPC " +
            "FROM Product INNER JOIN Store_Product ON Product.id_product = Store_Product.id_product " +
            "WHERE product_name = ? " +
            ")" +
            ")" +
            ")" +
            ")";
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
    public List<Product> selectProductsWithX(int idProductOld){
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WITH_PRODUCT)) {
            preparedStatement.setInt(1, idProductOld);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                int categoryNumber = resultSet.getInt(Attribute.CATEGORY_NUMBER);
                String productName = resultSet.getString(Attribute.PRODUCT_NAME);
                String characteristics = resultSet.getString(Attribute.PRODUCT_CHARACTERISTICS);
                products.add(new Product(idProduct, categoryNumber, productName, characteristics));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<SaleCounter> countTimesEmplSoldX(String emplSurname){
        List<SaleCounter> saleCounters = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_EMPL_TOUCHED)) {
            preparedStatement.setString(1, emplSurname);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);

                int count = resultSet.getInt("times_sold");
                saleCounters.add(new SaleCounter(UPC, count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleCounters;
    }
}
