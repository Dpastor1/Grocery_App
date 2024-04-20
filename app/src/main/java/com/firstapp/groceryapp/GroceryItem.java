package com.firstapp.groceryapp;

public class GroceryItem {
    private String cost;
    private String itemName;
    private String category;

    public GroceryItem(String itemName, String cost, String category){
        this.itemName = itemName;
        this.cost = cost;
        this.category = category;
    }

    //-----------Getters and Setters -------------------

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setCost(String cost){
        this.cost = cost;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCost(){
        return cost;
    }
    public String getItemName(){
        return itemName;
    }

    public String getCategory(){
        return category;
    }


    @Override
    public String toString(){
        return itemName + " " + cost + " " + category;
    }
}
