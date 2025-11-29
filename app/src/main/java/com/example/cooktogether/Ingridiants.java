package com.example.cooktogether;

public class Ingridiants {
    private double amount;
    private String typeAmount;
    private String Name;
    public  Ingridiants(double amount, String typeAmount, String name){
        this.amount=amount;
        this.typeAmount=typeAmount;
        this.Name=name;
    }
    public Ingridiants(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getAmount() {
        return amount;
    }

    public String getTypeAmount() {
        return typeAmount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTypeAmount(String typeAmount) {
        this.typeAmount = typeAmount;
    }
}
