package com.firstapp.groceryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {
    private Context context;
    private GroceryReceipt groceryItems;

    // Define a constant for the request code
    public static final int REQUEST_CODE_EDIT_ITEM = 101;

    public GroceryItemAdapter(Context context, GroceryReceipt groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroceryItem item = groceryItems.get(position);

        holder.textName.setText(item.getName());
        holder.textPrice.setText(item.getPrice());
        holder.textCategory.setText(item.getCategory());

        // Navigate to EditReceipt activity on item click with a result
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditReceipt.class);
            intent.putExtra("item_name", item.getName());
            intent.putExtra("item_price", item.getPrice());
            intent.putExtra("item_category", item.getCategory());
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
        });
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    // Create a method to update an item at a given position
    public void updateItem(int position, GroceryItem updatedItem) {
        groceryItems.set(position, updatedItem);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textPrice;
        public TextView textCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_grocery_item_name);
            textPrice = itemView.findViewById(R.id.text_grocery_item_price);
            textCategory = itemView.findViewById(R.id.text_grocery_item_category);
        }
    }
}
