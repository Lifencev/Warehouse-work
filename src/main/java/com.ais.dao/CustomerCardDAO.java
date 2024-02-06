package com.ais.dao;

import com.ais.auxiliary.CustomerShortInfo;
import com.ais.constants.Attribute;
import com.ais.model.CustomerCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerCardDAO implements IDAO {

    private static final String CREATE = "INSERT INTO customer_card (card_number,cust_surname, cust_name, cust_patronymic, phone_number, city, street, zip_code, percent) " + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM customer_card WHERE card_number=?";
    private static final String GET_ALL = "SELECT * FROM customer_card ORDER BY cust_surname";
    private static final String UPDATE = "UPDATE customer_card SET cust_surname=?, cust_name=?, cust_patronymic=?, phone_number=?, city=?, street=?, zip_code=?, percent=? WHERE card_number=?";
    private static final String DELETE = "DELETE FROM customer_card WHERE card_number=?";

    private static final String GET_ALL_BY_PERCENT = "SELECT * FROM customer_card WHERE percent =? ORDER BY cust_surname";
    //private static final String GET_BY_SURNAME = "SELECT * FROM customer_card WHERE cust_surname LIKE '%' || ? || '%'";
    private static final String GET_BY_SURNAME = "SELECT * FROM customer_card WHERE cust_surname LIKE CONCAT('%', ?, '%')";
    private static final String GET_ALL_WHO_ONCE_BOUGHT_ONLY_FROM_ONE_CATEGORY =
                    "SELECT card_number, cust_surname, cust_name " +
                    "FROM customer_card " +
                    "WHERE card_number IN (SELECT card_number " +
                                          "FROM `check` WHERE NOT EXISTS (SELECT * " +
                                                                       "FROM sale " +
                                                                       "WHERE `check`.check_number = sale.check_number " +
                                                                       "AND UPC NOT IN (SELECT UPC " +
                                                                                       "FROM store_product " +
                                                                                       "WHERE id_product IN (SELECT id_product " +
                                                                                                           "FROM product " +
                                                                                                           "WHERE category_number IN (SELECT category_number " +
                                                                                                                                    "FROM category " +
                                                                                                                                    "WHERE category_name =?" +
                                                                                                                                    ")" +
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

    public void insertCustomerCard(CustomerCard customerCard) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, customerCard.getCardNumber());
            preparedStatement.setString(2, customerCard.getCustSurname());
            preparedStatement.setString(3, customerCard.getCustName());
            preparedStatement.setString(4, customerCard.getCustPatronymic());
            System.out.println(customerCard.getCustPatronymic());
            preparedStatement.setString(5, customerCard.getPhoneNumber());
            preparedStatement.setString(6, customerCard.getCity());
            preparedStatement.setString(7, customerCard.getStreet());
            preparedStatement.setString(8, customerCard.getZipCode());
            preparedStatement.setInt(9, customerCard.getPercent());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateCustomerCard(CustomerCard customerCard) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, customerCard.getCustSurname());
            statement.setString(2, customerCard.getCustName());
            statement.setString(3, customerCard.getCustPatronymic());
            System.out.println(customerCard.getCustPatronymic());
            statement.setString(4, customerCard.getPhoneNumber());
            statement.setString(5, customerCard.getCity());
            statement.setString(6, customerCard.getStreet());
            statement.setString(7, customerCard.getZipCode());
            statement.setInt(8, customerCard.getPercent());
            statement.setString(9, customerCard.getCardNumber());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public CustomerCard selectCustomerCard(String cardNumber){
        CustomerCard customerCard = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setString(1, cardNumber);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String custSurname = resultSet.getString(Attribute.CUSTOMER_SURNAME);
                String custName = resultSet.getString(Attribute.CUSTOMER_NAME);
                String custPatronymic = resultSet.getString(Attribute.CUSTOMER_PATRONYMIC);
                String phoneNumber = resultSet.getString(Attribute.CUSTOMER_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.CUSTOMER_CITY);
                String street = resultSet.getString(Attribute.CUSTOMER_STREET);
                String zipCode = resultSet.getString(Attribute.CUSTOMER_ZIP_CODE);
                int percent = Integer.parseInt(resultSet.getString(Attribute.CUSTOMER_PERCENT));
                customerCard = new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCard;
    }

    public List<CustomerCard> selectAllCustomerCards(){
        List<CustomerCard> customerCards = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                String custSurname = resultSet.getString(Attribute.CUSTOMER_SURNAME);
                String custName = resultSet.getString(Attribute.CUSTOMER_NAME);
                String custPatronymic = resultSet.getString(Attribute.CUSTOMER_PATRONYMIC);
                String phoneNumber = resultSet.getString(Attribute.CUSTOMER_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.CUSTOMER_CITY);
                String street = resultSet.getString(Attribute.CUSTOMER_STREET);
                String zipCode = resultSet.getString(Attribute.CUSTOMER_ZIP_CODE);
                int percent = resultSet.getInt(Attribute.CUSTOMER_PERCENT);
                customerCards.add(new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCards;
    }

    public boolean deleteCustomerCard(String cardNumber) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setString(1, cardNumber);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<CustomerCard> selectAllCustomerCardsByPercent(int percent){
        List<CustomerCard> customerCards = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_PERCENT)) {
            preparedStatement.setInt(1, percent);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                String custSurname = resultSet.getString(Attribute.CUSTOMER_SURNAME);
                String custName = resultSet.getString(Attribute.CUSTOMER_NAME);
                String custPatronymic = resultSet.getString(Attribute.CUSTOMER_PATRONYMIC);
                String phoneNumber = resultSet.getString(Attribute.CUSTOMER_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.CUSTOMER_CITY);
                String street = resultSet.getString(Attribute.CUSTOMER_STREET);
                String zipCode = resultSet.getString(Attribute.CUSTOMER_ZIP_CODE);
                customerCards.add(new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCards;
    }

    public List<CustomerCard> selectBySurname(String surname){
        List<CustomerCard> customerCards = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_SURNAME)) {
            preparedStatement.setString(1, surname);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                String custSurname = resultSet.getString(Attribute.CUSTOMER_SURNAME);
                String custName = resultSet.getString(Attribute.CUSTOMER_NAME);
                String custPatronymic = resultSet.getString(Attribute.CUSTOMER_PATRONYMIC);
                String phoneNumber = resultSet.getString(Attribute.CUSTOMER_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.CUSTOMER_CITY);
                String street = resultSet.getString(Attribute.CUSTOMER_STREET);
                String zipCode = resultSet.getString(Attribute.CUSTOMER_ZIP_CODE);
                int percent = resultSet.getInt(Attribute.CUSTOMER_PERCENT);
                customerCards.add(new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCards;
    }

    public List<CustomerShortInfo> selectAllWhoOnceBoughtOnlyFromOneCategory(String categoryName){
        List<CustomerShortInfo> customerCards = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_WHO_ONCE_BOUGHT_ONLY_FROM_ONE_CATEGORY)) {
            preparedStatement.setString(1, categoryName);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                String custSurname = resultSet.getString(Attribute.CUSTOMER_SURNAME);
                String custName = resultSet.getString(Attribute.CUSTOMER_NAME);
                customerCards.add(new CustomerShortInfo(cardNumber, custSurname, custName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCards;
    }
}