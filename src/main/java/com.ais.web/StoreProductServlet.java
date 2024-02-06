package com.ais.web;

import com.ais.constants.Attribute;
import com.ais.dao.StoreProductDAO;
import com.ais.model.StoreProduct;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreProductServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final StoreProductDAO storeProductDAO;

    public StoreProductServlet(){
        this.storeProductDAO = new StoreProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/newstoreproduct":
                try {
                    showNewForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/insertstoreproduct":
                try {
                    insertStoreProduct(request, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/deletestoreproduct":
                try {
                    deleteStoreProduct(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/editstoreproduct":
                try {
                    showEditForm(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/updatestoreproduct":
                try {
                    updateStoreProduct(request, response);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/cstoreproducts":
                try{
                    clistStoreProducts(request, response);
                }catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/storeproductswsalewqty":
                try {
                    //listStoreProductsByQuantity(request, response);
                    handleQtyAndSale(request,response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cstoreproductswsalewqty":
                try {
                    //listStoreProductsByQuantity(request, response);
                    chandleQtyAndSale(request,response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/storeproductswqty":
                try {
                    handleQty(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/storeproductswupc":
                try {
                    listStoreProductsByUPC(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/cstoreproductswupc":
                try {
                    clistStoreProductsByUPC(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listStoreProducts(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void handleQtyAndSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean qty = Boolean.parseBoolean(request.getParameter("bqty"));
        boolean sale = Boolean.parseBoolean(request.getParameter("sale"));
        System.out.println(qty);
        System.out.println(sale);
        if ((qty==true) && (sale==true)){
            listPromotionalByQuantity(request,response);
        }else if((qty==true) && (sale==false)){
            listNonPromotionalByQuantity(request,response);
        }else if((qty==false)&&(sale==true)){
            listPromotionalByProductName(request,response);
        }else{
            listNonPromotionalByProductName(request,response);
        }
    }
    private void chandleQtyAndSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean qty = Boolean.parseBoolean(request.getParameter("bqty"));
        boolean sale = Boolean.parseBoolean(request.getParameter("sale"));
        System.out.println(qty);
        System.out.println(sale);
        if ((qty==true) && (sale==true)){
            clistPromotionalByQuantity(request,response);
        }else if((qty==true) && (sale==false)){
            clistNonPromotionalByQuantity(request,response);
        }else if((qty==false)&&(sale==true)){
            clistPromotionalByProductName(request,response);
        }else{
            clistNonPromotionalByProductName(request,response);
        }
    }
    private void handleQty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean qty = Boolean.parseBoolean(request.getParameter("bqty"));
        if (qty){
            listStoreProductsByQuantity(request, response);
        }else{
            response.sendRedirect("products");
        }
    }
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAddProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void insertStoreProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        String UPCProm = request.getParameter(Attribute.UPC_PROM);
        int idProduct = Integer.parseInt(request.getParameter(Attribute.PRODUCT_ID));
        double sellingPrice = Double.parseDouble(request.getParameter(Attribute.STORE_PRODUCT_SELLING_PRICE));
        int productsNumber = Integer.parseInt(request.getParameter(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER));
        boolean promotionalProduct = Boolean.parseBoolean(request.getParameter(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT));
        StoreProduct newStoreProduct = new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct);
        storeProductDAO.insertStoreProduct(newStoreProduct);
        response.sendRedirect("products");
    }

    private void deleteStoreProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        storeProductDAO.deleteStoreProduct(UPC);
        response.sendRedirect("products");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        StoreProduct storeProduct = storeProductDAO.selectStoreProduct(UPC);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAddProduct.jsp");
        request.setAttribute("store_product", storeProduct);
        dispatcher.forward(request, response);
    }

    private void updateStoreProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        String UPC = request.getParameter(Attribute.UPC);
        String UPCProm = request.getParameter(Attribute.UPC_PROM);
        int idProduct = Integer.parseInt(request.getParameter(Attribute.PRODUCT_ID));
        double sellingPrice = Double.parseDouble(request.getParameter(Attribute.STORE_PRODUCT_SELLING_PRICE));
        int productsNumber = Integer.parseInt(request.getParameter(Attribute.STORE_PRODUCT_PRODUCTS_NUMBER));
        System.out.println(request.getParameter(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT));
        boolean promotionalProduct = Boolean.parseBoolean(request.getParameter(Attribute.STORE_PRODUCT_PROMOTIONAL_PRODUCT));
        StoreProduct storeProduct = new StoreProduct(UPC, UPCProm, idProduct, sellingPrice, productsNumber, promotionalProduct);
        storeProductDAO.updateStoreProduct(storeProduct);
        response.sendRedirect("products");
    }

    private void listStoreProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllStoreProducts();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminAllProducts.jsp");
        dispatcher.forward(request, response);
    }

    private void clistStoreProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllStoreProducts();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cashierAllProducts.jsp");
        dispatcher.forward(request, response);
    }

    private void listStoreProductsByQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllStoreProductsByQuantity();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsforstore");
        dispatcher.forward(request, response);
    }
    private void listStoreProductsByUPC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String UPC = request.getParameter(Attribute.UPC);
        List<StoreProduct> storeProducts = new ArrayList<>();
        storeProducts.add(storeProductDAO.selectStoreProduct(UPC));
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsforstore");
        dispatcher.forward(request, response);
    }
    private void clistStoreProductsByUPC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String UPC = request.getParameter(Attribute.UPC);
        List<StoreProduct> storeProducts = new ArrayList<>();
        storeProducts.add(storeProductDAO.selectStoreProduct(UPC));
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cproductsforstore");
        dispatcher.forward(request, response);
    }
    private void listPromotionalByQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllPromotionalByQuantity();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsforstore");
        dispatcher.forward(request, response);
    }
    private void clistPromotionalByQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllPromotionalByQuantity();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cproductsforstore");
        dispatcher.forward(request, response);
    }
    private void listPromotionalByProductName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllPromotionalByProductName();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsforstore");
        dispatcher.forward(request, response);
    }
    private void clistPromotionalByProductName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllPromotionalByProductName();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cproductsforstore");
        dispatcher.forward(request, response);
    }

    private void listNonPromotionalByQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllNonPromotionalByQuantity();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsforstore");
        dispatcher.forward(request, response);
    }
    private void clistNonPromotionalByQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllNonPromotionalByQuantity();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cproductsforstore");
        dispatcher.forward(request, response);
    }

    private void listNonPromotionalByProductName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllNonPromotionalByProductName();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsforstore");
        dispatcher.forward(request, response);
    }
    private void clistNonPromotionalByProductName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllNonPromotionalByProductName();
        request.setAttribute("listStoreProduct", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cproductsforstore");
        dispatcher.forward(request, response);
    }

    private void listStoreProductsByProductName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreProduct> storeProducts = storeProductDAO.selectAllStoreProductsByProductName();
        request.setAttribute("listStoreProductsByProductName", storeProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("??????");
        dispatcher.forward(request, response);
    }
}