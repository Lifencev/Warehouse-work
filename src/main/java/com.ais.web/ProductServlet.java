package com.ais.web;

import com.ais.constants.Attribute;
import com.ais.model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

public class ProductServlet extends javax.servlet.http.HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final com.ais.dao.ProductDAO productDAO;

    public ProductServlet(){
        this.productDAO = new com.ais.dao.ProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/newproduct":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insertproduct":
                try {
                    insertProduct(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/deleteproduct":
                try {
                    deleteProduct(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/editproduct":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/updateproduct":
                try {
                    updateProduct(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/cproducts":
                try {
                    clistProducts(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/productswcategory":
                try {
                    listProductsByCategory(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cproductswcategory":
                try {
                    clistProductsByCategory(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cproductswname":
                try {
                    listProductsByName(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/productsforstore":
                try {
                    listProductsStore(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cproductsforstore":
                try {
                    clistProductsStore(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listProducts(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAddProductType.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int categoryNumber = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        String productName = request.getParameter(Attribute.PRODUCT_NAME);
        String characteristics = request.getParameter(Attribute.PRODUCT_CHARACTERISTICS);
        Product newProduct = new Product(categoryNumber, productName, characteristics);
        productDAO.insertProduct(newProduct);
        response.sendRedirect("products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("UTF-8");
        int idProduct = Integer.parseInt(request.getParameter(Attribute.PRODUCT_ID));
        productDAO.deleteProduct(idProduct);
        response.sendRedirect("products");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int idProduct = Integer.parseInt(request.getParameter(Attribute.PRODUCT_ID));
        Product product = productDAO.selectProduct(idProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAddProductType.jsp");
        request.setAttribute("product", product);
        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        int categoryNumber = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        String productName = request.getParameter(Attribute.PRODUCT_NAME);
        String characteristics = request.getParameter(Attribute.PRODUCT_CHARACTERISTICS);
        int idProduct = Integer.parseInt(request.getParameter("id_product"));
        Product product = new Product(idProduct, categoryNumber, productName, characteristics);
        productDAO.updateProduct(product);
        response.sendRedirect("products");
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.selectAllProducts();
        System.out.println(products.toString());
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/storeproducts");
        dispatcher.forward(request, response);
    }

    private void clistProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.selectAllProducts();
        System.out.println(products.toString());
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cstoreproducts");
        dispatcher.forward(request, response);
    }

    private void listProductsByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int categoryNumber = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        System.out.println(categoryNumber);
        List<Product> products = productDAO.getAllProductsByCategory(categoryNumber);
        System.out.println(products.toString());
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/storeproducts");
        dispatcher.forward(request, response);
    }
    private void clistProductsByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int categoryNumber = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        System.out.println(categoryNumber);
        List<Product> products = productDAO.getAllProductsByCategory(categoryNumber);
        System.out.println(products.toString());
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cstoreproducts");
        dispatcher.forward(request, response);
    }

    private void listProductsByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String productName = request.getParameter(Attribute.PRODUCT_NAME);
        System.out.println(productName);
        List<Product> products = productDAO.getByProductName(productName);
        System.out.println(products.toString());
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cstoreproducts");
        dispatcher.forward(request, response);
    }
    private void listProductsStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Product> products = productDAO.selectAllProducts();
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminStoreProducts.jsp");
        dispatcher.forward(request, response);
    }
    private void clistProductsStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Product> products = productDAO.selectAllProducts();
        request.setAttribute("listProducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierStoreProducts.jsp");
        dispatcher.forward(request, response);
    }
}