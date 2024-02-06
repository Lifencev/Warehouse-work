package com.ais.dao;

import com.ais.constants.Attribute;
import com.ais.model.Check;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckDAO implements IDAO {

    private static final String CREATE = "INSERT INTO `check` (check_number, id_employee, card_number, print_date, sum_total, vat) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_WITH_NO_CARD_NUMBER = "INSERT INTO `check` (check_number, id_employee, print_date, sum_total, vat) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM `check` WHERE check_number=?";
    private static final String GET_ALL = "SELECT * FROM `check` ORDER BY print_date";
    private static final String UPDATE = "UPDATE `check` SET id_employee=?, card_number=?, print_date=?, sum_total=?, vat=? WHERE check_number=?";
    private static final String DELETE = "DELETE FROM `check` WHERE check_number=?";

    private static final String GET_ALL_BY_CASHIER_AND_PERIOD = "SELECT * FROM `check` WHERE id_employee =? AND print_date BETWEEN ? AND ? ORDER BY print_date";
    private static final String GET_ALL_BY_PERIOD = "SELECT * FROM `check` WHERE print_date BETWEEN ? AND ? ORDER BY print_date";
    private static final String GET_ALL_BY_CASHIER_FOR_THE_DAY = "SELECT * FROM `check` WHERE id_employee =? AND DATE(print_date) = CURRENT_DATE";

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

    public void insertCheck(Check check) {
         if (check.getCardNumber().equals("")) {
             try(Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_WITH_NO_CARD_NUMBER)) {
                 preparedStatement.setString(1, check.getCheckNumber());
                 preparedStatement.setString(2, check.getIdEmployee());
                 preparedStatement.setTimestamp(3, check.getPrintDate());
                 preparedStatement.setDouble(4, check.getSumTotal());
                 preparedStatement.setDouble(5, check.getVat());
                 preparedStatement.executeUpdate();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }else{
            try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
                preparedStatement.setString(1, check.getCheckNumber());
                preparedStatement.setString(2, check.getIdEmployee());
                preparedStatement.setString(3, check.getCardNumber());
                preparedStatement.setTimestamp(4, check.getPrintDate());
                preparedStatement.setDouble(5, check.getSumTotal());
                preparedStatement.setDouble(6, check.getVat());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateCheck(Check check) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, check.getIdEmployee());
            statement.setString(2, check.getCardNumber());
            statement.setTimestamp(3, check.getPrintDate());
            statement.setDouble(4, check.getSumTotal());
            statement.setDouble(5, check.getVat());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public Check selectCheck(String checkNumber){
        Check check = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setString(1, checkNumber);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                Timestamp printDate = resultSet.getTimestamp(Attribute.CHECK_PRINT_DATE);
                double sumTotal = resultSet.getDouble(Attribute.CHECK_SUM_TOTAL);
                double vat = resultSet.getDouble(Attribute.CHECK_VAT);
                check = new Check(checkNumber, idEmployee, cardNumber, printDate, sumTotal, vat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public List<Check> selectAllChecks(){
        List<Check> checks = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String checkNumber = resultSet.getString(Attribute.CHECK_NUMBER);
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                Timestamp printDate = resultSet.getTimestamp(Attribute.CHECK_PRINT_DATE);
                double sumTotal = resultSet.getDouble(Attribute.CHECK_SUM_TOTAL);
                double vat = resultSet.getDouble(Attribute.CHECK_VAT);
                checks.add(new Check(checkNumber, idEmployee, cardNumber, printDate, sumTotal, vat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    public boolean deleteCheck(String checkNumber) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setString(1, checkNumber);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<Check> selectAllChecksByCashierAndPeriod(String idEmployee, LocalDate fromDate, LocalDate toDate){
        List<Check> checks = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_CASHIER_AND_PERIOD)) {
            preparedStatement.setString(1, idEmployee);
            preparedStatement.setDate(2, Date.valueOf(fromDate));
            preparedStatement.setDate(3, Date.valueOf(toDate));
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String checkNumber = resultSet.getString(Attribute.CHECK_NUMBER);
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                Timestamp printDate = resultSet.getTimestamp(Attribute.CHECK_PRINT_DATE);
                double sumTotal = resultSet.getDouble(Attribute.CHECK_SUM_TOTAL);
                double vat = resultSet.getDouble(Attribute.CHECK_VAT);
                checks.add(new Check(checkNumber, idEmployee, cardNumber, printDate, sumTotal, vat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    public List<Check> selectAllChecksByPeriod(LocalDate fromDate, LocalDate toDate){
        List<Check> checks = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_PERIOD)) {
            preparedStatement.setDate(1, Date.valueOf(fromDate));
            preparedStatement.setDate(2, Date.valueOf(toDate));
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String checkNumber = resultSet.getString(Attribute.CHECK_NUMBER);
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                Timestamp printDate = resultSet.getTimestamp(Attribute.CHECK_PRINT_DATE);
                double sumTotal = resultSet.getDouble(Attribute.CHECK_SUM_TOTAL);
                double vat = resultSet.getDouble(Attribute.CHECK_VAT);
                checks.add(new Check(checkNumber, idEmployee, cardNumber, printDate, sumTotal, vat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    public List<Check> selectChecksByCashierForTheDay(String idEmployee, LocalDate date){
        List<Check> checks = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_CASHIER_FOR_THE_DAY)) {
            preparedStatement.setString(1, idEmployee);
            preparedStatement.setDate(2, Date.valueOf(date));
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String checkNumber = resultSet.getString(Attribute.CHECK_NUMBER);
                String cardNumber = resultSet.getString(Attribute.CARD_NUMBER);
                Timestamp printDate = resultSet.getTimestamp(Attribute.CHECK_PRINT_DATE);
                double sumTotal = resultSet.getDouble(Attribute.CHECK_SUM_TOTAL);
                double vat = resultSet.getDouble(Attribute.CHECK_VAT);
                checks.add(new Check(checkNumber, idEmployee, cardNumber, printDate, sumTotal, vat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }
}