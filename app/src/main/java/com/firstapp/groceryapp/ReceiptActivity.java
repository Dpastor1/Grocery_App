package com.firstapp.groceryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        recyclerView = findViewById(R.id.recycler_view_grocery_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the grocery receipt from the intent
        Intent intent = getIntent();
        GroceryReceipt groceryReceipt = (GroceryReceipt) intent.getSerializableExtra("grocery_receipt");

        // Initialize the adapter with the list of grocery items and set it to the RecyclerView
        if (groceryReceipt != null) {
            adapter = new GroceryItemAdapter(this, groceryReceipt.getItems());
            recyclerView.setAdapter(adapter);
        }
    }

}
