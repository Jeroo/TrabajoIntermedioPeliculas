package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scuev.trabajointermediopeliculas.API.PeliculasService;
import com.example.scuev.trabajointermediopeliculas.Models.PeliculasAPI;
import com.example.scuev.trabajointermediopeliculas.Models.PeliculasRespuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listaPeliculas;
    ArrayList<Peliculas> lista;
    SharedPreferences sp;
    Button verPeliculas;
    Adaptador miadaptador;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaPeliculas = (ListView) findViewById(R.id.lstPeliculas);
        verPeliculas = (Button) findViewById(R.id.btnPeliculasAgregadas);
        lista = new ArrayList<Peliculas>();
        //obtenerPeliculas();

        miadaptador = new Adaptador(getApplicationContext(), lista);

        listaPeliculas.setAdapter(miadaptador);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        sp = getSharedPreferences("login", Context.MODE_PRIVATE);


        if (!sp.getBoolean("logged", false)) {
            goToLoginActivity();
        }

        String usuario = sp.getString("usuario", "");
        Boolean logeado = sp.getBoolean("logged", false);
        Log.d("Usuario", usuario);
        Log.d("logged", logeado.toString());


        listaPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Peliculas objPelicula = (Peliculas) parent.getItemAtPosition(position);

                Intent pasar = new Intent(getApplicationContext(), DetallePeliculaActivity.class);
                pasar.putExtra("objPelicula", (Serializable) objPelicula);
                pasar.putExtra("desde", "Remoto");
                startActivity(pasar);

            }
        });

        verPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PeliculasActivity.class);
                startActivity(i);
            }
        });

    }

    private void obtenerPeliculas() {


        PeliculasService service = retrofit.create(PeliculasService.class);
        Call<PeliculasRespuesta> peliculasRespuestaCall = service.obtenerListaPeliculas("batman", "507a25ac");

        peliculasRespuestaCall.enqueue(new Callback<PeliculasRespuesta>() {
            @Override
            public void onResponse(Call<PeliculasRespuesta> call, Response<PeliculasRespuesta> response) {
                if (response.isSuccessful()) {
                   // lista = new ArrayList<Peliculas>();
                    PeliculasRespuesta peliculasRespuesta = response.body();
                    ArrayList<PeliculasAPI> listaPeliculas = peliculasRespuesta.getSearch();
                    byte[] sevenItems = new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};


                    for (int i = 0; i < listaPeliculas.size(); i++) {

                        PeliculasAPI p = listaPeliculas.get(i);

                        /*try {
                            lista.add(new Peliculas(0, 0, p.getTitle(), p.getTitle(),
                                    p.getType(), "Santo Domingo", "", recoverImageFromUrl(p.getPoster().toString())));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/


                        System.out.println("Titulo Pelicula: " + p.getTitle());
                        Log.d("ListaPeliculasBatman", "Titulo Pelicula: " + p.getTitle());

                    }
                } else {

                    Toast toast1 = Toast.makeText(getApplicationContext(), "onResponse: " + response.errorBody().toString(), Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<PeliculasRespuesta> call, Throwable t) {

                Toast toast1 = Toast.makeText(getApplicationContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }
        });

    }

    public byte[] recoverImageFromUrl(String urlText) throws Exception {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }

        return output.toByteArray();
    }

    public void goToLoginActivity() {
        Intent i = new Intent(this, LogonActivity.class);
        startActivity(i);
    }
}