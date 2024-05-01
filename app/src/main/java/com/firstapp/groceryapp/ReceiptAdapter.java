package com.firstapp.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {
    private ArrayList<GroceryReceipt> receipts;
    private Context context; // Add context to access startActivity

    public ReceiptAdapter(Context context, ArrayList<GroceryReceipt> receipts) {
        this.context = context;
        this.receipts = receipts;
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);
        return new ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        GroceryReceipt receipt = receipts.get(position);
        holder.receiptTextView.setText(receipt.getName()); // Change here to show receipt name or something meaningful
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReceiptDetailActivity.class);
            intent.putExtra("receipt", receipt);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public static class ReceiptViewHolder extends RecyclerView.ViewHolder {
        private TextView receiptTextView;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            receiptTextView = itemView.findViewById(R.id.receiptTextView);
        }
    }
}
