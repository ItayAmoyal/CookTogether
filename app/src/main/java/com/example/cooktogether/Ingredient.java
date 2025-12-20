package com.example.cooktogether;

public class Ingredient {
    private String amount;
    private String unit;
    private String name;
    public Ingredient(String amount, String typeAmount, String name){
        this.amount=amount;
        this.unit =typeAmount;
        this.name =name;
    }
    public Ingredient(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
