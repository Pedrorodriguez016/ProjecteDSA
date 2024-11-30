package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Item> items = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onBuyClick(Item item);
    }

    public ShopAdapter(Context context, List<Item> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }
    public void updateItems(List<Item> newItems) {
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getType());
        holder.itemPrice.setText(item.getValue() + " coins");

        switch(item.getType()) {
            case "SWORD":
                holder.itemImage.setImageResource(R.drawable.sword);
                break;
            case "ARMOR":
                holder.itemImage.setImageResource(R.drawable.armadura);
                break;
            case "SHIELD":
                holder.itemImage.setImageResource(R.drawable.escudo);
                break;
            case "KNIFE":
                holder.itemImage.setImageResource(R.drawable.knife);
                break;
            case "POTION":
                holder.itemImage.setImageResource(R.drawable.potion);
                break;
        }

        holder.buyButton.setOnClickListener(v -> {
            if(listener != null) {
                listener.onBuyClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        Button buyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }
}