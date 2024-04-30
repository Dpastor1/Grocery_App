package com.firstapp.groceryapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReceiptLoader {

    public static GroceryReceipt loadReceipt(String jsonReceipt) {
        Gson gson = new Gson();
        Type groceryItemType = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> items = gson.fromJson(jsonReceipt, groceryItemType);

        GroceryReceipt receipt = new GroceryReceipt();
        receipt.setItems(items);
        receipt.setSize(items.size());
        Log.d("ReceiptLoader", "Loaded receipt: " + receipt.toString());
        return receipt;
    }
}
