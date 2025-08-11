package com.example.exammodel3.model;

import com.example.exammodel3.entities.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel extends BaseModel {
    public List<Category> getAllCategories() throws SQLException {
        String sql = "SELECT * FROM categories";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Category> list = new ArrayList<>();
        while (rs.next()) {
            Category cat = new Category();
            cat.setId(rs.getInt("id"));
            cat.setName(rs.getString("name"));
            list.add(cat);
        }
        return list;
    }
}
