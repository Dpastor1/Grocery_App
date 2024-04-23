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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_editor_page);

        editName = findViewById(R.id.edit_grocery_item_name);
        editPrice = findViewById(R.id.edit_grocery_item_price);
        editCategory = findViewById(R.id.edit_grocery_item_category);
        buttonSave = findViewById(R.id.button_save);

        Intent intent = getIntent();
        if (intent != null) {
            editName.setText(intent.getStringExtra("item_name"));
            editPrice.setText(intent.getStringExtra("item_price"));
            editCategory.setText(intent.getStringExtra("item_category"));
        }

        buttonSave.setOnClickListener(v -> {
            String newName = editName.getText().toString();
            String newPrice = editPrice.getText().toString();
            String newCategory = editCategory.getText().toString();

            if (newName.isEmpty() || newPrice.isEmpty() || newCategory.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Return updated values to the calling activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("item_name", newName);
            resultIntent.putExtra("item_price", newPrice);
            resultIntent.putExtra("item_category", newCategory);
            setResult(RESULT_OK, resultIntent); // Set the result code and intent
            finish(); // Close the activity
        });
    }
}
