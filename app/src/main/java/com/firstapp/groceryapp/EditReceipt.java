package com.firstapp.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditReceipt extends AppCompatActivity {
    private EditText editName, editPrice;
    private Spinner spinnerCategory;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_editor_page);

        editName = findViewById(R.id.edit_grocery_item_name);
        editPrice = findViewById(R.id.edit_grocery_item_price);
        spinnerCategory = findViewById(R.id.edit_grocery_item_category);
        buttonSave = findViewById(R.id.button_save_receipt);

        // Set up the category spinner with the array from strings.xml
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.grocery_categories,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            editName.setText(intent.getStringExtra("item_name"));
            editPrice.setText(intent.getStringExtra("item_price"));

            // Set the selected category based on the incoming data
            String currentCategory = intent.getStringExtra("item_category");
            if (currentCategory != null) {
                int position = categoryAdapter.getPosition(currentCategory);
                spinnerCategory.setSelection(position);
            }
        }

        buttonSave.setOnClickListener(v -> {
            String newName = editName.getText().toString();
            String newPrice = editPrice.getText().toString();
            String newCategory = spinnerCategory.getSelectedItem().toString();

            if (newName.isEmpty() || newPrice.isEmpty()) {
                Toast.makeText(this, "Name and Price are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("item_name", newName);
            resultIntent.putExtra("item_price", newPrice);
            resultIntent.putExtra("item_category", newCategory);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
