package com.firstapp.groceryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT_ITEM = 101;
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;
    private GroceryReceipt groceryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        recyclerView = findViewById(R.id.recycler_view_grocery_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        GroceryReceipt groceryReceipt = (GroceryReceipt) intent.getSerializableExtra("grocery_receipt");

        if (groceryReceipt != null) {
            groceryItems = groceryReceipt;
            adapter = new GroceryItemAdapter(this, groceryItems);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK && data != null) {
            String newName = data.getStringExtra("item_name");
            String newPrice = data.getStringExtra("item_price");
            String newCategory = data.getStringExtra("item_category");

            int position = -1; // Identify position based on your logic
            for (int i = 0; i < groceryItems.size(); i++) {
                if (groceryItems.get(i).getName().equals(newName)) {
                    position = i;
                    break;
                }
            }

            if (position >= 0) {
                GroceryItem updatedItem = new GroceryItem(newName, newPrice, newCategory);
                adapter.updateItem(position, updatedItem);
            }
        }
    }
}
