package com.example.exammodel3.controller;

import com.example.exammodel3.entities.Category;
import com.example.exammodel3.entities.Product;
import com.example.exammodel3.model.CategoryModel;
import com.example.exammodel3.model.ListProductModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = {"/product/*"})
public class ProductServlet extends HttpServlet {

    private ListProductModel listProductModel;
    private CategoryModel categoryModel;

    @Override
    public void init() throws ServletException {
        listProductModel = new ListProductModel();
        categoryModel = new CategoryModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getPathInfo();
        if (uri == null) {
            uri = "";
        }
        switch (uri) {
            case "/list":
                showListProduct(req, resp);
                break;
            case "/search":
                searchProducts(req, resp);
                break;
            case "/delete":
                deleteProduct(req, resp);
                break;
            case "/add":
                showAddForm(req, resp);
                break;
            case "/edit":
                showEditForm(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getPathInfo();
        if (uri == null) {
            uri = "";
        }
        switch (uri) {
            case "/add":
                addProduct(req, resp);
                break;
            case "/edit":
                updateProduct(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showListProduct(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Product> listProduct = listProductModel.ListProductModel();
            List<Category> listCategory = categoryModel.getAllCategories();
            req.setAttribute("listProduct", listProduct);
            req.setAttribute("listCategory", listCategory);
            req.getRequestDispatcher("/views/Product/list.jsp").forward(req, resp);

        } catch (SQLException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchProducts(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("category");
            String color = request.getParameter("color");

            Double price = null;
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                try {
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException ignored) {
                }
            }

            Integer categoryId = null;
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                try {
                    categoryId = Integer.parseInt(categoryIdStr);
                } catch (NumberFormatException ignored) {
                }
            }

            List<Product> products = listProductModel.searchProducts(name, price, categoryId, color);
            List<Category> listCategory = categoryModel.getAllCategories();

            request.setAttribute("listProduct", products);
            request.setAttribute("listCategory", listCategory);

            request.getRequestDispatcher("/views/Product/list.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                listProductModel.deleteProductById(id);
                resp.sendRedirect(req.getContextPath() + "/product/list");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID missing");
        }
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Category> categories = categoryModel.getAllCategories();
            List<String> colors = listProductModel.getAllColors();
            String message = req.getParameter("message");
            if (message != null && !message.trim().isEmpty()) {
                req.setAttribute("message", message);
            }
            req.setAttribute("listColor", colors);
            req.setAttribute("listCategory", categories);
            req.getRequestDispatcher("/views/Product/add.jsp").forward(req, resp);
        } catch (ServletException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantity");
        String color = req.getParameter("color");
        String description = req.getParameter("description");
        String categoryIdStr = req.getParameter("category");

        double price = 0;
        int quantity = 0;
        int categoryId = 0;

        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
            return;
        }

        Category category = new Category();
        category.setId(categoryId);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setColor(color);
        product.setDescription(description);
        product.setCategory(category);

        try {
            listProductModel.addProduct(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/product/add?message=add_success");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/product/list");
                return;
            }
            int id = Integer.parseInt(idStr);
            Product product = listProductModel.findById(id);
            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/product/list");
                return;
            }
            List<Category> categories = categoryModel.getAllCategories();

            req.setAttribute("product", product);
            req.setAttribute("listCategory", categories);
            req.getRequestDispatcher("/views/Product/edit.jsp").forward(req, resp);
        } catch (ServletException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String priceStr = req.getParameter("price");
            String quantityStr = req.getParameter("quantity");
            String color = req.getParameter("color");
            String description = req.getParameter("description");
            String categoryIdStr = req.getParameter("category");

            int id;
            double price;
            int quantity;
            int categoryId;

            try {
                id = Integer.parseInt(idStr);
                price = Double.parseDouble(priceStr);
                quantity = Integer.parseInt(quantityStr);
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Dữ liệu không hợp lệ");
                showEditForm(req, resp);
                return;
            }

            Category category = new Category();
            category.setId(categoryId);

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setColor(color);
            product.setDescription(description);
            product.setCategory(category);

            listProductModel.updateProduct(product);

            resp.sendRedirect(req.getContextPath() + "/product/list");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
