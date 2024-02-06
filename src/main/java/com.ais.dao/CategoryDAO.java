package com.ais.dao;

import com.ais.constants.Attribute;
import com.ais.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements IDAO {

    private static final String CREATE = "INSERT INTO category (category_name) VALUES (?)";
    private static final String GET_BY_ID = "SELECT * FROM category WHERE category_number=?";
    private static final String GET_ALL = "SELECT * FROM category ORDER BY category_name";
    private static final String UPDATE = "UPDATE category SET category_name=? WHERE category_number=?";
    private static final String DELETE = "DELETE FROM category WHERE category_number=?";

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

    public void insertCategory(Category category) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateCategory(Category category) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, category.getCategoryName());
            System.out.println(category.getCategoryName());
            statement.setInt(2, category.getCategoryNumber());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public Category selectCategory(int id){
        Category category = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String categoryName = resultSet.getString(Attribute.CATEGORY_NAME);
                category = new Category(id, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public List<Category> selectAllCategories(){
        List<Category> categories = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(Attribute.CATEGORY_NUMBER);
                String categoryName = resultSet.getString(Attribute.CATEGORY_NAME);
                categories.add(new Category(id, categoryName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public boolean deleteCategory(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}