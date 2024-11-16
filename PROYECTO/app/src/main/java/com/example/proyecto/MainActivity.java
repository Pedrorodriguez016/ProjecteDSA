package com.example.proyecto;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    EditText editText = (EditText) findViewById(R.id.name);
    String usrname = editText.getText().toString();
    EditText editText2 = (EditText) findViewById(R.id.password);
    String pswd = editText2.getText().toString();
    EditText editText3 = (EditText) findViewById(R.id.mail);
    String mail = editText3.getText().toString();

    public void LoginOnClick(View v) {
        Datos d = new Datos(usrname, pswd);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/dsaApp/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainService login = retrofit.create(MainService.class);
        Call<List<Datos>> call = login.CreateDATOS(d);
        String respuesta = null;
    }


    public void RegisteronClick(View v) {
        DatosRegistro d = new DatosRegistro(usrname, pswd, mail);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/dsaApp/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainService register = retrofit.create(MainService.class);
        Call<List<DatosRegistro>> call = register.CreateDATOSregistro(d);
        String respuesta = null;


    }
}




