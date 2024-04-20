package com.firstapp.groceryapp;

import java.util.ArrayList;

public class GroceryReceipt {
    private ArrayList<GroceryItem> items;
    private int size;
    private String name;

    public GroceryReceipt(){
        this.items = new ArrayList<GroceryItem>();
        this.size = 0;
        this.name = "UnnamedGroceryReceipt";
    }
    public GroceryReceipt(String name){
        this.items = new ArrayList<GroceryItem>();
        this.size = 0;
        this.name = "name";
    }

    public void addItem(GroceryItem item){
        items.add(item);
        size ++;
    }

    public GroceryItem pop(){
        if (size > 0){
            GroceryItem temp = items.get(0);
            items.remove(0);
            size --;
            return temp;
        }
        return null;
    }


    // --------------------Getters-----------------------------------
    public int size(){
        return items.size();
    }

    public String getName(){
        return name;
    }


    @Override
    public String toString(){
        String stringOutput = "";
        for (GroceryItem item : items){
            stringOutput = stringOutput + item.toString() + "\n";
        }
        return stringOutput;
    }
}




