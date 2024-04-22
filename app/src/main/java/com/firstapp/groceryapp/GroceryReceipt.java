package com.firstapp.groceryapp;

import com.firstapp.groceryapp.GroceryItem;

import java.io.Serializable;
import java.util.ArrayList;

public class GroceryReceipt implements Serializable {
    private ArrayList<GroceryItem> items;
    private int size;
    private String name;

    public GroceryReceipt() {
        this.items = new ArrayList<GroceryItem>();
        this.size = 0;
        this.name = "UnnamedGroceryReceipt";
    }

    public GroceryReceipt(String name) {
        this.items = new ArrayList<>();
        this.size = 0;
        this.name = name;
    }

    public void addItem(GroceryItem item) {
        items.add(item);
        size++;
    }

    public ArrayList<GroceryItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder stringOutput = new StringBuilder();
        for (GroceryItem item : items) {
            stringOutput.append(item.toString()).append("\n");
        }
        return stringOutput.toString();
    }
}
