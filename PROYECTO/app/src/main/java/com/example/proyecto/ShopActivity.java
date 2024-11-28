package com.example.proyecto;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import org.chromium.base.Callback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup adapter with click listener
        adapter = new ShopAdapter(this, items, item -> {
            // Handle buy click
            purchaseItem(item);
        });
        recyclerView.setAdapter(adapter);

        // Get items from server
        loadItems();
    }

    private void loadItems() {
        // Create Retrofit instance and service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopService service = retrofit.create(ShopService.class);

        // Make API call
        service.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    items.clear();
                    items.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(ShopActivity.this,
                        "Error loading items: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void purchaseItem(Item item) {
        // Implement purchase logic
        Toast.makeText(this, "Attempting to purchase " + item.getType(),
                Toast.LENGTH_SHORT).show();
        // Make API call to purchase item
    }
}