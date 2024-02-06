package com.ais.dao;

import com.ais.constants.Attribute;
import com.ais.model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO implements IDAO {

    private static final String CREATE = "INSERT INTO sale (UPC, check_number, products_number, selling_price) VALUES (?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM sale WHERE UPC=? AND check_number=?";
    private static final String GET_BY_CHECK = "SELECT * FROM sale WHERE check_number=?";
    private static final String GET_ALL = "SELECT * FROM sale ORDER BY check_number";
    private static final String UPDATE = "UPDATE sale SET products_number=?, selling_price=? WHERE WHERE UPC=? AND check_number=?";
    private static final String DELETE = "DELETE FROM sale WHERE UPC=? AND check_number=?";

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

    public void insertSale(Sale sale) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, sale.getUPC());
            preparedStatement.setString(2, sale.getCheckNumber());
            preparedStatement.setInt(3, sale.getProductNumber());
            preparedStatement.setDouble(4, sale.getSellingPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateSale(Sale sale) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setInt(1, sale.getProductNumber());
            statement.setDouble(2, sale.getSellingPrice());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public Sale selectSale(String UPC, String checkNumber){
        Sale sale = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setString(1, UPC);
            preparedStatement.setString(2, checkNumber);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int productsNumber = resultSet.getInt(Attribute.SALE_PRODUCTS_NUMBER);
                double sellingPrice = resultSet.getDouble(Attribute.SALE_SELLING_PRICE);
                sale = new Sale(UPC, checkNumber, productsNumber, sellingPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }

    public List<Sale> selectAllSales(){
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String checkNumber = resultSet.getString(Attribute.CHECK_NUMBER);
                int productsNumber = resultSet.getInt(Attribute.SALE_PRODUCTS_NUMBER);
                double sellingPrice = resultSet.getDouble(Attribute.SALE_SELLING_PRICE);
                sales.add(new Sale(UPC, checkNumber, productsNumber, sellingPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
    public List<Sale> selectByCheckNumber(String checkNumber1){
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CHECK)) {
            preparedStatement.setString(1, checkNumber1);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String UPC = resultSet.getString(Attribute.UPC);
                String checkNumber = resultSet.getString(Attribute.CHECK_NUMBER);
                int productsNumber = resultSet.getInt(Attribute.SALE_PRODUCTS_NUMBER);
                double sellingPrice = resultSet.getDouble(Attribute.SALE_SELLING_PRICE);
                sales.add(new Sale(UPC, checkNumber, productsNumber, sellingPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    public boolean deleteSale(String UPC, String checkNumber) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setString(1, UPC);
            statement.setString(2, checkNumber);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
