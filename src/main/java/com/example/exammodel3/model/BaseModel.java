package com.example.exammodel3.model;

import com.example.exammodel3.database.DBConnect;

import java.sql.Connection;

public class BaseModel {
    protected Connection conn;

    public BaseModel() {
        DBConnect dbConnect = new DBConnect();
        conn = dbConnect.getConnect();
    }
}
