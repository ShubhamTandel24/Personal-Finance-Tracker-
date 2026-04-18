
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
//package com.finance.tracker;
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Scanner sc = new Scanner(System.in);
//        AuthManager auth = new AuthManager();
//        FinanceManager fm = new FinanceManager();
//
//        while (true) {  // 🔁 MAIN LOOP (LOGIN AGAIN)
//
//            System.out.println("\n1. Register\n2. Login\n3. Exit");
//            int choice = sc.nextInt();
//            sc.nextLine();
//
//            if (choice == 3) {
//                System.out.println("Exiting App...");
//                break; // close program
//            }
//
//            System.out.print("Username: ");
//            String user = sc.nextLine();
//            System.out.print("Password: ");
//            String pass = sc.nextLine();
//
//            if (choice == 1) {
//                if (auth.register(user, pass)) {
//                    System.out.println("Registered Successfully!");
//                } else {
//                    System.out.println("User already exists!");
//                    continue; // 🔁 back to login menu
//                }
//            }
//
//            if (!auth.login(user, pass)) {
//                System.out.println("Login Failed!");
//                continue; // 🔁 back to login menu
//            }
//
//            System.out.println("Login Successful!");
//
//            // 🔁 DASHBOARD LOOP
//            while (true) {
//                System.out.println("\n1.Add Income\n2.Add Expense\n3.Show Transactions\n4.Set Budget\n5.Check Budget\n6.Logout");
//                int opt = sc.nextInt();
//
//                switch (opt) {
//                    case 1:
//                        System.out.print("Amount: ");
//                        double inc = sc.nextDouble();
//                        sc.nextLine();
//                        System.out.print("Category: ");
//                        String icat = sc.nextLine();
//                        fm.addTransaction("income", inc, icat);
//                        break;
//
//                    case 2:
//                        System.out.print("Amount: ");
//                        double exp = sc.nextDouble();
//                        sc.nextLine();
//                        System.out.print("Category: ");
//                        String ecat = sc.nextLine();
//                        fm.addTransaction("expense", exp, ecat);
//                        break;
//
//                    case 3:
//                        fm.showTransactions();
//                        break;
//
//                    case 4:
//                        System.out.print("Set Budget: ");
//                        double b = sc.nextDouble();
//                        fm.setBudget(b);
//                        break;
//
//                    case 5:
//                        fm.checkBudget();
//                        break;
//
//                    case 6:
//                        System.out.println("Logging out...");
//                        break; // 🔴 exits dashboard loop only
//                }
//
//                if (opt == 6) break; // 🔁 back to login menu
//            }
//        }
//
//        sc.close();
//    }
//}