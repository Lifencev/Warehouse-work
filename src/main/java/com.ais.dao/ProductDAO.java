package com.ais.dao;

import com.ais.constants.Attribute;
import com.ais.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IDAO {

    private static final String CREATE = "INSERT INTO product (category_number, product_name, characteristics) VALUES (?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM product WHERE id_product=?";
    private static final String GET_ALL = "SELECT * FROM product ORDER BY product_name";
    private static final String UPDATE = "UPDATE product SET category_number=?, product_name=?, characteristics=? WHERE id_product=?";
    private static final String DELETE = "DELETE FROM product WHERE id_product=?";
    private static final String GET_ALL_BY_CATEGORY = "SELECT * FROM product WHERE category_number =? ORDER BY product_name";
    //private static final String GET_BY_PRODUCT_NAME = "SELECT * FROM product WHERE product_name LIKE '%' || ? || '%'";
    private static final String GET_BY_PRODUCT_NAME = "SELECT * FROM product WHERE product_name LIKE CONCAT('%', ?, '%')";

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

    public void insertProduct(Product product) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setInt(1, product.getCategoryNumber());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getCharacteristics());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setInt(1, product.getCategoryNumber());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getCharacteristics());
            statement.setInt(4, product.getIdProduct());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public Product selectProduct(int idProduct){
        Product product = null;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, idProduct);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int categoryNumber = Integer.parseInt(resultSet.getString(Attribute.CATEGORY_NUMBER));
                String productName = resultSet.getString(Attribute.PRODUCT_NAME);
                String characteristics = resultSet.getString(Attribute.PRODUCT_CHARACTERISTICS);
                product = new Product(idProduct, categoryNumber, productName, characteristics);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> selectAllProducts(){
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
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

    public boolean deleteProduct(int idProduct) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, idProduct);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<Product> getAllProductsByCategory(int category) {
		List<Product> products = new ArrayList<>();
		try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_CATEGORY)) {
			preparedStatement.setInt(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
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

    public List<Product> getByProductName(String productName) {
		List<Product> products = new ArrayList<>();
		try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_PRODUCT_NAME)) {
			preparedStatement.setString(1, productName);
            ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
                String fullProductName = resultSet.getString(Attribute.PRODUCT_NAME);
				int idProduct = resultSet.getInt(Attribute.PRODUCT_ID);
                int categoryNumber = resultSet.getInt(Attribute.CATEGORY_NUMBER);
                String characteristics = resultSet.getString(Attribute.PRODUCT_CHARACTERISTICS);
                products.add(new Product(idProduct, categoryNumber, fullProductName, characteristics));
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return products;
	}
}