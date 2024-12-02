package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import android.widget.ProgressBar;


public class ShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private List<Item> items = new ArrayList<>();
    private ProgressBar progressBar;
    private Datos datos;
    int id;
    public static final String BASE_URI = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShopAdapter(this, items, this::purchaseItem);
        recyclerView.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        int id= prefs.getInt("id",-1);
        CargarTienda();

    }
    private void CargarTienda() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShopService lista = retrofit.create(ShopService.class);
        progressBar.setVisibility(View.VISIBLE);
        lista.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {


                if (response.isSuccessful() && response.body() != null) {
                    items.clear();
                    items.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("Error", "Error al cargar los items: " + response.code());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error", "Error de conexión: " + t.getMessage());
            }

        });
    }




    private void purchaseItem(Item item) {
        Toast.makeText(this, "Comprando " + item.getType(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopService lista = retrofit.create(ShopService.class);
        lista.purchaseItem(id, item.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ShopActivity.this,
                            "Compra realizada con éxito", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    String message;
                    switch (response.code()) {
                        case 401:
                            message = "No tienes suficientes recursos para comprar este item";
                            break;
                        case 404:
                            message = "Usuario o item no encontrado";
                            break;
                        default:
                            message = "Error en la compra: " + response.code();
                    }
                    Toast.makeText(ShopActivity.this, message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ShopActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error en la compra: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}