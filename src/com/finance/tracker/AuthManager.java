package com.finance.tracker;

import java.sql.*;
import java.security.MessageDigest;
import com.finance.tracker.db.DBManager;

public class AuthManager {

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean register(String username, String password) {
        String query = "INSERT INTO user(username,password) VALUES(?,?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, hashPassword(password));

            ps.executeUpdate();
            System.out.println("✅ Registered");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Username exists");
            return false;
        }
    }

    public int login(String username, String password) {
        String query = "SELECT id FROM user WHERE username=? AND password=?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, hashPassword(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt("id");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
//package com.finance.tracker;
//
//import java.sql.*;
//import com.finance.tracker.db.DBManager;
//
//public class AuthManager {
//
//    // REGISTER
//    public boolean register(String username, String password) {
//        String query = "INSERT INTO users(username, password) VALUES(?, ?)";
//
//        try (Connection con = DBManager.getConnection();
//             PreparedStatement ps = con.prepareStatement(query)) {
//
//            ps.setString(1, username);
//            ps.setString(2, password);
//
//            ps.executeUpdate();
//            System.out.println("✅ Registered Successfully");
//            return true;
//
//        } catch (Exception e) {
//            System.out.println("❌ Username already exists!");
//            return false;
//        }
//    }
//
//    // LOGIN → RETURNS user_id 🔥
//    public int login(String username, String password) {
//        String query = "SELECT id FROM users WHERE username=? AND password=?";
//
//        try (Connection con = DBManager.getConnection();
//             PreparedStatement ps = con.prepareStatement(query)) {
//
//            ps.setString(1, username);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt("id"); // 🔥 important
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return -1;
//    }
//}