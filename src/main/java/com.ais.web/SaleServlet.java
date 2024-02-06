package com.ais.web;

import com.ais.constants.Attribute;
import com.ais.dao.SaleDAO;
import com.ais.model.Sale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

public class SaleServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final SaleDAO saleDAO;

    public SaleServlet(){
        this.saleDAO = new SaleDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/new":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insert":
                try {
                    insertSale(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/delete":
                try {
                    deleteSale(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/edit":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/update":
                try {
                    updateSale(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listSales(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierAddCheck.jsp");
        dispatcher.forward(request, response);
    }

    private void insertSale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        String checkNumber = request.getParameter(Attribute.CHECK_NUMBER);
        int productsNumber = Integer.parseInt(request.getParameter(Attribute.SALE_PRODUCTS_NUMBER));
        double sellingPrice = Double.parseDouble(request.getParameter(Attribute.SALE_SELLING_PRICE));
        Sale newSale = new Sale(UPC, checkNumber, productsNumber, sellingPrice);
        saleDAO.insertSale(newSale);
        response.sendRedirect("list");
    }

    private void deleteSale(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        String checkNumber = request.getParameter(Attribute.CHECK_NUMBER);
        saleDAO.deleteSale(UPC, checkNumber);
        response.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        String checkNumber = request.getParameter(Attribute.CHECK_NUMBER);
        Sale sale = saleDAO.selectSale(UPC, checkNumber);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierAddCheck.jsp");
        request.setAttribute("sale", sale);
        dispatcher.forward(request, response);
    }

    private void updateSale(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        int productsNumber = Integer.parseInt(request.getParameter(Attribute.SALE_PRODUCTS_NUMBER));
        double sellingPrice = Double.parseDouble(request.getParameter(Attribute.SALE_SELLING_PRICE));
        Sale sale = new Sale(productsNumber, sellingPrice);
        saleDAO.updateSale(sale);
        response.sendRedirect("list");
    }

    private void listSales(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Sale> sales = saleDAO.selectAllSales();
        request.setAttribute("listSales", sales);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../adminAllCheck.jsp");
        dispatcher.forward(request, response);
    }
}