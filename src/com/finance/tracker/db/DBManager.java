package com.finance.tracker.db;

import java.sql.*;

public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/finance_tracker";
    private static final String USER = "root";
    private static final String PASSWORD = "Root@07"; // 🔥 change if needed

    public static void createTable() {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS user (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE," +
                "password VARCHAR(256))"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT," +
                "type VARCHAR(10)," +
                "amount DOUBLE," +
                "date DATE," +
                "category VARCHAR(50)," +
                "description VARCHAR(255)," +
                "mode VARCHAR(10)," +
                "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE)"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS budget (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT," +
                "monthly_budget DOUBLE," +
                "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE)"
            );

            System.out.println("Tables ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}