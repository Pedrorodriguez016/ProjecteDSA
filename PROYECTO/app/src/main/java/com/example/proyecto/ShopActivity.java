package com.example.proyecto;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shopRecyclerView;
    private ShopAdapter shopAdapter;
    private List<ShopItem> shopItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        shopItems = new ArrayList<>();

        // Objetos de ejemplo
        shopItems.add(new ShopItem("Cuchillo", 19.99, R.drawable.knife));
        shopItems.add(new ShopItem("Escudo", 29.99, R.drawable.escudo));
        shopItems.add(new ShopItem("Sword", 39.99, R.drawable.sword));
        shopItems.add(new ShopItem("Pócima", 9.99, R.drawable.potion));
        shopItems.add(new ShopItem("Armadura", 99.99, R.drawable.armadura));
        // Add more items as needed

        shopAdapter = new ShopAdapter(this, shopItems);
        shopRecyclerView.setAdapter(shopAdapter);

    }
}