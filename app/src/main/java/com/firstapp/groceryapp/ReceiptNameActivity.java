package com.firstapp.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiptNameActivity extends AppCompatActivity {

    EditText editReceiptName;
    Button buttonSaveReceiptName;

    GroceryReceipt receipt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_name);

        editReceiptName = findViewById(R.id.edit_receipt_name);
        buttonSaveReceiptName = findViewById(R.id.button_save_receipt_name);

        // Retrieve the GroceryReceipt object from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("grocery_receipt")) {
            receipt = (GroceryReceipt) intent.getSerializableExtra("grocery_receipt");
        } else {
            // Handle the case where receipt is not provided
            // You may want to finish the activity or show an error message
        }

        buttonSaveReceiptName.setOnClickListener(v -> {
            // Get the entered receipt name
            String receiptName = editReceiptName.getText().toString().trim();

            // Check if the name is not empty
            if (!receiptName.isEmpty()) {
                // Update the receipt name
                receipt.setName(receiptName);

                // Create an intent to start ReceiptActivity
                Intent receiptIntent = new Intent(ReceiptNameActivity.this, ReceiptActivity.class);
                receiptIntent.putExtra("grocery_receipt", receipt);
                startActivity(receiptIntent);
            } else {
                // Notify the user if the name is empty
                editReceiptName.setError("Receipt name cannot be empty");
            }
        });
    }

}
