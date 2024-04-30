package com.firstapp.groceryapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ReceiptViewerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReceiptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_viewer);

        Log.d("ReceiptViewerActivity", "onCreate: Retrieving saved receipts...");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve saved receipts from SharedPreferences
        ArrayList<GroceryReceipt> savedReceipts = ReceiptManager.getSavedReceipts(this);
        Log.d("ReceiptViewerActivity", "Retrieved " + savedReceipts.size() + " saved receipts.");

        // Set up RecyclerView adapter
        adapter = new ReceiptAdapter(savedReceipts);
        recyclerView.setAdapter(adapter);
    }
}
