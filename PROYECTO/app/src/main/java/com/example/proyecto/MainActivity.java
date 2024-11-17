package com.example.proyecto;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Validamos los campos de EditText
        editTextUsername = findViewById(R.id.name);
        editTextPassword = findViewById(R.id.password);
        editTextEmail = findViewById(R.id.mail);

    }

    public void LoginOnClick(View v) {
        String usrname = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        //Validar el input
        if (usrname.isEmpty() || password.isEmpty()) {

            Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
            return;

        }

        Datos datosLogin = new Datos(usrname, password);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/game/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainService login = retrofit.create(MainService.class);
        Call<List<Datos>> call = login.CreateDATOS(datosLogin);
        String respuesta = null;

        call.enqueue(new Callback<List<Datos>>() {

            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {

                if (response.isSuccessful()) {
                    //Login exitoso
                    Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();

                    //Empezar ShopActivity
                    Intent intent = new Intent(MainActivity.this, ShopActivity.class);



                } else {
                    //Por si falla el login
                    Toast.makeText(MainActivity.this, "Ha fallado el login. Inténtalo otra vez.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<Datos>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }


    public void RegisteronClick(View v) {

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();
        //Validar el input
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {

            Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
            return;

        }

        DatosRegistro d = new DatosRegistro(username, password, email);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/game/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainService register = retrofit.create(MainService.class);
        Call<List<DatosRegistro>> call = register.CreateDATOSregistro(d);
        String respuesta = null;

        call.enqueue(new Callback<List<DatosRegistro>>() {

            @Override
            public void onResponse(Call<List<DatosRegistro>> call, Response<List<DatosRegistro>> response) {

                if (response.isSuccessful()) {
                    //Registro con éxito
                    Toast.makeText(MainActivity.this, "Registro completado.", Toast.LENGTH_SHORT).show();
                    //Vaciar campos
                    editTextUsername.setText("");
                    editTextPassword.setText("");
                    editTextEmail.setText("");

                } else {
                    //Falla el registro
                    Toast.makeText(MainActivity.this, "Registro fallido. Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<DatosRegistro>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error de network:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        })
    ;}
}




