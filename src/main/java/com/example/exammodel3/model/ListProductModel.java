package com.example.exammodel3.model;

import com.example.exammodel3.entities.Category;
import com.example.exammodel3.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListProductModel extends BaseModel {
    public List<Product> ListProductModel() throws SQLException {
        String sql = "SELECT p.id, p.name, p.price, p.quantity,p.color, p.description, c.name AS category_name\n" +
                "        FROM product p\n" +
                "        JOIN categories c ON p.category_id = c.id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Product> listProduct = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String color = rs.getString("color");
            String description = rs.getString("description");
            String categoryName = rs.getString("category_name");

            Category category = new Category(categoryName);
            Product list = new Product(id, name, price, quantity, color, description, category);
            listProduct.add(list);
        }
        return listProduct;
    }

    public List<Product> searchProducts(String name, Double price, Integer categoryId, String color) throws SQLException {
        String sql = "SELECT p.*, c.name AS category_name " +
                "FROM product p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE ( ? IS NULL OR p.name LIKE CONCAT('%', ?, '%') ) " +
                "AND ( ? IS NULL OR p.price = ? ) " +
                "AND ( ? IS NULL OR p.category_id = ? ) " +
                "AND ( ? IS NULL OR p.color LIKE CONCAT('%', ?, '%') )";

        PreparedStatement ps = conn.prepareStatement(sql);

        // Tham số index từ 1 đến 8
        ps.setString(1, name);
        ps.setString(2, name);

        if (price != null) {
            ps.setDouble(3, price);
            ps.setDouble(4, price);
        } else {
            ps.setNull(3, java.sql.Types.DOUBLE);
            ps.setNull(4, java.sql.Types.DOUBLE);
        }

        if (categoryId != null) {
            ps.setInt(5, categoryId);
            ps.setInt(6, categoryId);
        } else {
            ps.setNull(5, java.sql.Types.INTEGER);
            ps.setNull(6, java.sql.Types.INTEGER);
        }

        ps.setString(7, color);
        ps.setString(8, color);

        ResultSet rs = ps.executeQuery();
        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setColor(rs.getString("color"));
            product.setDescription(rs.getString("description"));

            Category cat = new Category();
            cat.setId(rs.getInt("category_id"));
            cat.setName(rs.getString("category_name"));
            product.setCategory(cat);

            list.add(product);
        }
        return list;
    }

    public void deleteProductById(int id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO product (name, price, quantity, color, description, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.setString(4, product.getColor());
        ps.setString(5, product.getDescription());
        ps.setInt(6, product.getCategory().getId());
        ps.executeUpdate();
    }

    public Product findById(int id) throws SQLException {
        String sql = "SELECT p.*, c.name AS category_name FROM product p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setColor(rs.getString("color"));
            product.setDescription(rs.getString("description"));

            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("category_name"));
            product.setCategory(category);

            return product;
        }
        return null;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE product SET name = ?, price = ?, quantity = ?, color = ?, description = ?, category_id = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.setString(4, product.getColor());
        ps.setString(5, product.getDescription());
        ps.setInt(6, product.getCategory().getId());
        ps.setInt(7, product.getId());
        ps.executeUpdate();
    }
    public List<String> getAllColors() throws SQLException {
        String sql = "SELECT DISTINCT color FROM product WHERE color IS NOT NULL AND color <> ''";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<String> colors = new ArrayList<>();
        while (rs.next()) {
            colors.add(rs.getString("color"));
        }
        return colors;
    }

}
