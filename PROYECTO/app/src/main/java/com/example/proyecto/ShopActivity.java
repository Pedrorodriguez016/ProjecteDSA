package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = getShopItems();
        adapter = new ShopAdapter(this, items, item -> purchaseItem(item));
        recyclerView.setAdapter(adapter);
    }

    private List<Item> getShopItems() {
        List<Item> shopItems = new ArrayList<>();
        shopItems.add(new Item(0, "KNIFE", 154));
        shopItems.add(new Item(1, "ARMOR", 175));
        shopItems.add(new Item(2, "SWORD", 4896));
        shopItems.add(new Item(3, "POTION", 34));
        shopItems.add(new Item(4, "SHIELD", 200));
        return shopItems;
    }

    private void purchaseItem(Item item) {
        Toast.makeText(this, "Attempting to purchase " + item.getType(), Toast.LENGTH_SHORT).show();
    }
}