package com.example.finalapp;

public class Goal {
    public int id;
    public String name, amount, description;

    public Goal(int id, String name, String amount, String description){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
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

    public void getName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void getDescription(String description) {
        this.description = description;
    }

    public Goal(){

    }
}
