package com.ais.dao;

import com.ais.model.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO implements IDAO {

    public boolean validate(Login login) throws ClassNotFoundException {
        boolean status = false;
        Class.forName(JDBC_DRIVER);
        try (Connection connection = DriverManager
            .getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            PreparedStatement preparedStatement = connection
            .prepareStatement("select * from login where username = ? and password = ? ")) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPassword());
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public Connection getConnection() {
        return null;
    }
}