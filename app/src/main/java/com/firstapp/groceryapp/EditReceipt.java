package com.firstapp.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditReceipt extends AppCompatActivity {
    private EditText editName, editPrice, editCategory;
    private Button buttonSave;
    private String itemName, itemPrice, itemCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_editor_page);

        editName = findViewById(R.id.edit_grocery_item_name);
        editPrice = findViewById(R.id.edit_grocery_item_price);
        editCategory = findViewById(R.id.edit_grocery_item_category);
        buttonSave = findViewById(R.id.button_save);

        // Get item details from the Intent
        Intent intent = getIntent();
        itemName = intent.getStringExtra("item_name");
        itemPrice = intent.getStringExtra("item_price");
        itemCategory = intent.getStringExtra("item_category");

        // Populate EditText with current values
        editName.setText(itemName);
        editPrice.setText(itemPrice);
        editCategory.setText(itemCategory);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editName.getText().toString();
                String newPrice = editPrice.getText().toString();
                String newCategory = editCategory.getText().toString();
                Log.d("Name:", newName);
                Log.d("Price:", newPrice);
                Log.d("Category:", newCategory);

                if (newName.isEmpty() || newPrice.isEmpty() || newCategory.isEmpty()) {
                    Toast.makeText(EditReceipt.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Return the updated values to the MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("item_name", newName);
                resultIntent.putExtra("item_price", newPrice);
                resultIntent.putExtra("item_category", newCategory);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close the activity
            }
        });
    }
}
