package com.example.cooktogether;

public class Ingredient {
    private String amount;
    private String Unit;
    private String Name;
    public Ingredient(String amount, String typeAmount, String name){
        this.amount=amount;
        this.Unit =typeAmount;
        this.Name=name;
    }
    public Ingredient(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return Unit;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.Unit = unit;
    }
}
