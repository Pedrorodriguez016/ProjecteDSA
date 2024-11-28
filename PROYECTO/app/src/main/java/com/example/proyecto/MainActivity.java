package com.example.proyecto;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Validamos los campos de EditText
        editTextUsername = findViewById(R.id.name);
        editTextPassword = findViewById(R.id.password);

    }

    public void LoginOnClick(View v) {
        String name = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        //Validar el input
        if (name.isEmpty() || password.isEmpty()) {

            Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
            return;

        }

        Datos datosLogin = new Datos(name, password);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainService login = retrofit.create(MainService.class);
        Call<List<Datos>> call = login.loginUser(datosLogin);
        String respuesta = null;

        call.enqueue(new Callback<List<Datos>>() {

            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {

                if (response.isSuccessful()) {
                    //Login exitoso
                    Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    Log.i("INFO", "Sesion Iniciada");
                    //Empezar ShopActivity
                    Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                    startActivity(intent);


                }
                else {
                    //Por si falla el login
                    Toast.makeText(MainActivity.this, "Ha fallado el login. Inténtalo otra vez.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<Datos>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Error", t.getMessage());
            }

        });
    }


  public void registerOnClick(View v){
      Intent intent = new Intent (MainActivity.this, RegistrarUsuario.class);
      startActivity(intent);
    }
}




