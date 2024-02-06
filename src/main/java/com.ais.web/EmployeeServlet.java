package com.ais.web;

import com.ais.auxiliary.EmployeeShortInfo;
import com.ais.constants.Attribute;
import com.ais.dao.EmployeeDAO;
import com.ais.model.Employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class EmployeeServlet extends javax.servlet.http.HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final EmployeeDAO employeeDAO;

    public EmployeeServlet(){
        this.employeeDAO = new EmployeeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/newemployee":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insertemployee":
                try {
                    insertEmployee(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/deleteemployee":
                try {
                    deleteEmployee(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/editemployee":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/updateemployee":
                try {
                    updateEmployee(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/cashiers":
                try {
                    listCashiers(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/employeesbysurname":
                try {
                    listPhoneNumberAndAddressBySurname(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/totalsumbycashierfromhiscity":
                try {
                    listTotalSumOfSalesByCashierToCustomersFromHisCity(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listEmployees(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminChangeWorkersList.jsp");
        dispatcher.forward(request, response);
    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        String emplSurname = request.getParameter(Attribute.EMPLOYEE_SURNAME);
        String emplName = request.getParameter(Attribute.EMPLOYEE_NAME);
        String emplPatronymic = request.getParameter(Attribute.EMPLOYEE_PATRONYMIC);
        String emplRole = request.getParameter(Attribute.EMPLOYEE_ROLE);
        double salary = Double.parseDouble(request.getParameter(Attribute.EMPLOYEE_SALARY));
        Date dateOfBirth = Date.valueOf(request.getParameter(Attribute.EMPLOYEE_DATE_OF_BIRTH));
        Date dateOfStart = Date.valueOf(request.getParameter(Attribute.EMPLOYEE_DATE_OF_START));
        String phoneNumber = request.getParameter(Attribute.EMPLOYEE_PHONE_NUMBER);
        String city = request.getParameter(Attribute.EMPLOYEE_CITY);
        String street = request.getParameter(Attribute.EMPLOYEE_STREET);
        String zipCode = request.getParameter(Attribute.EMPLOYEE_ZIP_CODE);
        Employee newEmployee = new Employee(idEmployee, emplSurname, emplName, emplPatronymic, emplRole, salary,
                dateOfBirth, dateOfStart, phoneNumber, city, street, zipCode);
        employeeDAO.insertEmployee(newEmployee);
        response.sendRedirect(request.getContextPath()+"/employees");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        employeeDAO.deleteEmployee(idEmployee);
        response.sendRedirect(request.getContextPath()+"/employees");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        Employee employee = employeeDAO.selectEmployee(idEmployee);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminChangeWorkersList.jsp");
        request.setAttribute("employee", employee);
        dispatcher.forward(request, response);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        String idEmployee = request.getParameter(Attribute.EMPLOYEE_ID);
        String emplSurname = request.getParameter(Attribute.EMPLOYEE_SURNAME);
        String emplName = request.getParameter(Attribute.EMPLOYEE_NAME);
        String emplPatronymic = request.getParameter(Attribute.EMPLOYEE_PATRONYMIC);
        String emplRole = request.getParameter(Attribute.EMPLOYEE_ROLE);
        double salary = Double.parseDouble(request.getParameter(Attribute.EMPLOYEE_SALARY));
        Date dateOfBirth = Date.valueOf(request.getParameter(Attribute.EMPLOYEE_DATE_OF_BIRTH));
        Date dateOfStart = Date.valueOf(request.getParameter(Attribute.EMPLOYEE_DATE_OF_START));
        String phoneNumber = request.getParameter(Attribute.EMPLOYEE_PHONE_NUMBER);
        String city = request.getParameter(Attribute.EMPLOYEE_CITY);
        String street = request.getParameter(Attribute.EMPLOYEE_STREET);
        String zipCode = request.getParameter(Attribute.EMPLOYEE_ZIP_CODE);
        Employee employee = new Employee(idEmployee, emplSurname, emplName, emplPatronymic, emplRole,
                salary, dateOfBirth, dateOfStart, phoneNumber, city, street, zipCode);
        employeeDAO.updateEmployee(employee);
        response.sendRedirect(request.getContextPath()+"/employees");
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Employee> employees = employeeDAO.selectAllEmployees();
        request.setAttribute("listEmployees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminWorkers.jsp");
        dispatcher.forward(request, response);
    }

    private void listCashiers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Employee> employees = employeeDAO.selectAllCashiers();
        request.setAttribute("listEmployees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminWorkers.jsp");
        dispatcher.forward(request, response);
    }

    private void listPhoneNumberAndAddressBySurname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String employeeSurname = request.getParameter(Attribute.EMPLOYEE_SURNAME);
        List<Employee> employees = employeeDAO.selectPhoneNumberAndAddressBySurname(employeeSurname);
        request.setAttribute("listEmployees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminWorkers.jsp");
        dispatcher.forward(request, response);
    }
    private void listTotalSumOfSalesByCashierToCustomersFromHisCity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<EmployeeShortInfo> employees = employeeDAO.selectTotalSumOfSalesByCashierToCustomersFromHisCity();
        request.setAttribute("result", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customQuery2.jsp");
        dispatcher.forward(request, response);
    }
}