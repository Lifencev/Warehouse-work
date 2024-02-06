package com.ais.dao;

import java.sql.Connection;

public interface IDAO {

    String JDBC_URL = "jdbc:mysql://localhost:3306/ais?useSSL=false&serverTimezone=UTC";
    String JDBC_USERNAME = "root";
    String JDBC_PASSWORD = "12345678";
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    Connection getConnection();
}
