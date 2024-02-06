package com.ais.web;


import com.ais.constants.Attribute;
import com.ais.dao.CategoryDAO;
import com.ais.model.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

public class CategoryServlet extends javax.servlet.http.HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final CategoryDAO categoryDAO;

    public CategoryServlet(){
        this.categoryDAO = new CategoryDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/newcategory":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insertcategory":
                try {
                    insertCategory(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/deletecategory":
                try {
                    deleteCategory(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/editcategory":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/updatecategory":
                try {
                    updateCategory(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listCategories(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminChangeCategory.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String categoryName = request.getParameter(Attribute.CATEGORY_NAME);
        Category newCategory = new Category(categoryName);
        categoryDAO.insertCategory(newCategory);
        response.sendRedirect(request.getContextPath()+"/categories");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        categoryDAO.deleteCategory(id);
        response.sendRedirect(request.getContextPath()+"/categories");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        Category category = categoryDAO.selectCategory(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminChangeCategory.jsp");
        request.setAttribute("category", category);
        dispatcher.forward(request, response);
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter(Attribute.CATEGORY_NUMBER));
        String categoryName = request.getParameter(Attribute.CATEGORY_NAME);
        System.out.println(categoryName);
        Category category = new Category(id, categoryName);
        categoryDAO.updateCategory(category);
        response.sendRedirect(request.getContextPath()+"/categories");
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDAO.selectAllCategories();
        request.setAttribute("listCategories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminCategories.jsp");
        dispatcher.forward(request, response);
    }
}