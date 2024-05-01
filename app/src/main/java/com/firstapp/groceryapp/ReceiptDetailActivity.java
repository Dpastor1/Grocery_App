package com.firstapp.groceryapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);

        GroceryReceipt receipt = (GroceryReceipt) getIntent().getSerializableExtra("receipt");

        recyclerView = findViewById(R.id.recycler_view_grocery_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroceryItemAdapter(this, receipt);
        recyclerView.setAdapter(adapter);
    }
}
