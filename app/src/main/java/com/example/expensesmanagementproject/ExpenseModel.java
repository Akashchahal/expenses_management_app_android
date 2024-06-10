package com.example.expensesmanagementproject;


public class ExpenseModel {

    private int id;
    private String name;
    private int amount;
    private String dated;

    public ExpenseModel(int id, String name, int amount, String dated) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.dated = dated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }
}
