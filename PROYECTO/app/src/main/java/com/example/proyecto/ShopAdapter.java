package com.example.proyecto;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<ShopItem> shopItems;
    private Context context;

    public ShopAdapter(Context context, List<ShopItem> shopItems) {

        this.context = context;
        this.shopItems = shopItems;

    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ShopViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {

        ShopItem item = shopItems.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.itemImage.setImageResource(item.getImageResourceId());

    }

    @Override
    public int getItemCount() {

        return shopItems.size();

    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        Button buyButton;

        public ShopViewHolder(@NonNull View itemView) {

            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }
}