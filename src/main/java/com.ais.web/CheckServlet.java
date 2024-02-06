package com.ais.web;

import com.ais.constants.Attribute;
import com.ais.dao.CheckDAO;
import com.ais.dao.SaleDAO;
import com.ais.dao.StoreProductDAO;
import com.ais.model.Check;
import com.ais.model.Sale;
import com.ais.model.StoreProduct;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class CheckServlet extends javax.servlet.http.HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final CheckDAO checkDAO;
    private final StoreProductDAO storeProductDAO;
    private final SaleDAO saleDAO;

    public CheckServlet(){
        this.checkDAO = new CheckDAO();
        this.storeProductDAO = new StoreProductDAO();
        this.saleDAO = new SaleDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/newcheck":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insertcheck":
                try {
                    insertCheck(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/deletecheck":
                try {
                    deleteCheck(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/editcheck":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/updatecheck":
                try {
                    updateCheck(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/cchecks":
                try {
                    clistChecks(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/checkinfo":
                try {
                    containsCheck(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/ccheckinfo":
                try {
                    cContainsCheck(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/checksbydatebycashier":
                try {
                    listChecksByCashierAndPeriod(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
            case "/checksbydate":
                try {
                    System.out.println("here");
                    listChecksByPeriod(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
            case "?":
                try {
                    listChecksByCashierForTheDay(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listChecks(request, response);
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

    private void insertCheck(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        String checkNumber = request.getParameter(Attribute.CHECK_NUMBER);
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        Timestamp print_date = new Timestamp(System.currentTimeMillis());
        String [] upcs= request.getParameterValues(Attribute.UPC);
        String [] pns = request.getParameterValues(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER);
        int [] products_numbers = new int[pns.length];
        for (int i =0; i < pns.length; i++) {
            products_numbers[i] = Integer.parseInt(pns[i]);
        }
        double sumTotal =0;
        boolean isOkayToAdd = true;
        for (int i =0; i < upcs.length; i++){
            StoreProduct product = storeProductDAO.selectStoreProduct(upcs[i]);
            sumTotal+=product.getSellingPrice()*products_numbers[i];
            if (product.getProductsNumber()<products_numbers[i]) {
                isOkayToAdd=false;
                break;
            }
            StoreProduct newProduct = new StoreProduct(product.getUPC(), product.getUPCProm(),
                    product.getIdProduct(), product.getSellingPrice(), product.getProductsNumber()-products_numbers[i], product.isPromotionalProduct());
            storeProductDAO.updateStoreProduct(newProduct);
        }
        if(isOkayToAdd) {
            double vat = 0.2 * sumTotal;
            Check newCheck = new Check(checkNumber, idEmployee, cardNumber, print_date, sumTotal, vat);
            checkDAO.insertCheck(newCheck);
            for (int i = 0; i < upcs.length; i++) {
                Sale sale = new Sale(upcs[i], checkNumber, products_numbers[i], storeProductDAO.selectStoreProduct(upcs[i]).getSellingPrice());
                saleDAO.insertSale(sale);
            }
        }
        response.sendRedirect(request.getContextPath()+"/cchecks");
    }

    private void deleteCheck(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("UTF-8");
        String checkNumber = request.getParameter(Attribute.CHECK_NUMBER);
        List<Sale> sales= saleDAO.selectByCheckNumber(checkNumber);
        for (Sale sale:sales ){
            StoreProduct product = storeProductDAO.selectStoreProduct(sale.getUPC());
            StoreProduct newProduct = new StoreProduct(product.getUPC(), product.getUPCProm(), product.getIdProduct(),
                    product.getSellingPrice(), product.getProductsNumber()+sale.getProductNumber(), product.isPromotionalProduct());
            storeProductDAO.updateStoreProduct(newProduct);
        }
        checkDAO.deleteCheck(checkNumber);
        response.sendRedirect(request.getContextPath()+"/checks");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String checkNumber = request.getParameter(Attribute.CHECK_NUMBER);
        Check check = checkDAO.selectCheck(checkNumber);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierAddCheck.jsp");
        request.setAttribute("check", check);
        dispatcher.forward(request, response);
    }

    private void updateCheck(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        Timestamp printDate = Timestamp.valueOf(request.getParameter(Attribute.CHECK_PRINT_DATE));
        double sumTotal = Double.parseDouble(request.getParameter(Attribute.CHECK_SUM_TOTAL));
        double vat = Double.parseDouble(request.getParameter(Attribute.CHECK_VAT));
        Check check = new Check(idEmployee, cardNumber, printDate, sumTotal, vat);
        checkDAO.updateCheck(check);
        response.sendRedirect(request.getContextPath()+"/checks");
    }

    private void listChecks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Check> checks = checkDAO.selectAllChecks();
        request.setAttribute("listChecks", checks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAllChecks.jsp");
        dispatcher.forward(request, response);
    }
    private void clistChecks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Check> checks = checkDAO.selectAllChecks();
        request.setAttribute("listChecks", checks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierChecks.jsp");
        dispatcher.forward(request, response);
    }
    private void cContainsCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkNumber=request.getParameter(Attribute.CHECK_NUMBER);
        List<Sale> sales = saleDAO.selectByCheckNumber(checkNumber);
        request.setAttribute("listSales", sales);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierCheckContains.jsp");
        dispatcher.forward(request, response);
    }
    private void containsCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkNumber=request.getParameter(Attribute.CHECK_NUMBER);
        List<Sale> sales = saleDAO.selectByCheckNumber(checkNumber);
        request.setAttribute("listSales", sales);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminCheckContains.jsp");
        dispatcher.forward(request, response);
    }

    private void listChecksByCashierAndPeriod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        LocalDate fromDate = LocalDate.parse(request.getParameter(Attribute.FROM_DATE));
        LocalDate toDate = LocalDate.parse(request.getParameter(Attribute.TO_DATE));
        List<Check> checks = checkDAO.selectAllChecksByCashierAndPeriod(idEmployee, fromDate, toDate);
        request.setAttribute("listChecks", checks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAllChecks.jsp");
        dispatcher.forward(request, response);
    }

    private void listChecksByPeriod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDate fromDate = LocalDate.parse(request.getParameter(Attribute.FROM_DATE));
        LocalDate toDate = LocalDate.parse(request.getParameter(Attribute.TO_DATE));
        System.out.println(request.getParameter(Attribute.FROM_DATE));
        List<Check> checks = checkDAO.selectAllChecksByPeriod(fromDate, toDate);
        request.setAttribute("listChecks", checks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAllChecks.jsp");
        dispatcher.forward(request, response);
    }

    private void listChecksByCashierForTheDay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        LocalDate date = LocalDate.parse(request.getParameter(Attribute.CHECK_PRINT_DATE));
        List<Check> checks = checkDAO.selectChecksByCashierForTheDay(idEmployee, date);
        request.setAttribute("listChecksByCashierForTheDay", checks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("?");
        dispatcher.forward(request, response);
    }
}