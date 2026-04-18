package com.finance.tracker;

public class Transaction {

    private int id;
    private String type;      
    private double amount;
    private String date;
    private String category;
    private String description;
    private String mode;     

    public Transaction(String type, double amount, String date,
                       String category, String description, String mode) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
        this.mode = mode;
    }

    // getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getMode() { return mode; }
}
//package com.finance.tracker;
//
//public class Transaction {
//    private String type; // income or expense
//    private double amount;
//    private String category;
//
//    public Transaction(String type, double amount, String category) {
//        this.type = type;
//        this.amount = amount;
//        this.category = category;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    @Override
//    public String toString() {
//        return type + " | " + amount + " | " + category;
//    }
//}