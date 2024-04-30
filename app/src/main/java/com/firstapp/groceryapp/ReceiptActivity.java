package com.firstapp.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

public class ReceiptActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "grocery_prefs";
    private static final String RECEIPT_KEY = "saved_receipt";
    private static final int REQUEST_CODE_EDIT_ITEM = 101;
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;
    private GroceryReceipt groceryItems;

    private GroceryReceipt groceryReceipt;
    private Button buttonSaveReceipt;
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
        buttonSaveReceipt = findViewById(R.id.button_save_receipt);
        buttonSaveReceipt.setOnClickListener(v -> saveReceipt());
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
    private void saveReceipt() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Serialize the grocery receipt into JSON
        Gson gson = new Gson();
        String jsonReceipt = gson.toJson(groceryItems);
        Log.d("Receipt Save", jsonReceipt);
        editor.putString(RECEIPT_KEY, jsonReceipt);
        editor.apply();

        // Display a message indicating successful save
        Toast.makeText(this, "Receipt saved successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to the menu screen
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the back stack
        startActivity(intent);
        finish(); // Finish this activity
    }
}
