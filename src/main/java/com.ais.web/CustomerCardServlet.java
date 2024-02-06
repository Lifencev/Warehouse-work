package com.ais.web;

import com.ais.auxiliary.CustomerShortInfo;
import com.ais.constants.Attribute;
import com.ais.dao.CustomerCardDAO;
import com.ais.model.CustomerCard;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

public class CustomerCardServlet extends javax.servlet.http.HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final CustomerCardDAO customerCardDAO;

    public CustomerCardServlet(){
        this.customerCardDAO = new CustomerCardDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        System.out.println("action");
        switch (action) {
            case "/newcustomercard":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insertcustomercard":
                try {
                    insertCustomerCard(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/deletecustomercard":
                try {
                    deleteCustomerCard(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/editcustomercard":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/updatecustomercard":
                try {
                    updateCustomerCard(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "/cnewcustomercard":
                try {
                    cshowNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cinsertcustomercard":
                try {
                    cinsertCustomerCard(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cdeletecustomercard":
                try {
                    cdeleteCustomerCard(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/ceditcustomercard":
                try {
                    cshowEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cupdatecustomercard":
                try {
                    cupdateCustomerCard(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/ccustomercards":
                try {
                    System.out.println("fhwefdoijse");
                    clistCustomerCards(request, response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }
                break;
            case "/customercardspercent":
                try {
                    listCustomerCardsByPercent(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/ccustomercardssurname":
                try {
                    listCustomerCardsBySurname(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/customersboughtfromonecategory":
                try {
                    listCustomersWhoOnceBoughtOnlyFromOneCategory(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listCustomerCards(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminChangeClientsList.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCustomerCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        String custSurname = request.getParameter(Attribute.CUSTOMER_SURNAME);
        String custName = request.getParameter(Attribute.CUSTOMER_NAME);
        String custPatronymic = request.getParameter(Attribute.CUSTOMER_PATRONYMIC);
        String phoneNumber = request.getParameter(Attribute.CUSTOMER_PHONE_NUMBER);
        String city = request.getParameter(Attribute.CUSTOMER_CITY);
        String street = request.getParameter(Attribute.CUSTOMER_STREET);
        String zipCode = request.getParameter(Attribute.CUSTOMER_ZIP_CODE);
        int percent = Integer.parseInt(request.getParameter(Attribute.CUSTOMER_PERCENT));
        CustomerCard newCustomerCard = new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent);
        customerCardDAO.insertCustomerCard(newCustomerCard);
        response.sendRedirect(request.getContextPath()+"/customercards");
    }

    private void deleteCustomerCard(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        customerCardDAO.deleteCustomerCard(cardNumber);
        response.sendRedirect(request.getContextPath()+"/customercards");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        CustomerCard customerCard = customerCardDAO.selectCustomerCard(cardNumber);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminChangeClientsList.jsp");
        request.setAttribute("customerCard", customerCard);
        dispatcher.forward(request, response);
    }

    private void updateCustomerCard(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        String custSurname = request.getParameter(Attribute.CUSTOMER_SURNAME);
        String custName = request.getParameter(Attribute.CUSTOMER_NAME);
        String custPatronymic = request.getParameter(Attribute.CUSTOMER_PATRONYMIC);
        String phoneNumber = request.getParameter(Attribute.CUSTOMER_PHONE_NUMBER);
        String city = request.getParameter(Attribute.CUSTOMER_CITY);
        String street = request.getParameter(Attribute.CUSTOMER_STREET);
        String zipCode = request.getParameter(Attribute.CUSTOMER_ZIP_CODE);
        int percent = Integer.parseInt(request.getParameter(Attribute.CUSTOMER_PERCENT));
        CustomerCard customerCard = new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent);
        customerCardDAO.updateCustomerCard(customerCard);
        response.sendRedirect(request.getContextPath()+"/customercards");
    }

    private void listCustomerCards(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CustomerCard> customerCards = customerCardDAO.selectAllCustomerCards();
        request.setAttribute("listCustomerCards", customerCards);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminClients.jsp");
        dispatcher.forward(request, response);
    }

    private void cshowNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierChangeClientsList.jsp");
        dispatcher.forward(request, response);
    }

    private void cinsertCustomerCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        String custSurname = request.getParameter(Attribute.CUSTOMER_SURNAME);
        String custName = request.getParameter(Attribute.CUSTOMER_NAME);
        String custPatronymic = request.getParameter(Attribute.CUSTOMER_PATRONYMIC);
        String phoneNumber = request.getParameter(Attribute.CUSTOMER_PHONE_NUMBER);
        String city = request.getParameter(Attribute.CUSTOMER_CITY);
        String street = request.getParameter(Attribute.CUSTOMER_STREET);
        String zipCode = request.getParameter(Attribute.CUSTOMER_ZIP_CODE);
        int percent = Integer.parseInt(request.getParameter(Attribute.CUSTOMER_PERCENT));
        CustomerCard newCustomerCard = new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent);
        customerCardDAO.insertCustomerCard(newCustomerCard);
        response.sendRedirect(request.getContextPath()+"/ccustomercards");
    }

    private void cdeleteCustomerCard(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        customerCardDAO.deleteCustomerCard(cardNumber);
        response.sendRedirect(request.getContextPath()+"/ccustomercards");
    }

    private void cshowEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        CustomerCard customerCard = customerCardDAO.selectCustomerCard(cardNumber);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierChangeClientsList.jsp");
        request.setAttribute("customerCard", customerCard);
        dispatcher.forward(request, response);
    }

    private void cupdateCustomerCard(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        String cardNumber = request.getParameter(Attribute.CARD_NUMBER);
        String custSurname = request.getParameter(Attribute.CUSTOMER_SURNAME);
        String custName = request.getParameter(Attribute.CUSTOMER_NAME);
        String custPatronymic = request.getParameter(Attribute.CUSTOMER_PATRONYMIC);
        String phoneNumber = request.getParameter(Attribute.CUSTOMER_PHONE_NUMBER);
        String city = request.getParameter(Attribute.CUSTOMER_CITY);
        String street = request.getParameter(Attribute.CUSTOMER_STREET);
        String zipCode = request.getParameter(Attribute.CUSTOMER_ZIP_CODE);
        int percent = Integer.parseInt(request.getParameter(Attribute.CUSTOMER_PERCENT));
        CustomerCard customerCard = new CustomerCard(cardNumber, custSurname, custName, custPatronymic, phoneNumber, city, street, zipCode, percent);
        customerCardDAO.updateCustomerCard(customerCard);
        response.sendRedirect(request.getContextPath()+"/ccustomercards");
    }

    private void clistCustomerCards(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CustomerCard> customerCards = customerCardDAO.selectAllCustomerCards();
        System.out.println("why tf are you here now?");
        request.setAttribute("listCustomerCards", customerCards);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierClients.jsp");
        dispatcher.forward(request, response);
    }

    private void listCustomerCardsByPercent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int percent = Integer.parseInt(request.getParameter(Attribute.CUSTOMER_PERCENT));
        List<CustomerCard> customerCards = customerCardDAO.selectAllCustomerCardsByPercent(percent);
        request.setAttribute("listCustomerCards", customerCards);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminClients.jsp");
        dispatcher.forward(request, response);
    }

    private void listCustomerCardsBySurname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String surname = request.getParameter(Attribute.CUSTOMER_SURNAME);
        List<CustomerCard> customerCards = customerCardDAO.selectBySurname(surname);
        request.setAttribute("listCustomerCards", customerCards);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierClients.jsp");
        dispatcher.forward(request, response);
    }
    private void listCustomersWhoOnceBoughtOnlyFromOneCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String categoryName = request.getParameter(Attribute.CATEGORY_NAME);
        List<CustomerShortInfo> customerCards = customerCardDAO.selectAllWhoOnceBoughtOnlyFromOneCategory(categoryName);
        request.setAttribute("result", customerCards);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customQuery1.jsp");
        dispatcher.forward(request, response);
    }
}