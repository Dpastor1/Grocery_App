package com.firstapp.groceryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {

    private ArrayList<GroceryReceipt> receipts;

    public ReceiptAdapter(ArrayList<GroceryReceipt> receipts) {
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
        holder.bind(receipt);
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

        public void bind(GroceryReceipt receipt) {
            receiptTextView.setText(receipt.toString());
        }
    }
}
