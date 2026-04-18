package com.finance.tracker;

import java.sql.*;
import java.time.LocalDate;
import com.finance.tracker.db.DBManager;

public class FinanceManager {

    public void addIncome(int userId, double amount, String mode) {
        String q = "INSERT INTO transactions(user_id,type,amount,date,mode) VALUES(?,?,?,?,?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {

            ps.setInt(1, userId);
            ps.setString(2, "income");
            ps.setDouble(3, amount);
            ps.setString(4, LocalDate.now().toString());
            ps.setString(5, mode);

            ps.executeUpdate();
            System.out.println(" Income added");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addExpense(int userId, double amount, String cat, String desc, String mode) {
        String q = "INSERT INTO transactions VALUES(NULL,?,?,?,?,?,?,?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {

            ps.setInt(1, userId);
            ps.setString(2, "expense");
            ps.setDouble(3, amount);
            ps.setString(4, LocalDate.now().toString());
            ps.setString(5, cat);
            ps.setString(6, desc);
            ps.setString(7, mode);

            ps.executeUpdate();
            System.out.println(" Expense added");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void setBudget(int userId, double amount) {
        try (Connection con = DBManager.getConnection()) {

            PreparedStatement del = con.prepareStatement("DELETE FROM budget WHERE user_id=?");
            del.setInt(1, userId);
            del.executeUpdate();

            PreparedStatement ins = con.prepareStatement(
                    "INSERT INTO budget(user_id,monthly_budget) VALUES(?,?)");

            ins.setInt(1, userId);
            ins.setDouble(2, amount);
            ins.executeUpdate();

            System.out.println(" Budget set");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void generateReport(int userId) {
        try (Connection con = DBManager.getConnection()) {

            ResultSet r1 = con.prepareStatement(
                    "SELECT SUM(amount) FROM transactions WHERE user_id="+userId+" AND type='income'")
                    .executeQuery();
            r1.next();
            double income = r1.getDouble(1);

            ResultSet r2 = con.prepareStatement(
                    "SELECT SUM(amount) FROM transactions WHERE user_id="+userId+" AND type='expense'")
                    .executeQuery();
            r2.next();
            double expense = r2.getDouble(1);

            double balance = income - expense;

            ResultSet r3 = con.prepareStatement(
                    "SELECT monthly_budget FROM budget WHERE user_id="+userId)
                    .executeQuery();

            double budget = 0;
            if (r3.next()) budget = r3.getDouble(1);

            System.out.println("\n===== REPORT =====");
            System.out.println("Income: " + income);
            System.out.println("Expense: " + expense);
            System.out.println("Balance: " + balance);

            if (budget > 0) {
                System.out.println("Budget: " + budget);

                if (expense > budget)
                    System.out.println(" Budget EXCEEDED!");
                else
                    System.out.println(" Within Budget");
            }

            //  CATEGORY %
            ResultSet rs = con.prepareStatement(
                "SELECT category,SUM(amount) FROM transactions WHERE user_id="+userId+
                " AND type='expense' GROUP BY category").executeQuery();

            System.out.println("\nCategory %:");
            while (rs.next()) {
                String cat = rs.getString(1);
                double amt = rs.getDouble(2);

                double percent = (amt / expense) * 100;
                System.out.println(cat + " : " + String.format("%.2f", percent) + "%");
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
