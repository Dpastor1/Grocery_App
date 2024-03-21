package com.firstapp.groceryapp;

import java.util.ArrayList;

public class GroceryReceipt {
    private ArrayList<GroceryItem> items;
    private int size;

    public GroceryReceipt(){
        this.items = new ArrayList<GroceryItem>();
        this.size = 0;
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

    public int size(){
        return items.size();
    }
}


