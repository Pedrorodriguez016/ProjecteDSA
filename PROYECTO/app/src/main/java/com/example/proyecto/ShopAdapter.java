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
import java.util.List;
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<Item> items;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(String.valueOf(item.getId())); // o el campo que quieras mostrar
        holder.itemPrice.setText(item.getValue() + " coins");

        // Set image based on item value u otro criterio
        switch(item.getValue()) {
            case 175:
                holder.itemImage.setImageResource(R.drawable.armadura);
                break;
            case 154:
                holder.itemImage.setImageResource(R.drawable.knife);
                break;
            case 34:
                holder.itemImage.setImageResource(R.drawable.potion);
                break;
            case 4896:
                holder.itemImage.setImageResource(R.drawable.sword);
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