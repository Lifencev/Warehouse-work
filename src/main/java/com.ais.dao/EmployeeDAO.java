package com.ais.dao;

import com.ais.auxiliary.EmployeeShortInfo;
import com.ais.constants.Attribute;
import com.ais.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IDAO {

    private static final String CREATE = "INSERT INTO employee (id_employee, empl_surname, empl_name, empl_patronymic, empl_role, salary, date_of_birth, date_of_start, " +
            "phone_number, city, street, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    private static final String GET_BY_ID = "SELECT * FROM employee WHERE id_employee=?";
    private static final String GET_ALL = "SELECT * FROM employee ORDER BY empl_surname";
    private static final String UPDATE = "UPDATE employee SET empl_surname=?, empl_name=?, empl_patronymic=?, empl_role=?, salary=?, date_of_birth=?, date_of_start=?, " +
            "phone_number=?, city=?, street=?, zip_code=? WHERE id_employee=?";
    private static final String DELETE = "DELETE FROM employee WHERE id_employee=?";

    private static final String GET_ALL_CASHIERS = "SELECT * FROM employee WHERE empl_role = 'касир' ORDER BY empl_surname";
    private static final String GET_PHONE_NUMBER_AND_ADDRESS_BY_SURNAME = "SELECT * FROM employee WHERE empl_surname =?";
    private static final String GET_TOTAL_SUM_OF_SALES_BY_CASHIER_TO_CUSTOMERS_FROM_HIS_CITY = "SELECT employee.id_employee, empl_surname, empl_name, employee.city, SUM(sum_total) " +
                                                                                               "FROM employee JOIN `check` ON employee.id_employee = `check`.id_employee " +
                                                                                               "WHERE employee.city = (SELECT city " +
                                                                                                                      "FROM customer_card " +
                                                                                                                      "WHERE customer_card.card_number = `check`.card_number" +
                                                                                                                      ")" +
                                                                                               "AND empl_role = 'касир' " +
                                                                                               "GROUP BY `check`.id_employee";

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

    public void insertEmployee(Employee employee) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, employee.getIdEmployee());
            preparedStatement.setString(2, employee.getEmplSurname());
            preparedStatement.setString(3, employee.getEmplName());
            preparedStatement.setString(4, employee.getEmplPatronymic());
            preparedStatement.setString(5, employee.getEmplRole());
            preparedStatement.setDouble(6, employee.getSalary());
            preparedStatement.setDate(7, employee.getDateOfBirth());
            preparedStatement.setDate(8, employee.getDateOfStart());
            preparedStatement.setString(9, employee.getPhoneNumber());
            preparedStatement.setString(10, employee.getCity());
            preparedStatement.setString(11, employee.getStreet());
            preparedStatement.setString(12, employee.getZipCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, employee.getEmplSurname());
            statement.setString(2, employee.getEmplName());
            statement.setString(3, employee.getEmplPatronymic());
            statement.setString(4, employee.getEmplRole());
            statement.setDouble(5, employee.getSalary());
            statement.setDate(6, employee.getDateOfBirth());
            statement.setDate(7, employee.getDateOfStart());
            statement.setString(8, employee.getPhoneNumber());
            statement.setString(9, employee.getCity());
            statement.setString(10, employee.getStreet());
            statement.setString(11, employee.getZipCode());
            statement.setString(12, employee.getIdEmployee());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public Employee selectEmployee(String idEmployee){
        Employee employee = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setString(1, idEmployee);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String emplSurname = resultSet.getString(Attribute.EMPLOYEE_SURNAME);
                String emplName = resultSet.getString(Attribute.EMPLOYEE_NAME);
                String emplPatronymic = resultSet.getString(Attribute.EMPLOYEE_PATRONYMIC);
                String emplRole = resultSet.getString(Attribute.EMPLOYEE_ROLE);
                double salary = resultSet.getDouble(Attribute.EMPLOYEE_SALARY);
                Date dateOfBirth = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_BIRTH);
                Date dateOfStart = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_START);
                String phoneNumber = resultSet.getString(Attribute.EMPLOYEE_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.EMPLOYEE_CITY);
                String street = resultSet.getString(Attribute.EMPLOYEE_STREET);
                String zipCode = resultSet.getString(Attribute.EMPLOYEE_ZIP_CODE);
                employee = new Employee(idEmployee, emplSurname, emplName, emplPatronymic, emplRole,
                        salary, dateOfBirth, dateOfStart, phoneNumber, city, street, zipCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> selectAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String emplSurname = resultSet.getString(Attribute.EMPLOYEE_SURNAME);
                String emplName = resultSet.getString(Attribute.EMPLOYEE_NAME);
                String emplPatronymic = resultSet.getString(Attribute.EMPLOYEE_PATRONYMIC);
                String emplRole = resultSet.getString(Attribute.EMPLOYEE_ROLE);
                double salary = resultSet.getDouble(Attribute.EMPLOYEE_SALARY);
                Date dateOfBirth = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_BIRTH);
                Date dateOfStart = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_START);
                String phoneNumber = resultSet.getString(Attribute.EMPLOYEE_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.EMPLOYEE_CITY);
                String street = resultSet.getString(Attribute.EMPLOYEE_STREET);
                String zipCode = resultSet.getString(Attribute.EMPLOYEE_ZIP_CODE);
                employees.add(new Employee(idEmployee, emplSurname, emplName, emplPatronymic, emplRole,
                        salary, dateOfBirth, dateOfStart, phoneNumber, city, street, zipCode));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public boolean deleteEmployee(String  idEmployee) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setString(1, idEmployee);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<Employee> selectAllCashiers(){
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CASHIERS)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String emplSurname = resultSet.getString(Attribute.EMPLOYEE_SURNAME);
                String emplName = resultSet.getString(Attribute.EMPLOYEE_NAME);
                String emplPatronymic = resultSet.getString(Attribute.EMPLOYEE_PATRONYMIC);
                String emplRole = resultSet.getString(Attribute.EMPLOYEE_ROLE);
                double salary = resultSet.getDouble(Attribute.EMPLOYEE_SALARY);
                Date dateOfBirth = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_BIRTH);
                Date dateOfStart = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_START);
                String phoneNumber = resultSet.getString(Attribute.EMPLOYEE_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.EMPLOYEE_CITY);
                String street = resultSet.getString(Attribute.EMPLOYEE_STREET);
                String zipCode = resultSet.getString(Attribute.EMPLOYEE_ZIP_CODE);
                employees.add(new Employee(idEmployee, emplSurname, emplName, emplPatronymic, emplRole,
                        salary, dateOfBirth, dateOfStart, phoneNumber, city, street, zipCode));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    public List<Employee> selectPhoneNumberAndAddressBySurname(String surname){
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_PHONE_NUMBER_AND_ADDRESS_BY_SURNAME)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String emplName = resultSet.getString(Attribute.EMPLOYEE_NAME);
                String emplPatronymic = resultSet.getString(Attribute.EMPLOYEE_PATRONYMIC);
                String emplRole = resultSet.getString(Attribute.EMPLOYEE_ROLE);
                double salary = resultSet.getDouble(Attribute.EMPLOYEE_SALARY);
                Date dateOfBirth = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_BIRTH);
                Date dateOfStart = resultSet.getDate(Attribute.EMPLOYEE_DATE_OF_START);
                String phoneNumber = resultSet.getString(Attribute.EMPLOYEE_PHONE_NUMBER);
                String city = resultSet.getString(Attribute.EMPLOYEE_CITY);
                String street = resultSet.getString(Attribute.EMPLOYEE_STREET);
                String zipCode = resultSet.getString(Attribute.EMPLOYEE_ZIP_CODE);
                employees.add(new Employee(idEmployee, surname, emplName, emplPatronymic, emplRole,
                        salary, dateOfBirth, dateOfStart, phoneNumber, city, street, zipCode));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<EmployeeShortInfo> selectTotalSumOfSalesByCashierToCustomersFromHisCity(){
        List<EmployeeShortInfo> employees = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_SUM_OF_SALES_BY_CASHIER_TO_CUSTOMERS_FROM_HIS_CITY)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String idEmployee = resultSet.getString(Attribute.EMPLOYEE_ID);
                String emplSurname = resultSet.getString(Attribute.EMPLOYEE_SURNAME);
                String emplName = resultSet.getString(Attribute.EMPLOYEE_NAME);
                String city = resultSet.getString(Attribute.EMPLOYEE_CITY);
                double totalSum = resultSet.getDouble(Attribute.SUM_TOTAL);
                employees.add(new EmployeeShortInfo(idEmployee, emplSurname, emplName, city, totalSum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}