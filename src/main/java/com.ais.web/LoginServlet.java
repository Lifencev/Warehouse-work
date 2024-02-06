package com.ais.web;

import com.ais.constants.Attribute;
import com.ais.dao.LoginDAO;
import com.ais.model.Login;

import java.io.IOException;
import java.io.Serial;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private LoginDAO loginDao;

    public void init() {
        loginDao = new LoginDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter(Attribute.USERNAME);
        String password = request.getParameter(Attribute.PASSWORD);
        Login loginBean = new Login();
        loginBean.setUsername(username);
        loginBean.setPassword(password);
        try {
            if (loginDao.validate(loginBean)) {
                HttpSession session = request.getSession();
                session.setAttribute(Attribute.USERNAME, username);
                response.sendRedirect("adminAllProducts.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(Attribute.USERNAME, username);
                response.sendRedirect("login.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}