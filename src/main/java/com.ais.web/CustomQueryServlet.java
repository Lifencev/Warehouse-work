package com.ais.web;

import com.ais.constants.Attribute;
import com.ais.dao.CustomDAO;
import com.ais.model.Product;
import com.ais.model.SaleCounter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

public class CustomQueryServlet extends javax.servlet.http.HttpServlet{
    @Serial
    private static final long serialVersionUID = 1L;
    CustomDAO customDAO;
    public CustomQueryServlet(){
        this.customDAO = new com.ais.dao.CustomDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/queryprep":
                try {
                    queryPrep(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/query1":
                try {

                    countProducts(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/query2":
                try {
                    getProductsWithX(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    void countProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emplSurname = request.getParameter(Attribute.EMPLOYEE_SURNAME);
        List<SaleCounter> result = customDAO.countTimesEmplSoldX(emplSurname);
        request.setAttribute("result", result);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customQuery1.jsp");
        dispatcher.forward(request,response);
    }
    void getProductsWithX(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(Attribute.PRODUCT_ID));
        List<Product> result = customDAO.selectProductsWithX(id);
        request.setAttribute("result", result);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customQuery2.jsp");
        dispatcher.forward(request,response);
    }
    void queryPrep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customQueryPrep.jsp");
        dispatcher.forward(request, response);
    }
}
