
package com.finance.tracker;

import java.util.Scanner;
import com.finance.tracker.db.DBManager;

public class Main {

    public static void main(String[] args) {

        DBManager.createTable();

        Scanner sc = new Scanner(System.in);
        AuthManager auth = new AuthManager();

        while (true) {

            System.out.println("\n1.Register 2.Login 3.Exit");
            int ch = sc.nextInt(); sc.nextLine();

            if (ch == 1) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                auth.register(u, p);
            }

            else if (ch == 2) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();

                int userId = auth.login(u, p);

                if (userId == -1) {
                    System.out.println("❌ Login failed");
                    continue;
                }

                FinanceManager fm = new FinanceManager();

                while (true) {
                    System.out.println("\n1.Income 2.Expense 3.Budget 4.Report 5.Logout");
                    int op = sc.nextInt(); sc.nextLine();

                    if (op == 1) {
                        System.out.print("Amount: ");
                        double a = sc.nextDouble(); sc.nextLine();
                        System.out.print("Mode: ");
                        String m = sc.nextLine();
                        fm.addIncome(userId, a, m);
                    }

                    else if (op == 2) {
                        System.out.print("Amount: ");
                        double a = sc.nextDouble(); sc.nextLine();
                        System.out.print("Category: ");
                        String c = sc.nextLine();
                        System.out.print("Desc: ");
                        String d = sc.nextLine();
                        System.out.print("Mode: ");
                        String m = sc.nextLine();
                        fm.addExpense(userId, a, c, d, m);
                    }

                    else if (op == 3) {
                        System.out.print("Budget: ");
                        double b = sc.nextDouble(); sc.nextLine();
                        fm.setBudget(userId, b);
                    }

                    else if (op == 4) {
                        fm.generateReport(userId);
                    }

                    else break;
                }
            }

            else break;
        }

        sc.close();
    }
}
