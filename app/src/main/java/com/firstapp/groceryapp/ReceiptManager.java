package com.firstapp.groceryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ReceiptManager {

    private static final String PREFS_NAME = "receipt_prefs";
    private static final String RECEIPT_KEY = "saved_receipts";

    public static void saveReceipt(Context context, GroceryReceipt receipt) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Retrieve existing saved receipts
        ArrayList<GroceryReceipt> savedReceipts = getSavedReceipts(context);

        // Add the new receipt to the list
        savedReceipts.add(receipt);

        // Serialize the updated list and save it back to SharedPreferences
        Gson gson = new Gson();
        String jsonReceipts = gson.toJson(savedReceipts);
        editor.putString(RECEIPT_KEY, jsonReceipts);
        editor.apply();
    }

    public static ArrayList<GroceryReceipt> getSavedReceipts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonReceipts = prefs.getString(RECEIPT_KEY, null);

        ArrayList<GroceryReceipt> savedReceipts = new ArrayList<>();
        if (jsonReceipts != null) {
            Gson gson = new Gson();
            savedReceipts = gson.fromJson(jsonReceipts, new TypeToken<ArrayList<GroceryReceipt>>() {}.getType());
            Log.d("ReceiptManager", "Saved receipts: " + savedReceipts.toString());
        }
        else{
            Log.d("ReceiptManager", "No saved receipts found.");
        }
        return savedReceipts;
    }
}
