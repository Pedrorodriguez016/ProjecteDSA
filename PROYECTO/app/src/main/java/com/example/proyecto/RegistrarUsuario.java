package com.example.proyecto;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarUsuario extends AppCompatActivity{
    private TextView editTextUsername;
    private TextView editTextContraseña;
    private TextView editTextEmail;
    private TextView editTextConfirmacion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.usernameText);
        editTextContraseña = findViewById(R.id.contraseñaText);
        editTextEmail = findViewById(R.id.emailText);
        editTextConfirmacion= findViewById(R.id.confirmacionText);

    }
    public void RegisteronClick(View v) {

        String name = editTextUsername.getText().toString();
        String contraseña = editTextContraseña.getText().toString();
        String confirmacion= editTextConfirmacion.getText().toString();
        String email = editTextEmail.getText().toString();
        //Validar el input
        if(!contraseña.equals(confirmacion)) {
            Toast.makeText(this, "Contraseñas incorrectas", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty() || contraseña.isEmpty() || email.isEmpty()||confirmacion.isEmpty()) {

            Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
            return;

        }

        DatosRegistro d = new DatosRegistro(name, contraseña, email);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistroService register = retrofit.create(RegistroService.class);
        Call<List<DatosRegistro>> call = register.newUser(d);
        String respuesta = null;

        call.enqueue(new Callback<List<DatosRegistro>>() {

            @Override
            public void onResponse(Call<List<DatosRegistro>> call, Response<List<DatosRegistro>> response) {

                if (response.isSuccessful()) {
                    //Registro con éxito
                    Toast.makeText(RegistrarUsuario.this, "Registro completado.", Toast.LENGTH_SHORT).show();
                    //Vaciar campos
                    editTextUsername.setText("");
                    editTextContraseña.setText("");
                    editTextEmail.setText("");


                } else {
                    //Falla el registro
                    Toast.makeText(RegistrarUsuario.this, "Registro fallido. Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<DatosRegistro>> call, Throwable t) {

                Toast.makeText(RegistrarUsuario.this, "Error de network:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        })
        ;}
}

