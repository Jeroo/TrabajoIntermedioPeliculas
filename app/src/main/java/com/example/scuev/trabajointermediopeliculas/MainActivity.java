package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.scuev.trabajointermediopeliculas.API.PeliculasService;
import com.example.scuev.trabajointermediopeliculas.Models.PeliculasAPI;
import com.example.scuev.trabajointermediopeliculas.Models.PeliculasRespuesta;
import com.example.scuev.trabajointermediopeliculas.utils.PeliculasSQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    Button buscarPeliculasAPI;
    EditText txtBuscarAPI;
    Adaptador miadaptador;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listaPeliculas = (ListView) findViewById(R.id.lstPeliculas);
        txtBuscarAPI = (EditText)findViewById(R.id.txtBuscarPeliculasAPI);
        verPeliculas = (Button) findViewById(R.id.btnPeliculasAgregadas);
        buscarPeliculasAPI = (Button) findViewById(R.id.btnBuscarPeliculasAPI);


       // lista = new ArrayList<Peliculas>();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerPeliculas("batman");

        miadaptador = new Adaptador(getApplicationContext(), lista);

        listaPeliculas.setAdapter(miadaptador);


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


        buscarPeliculasAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerPeliculas(txtBuscarAPI.getText().toString());

                miadaptador = new Adaptador(getApplicationContext(), lista);

                listaPeliculas.setAdapter(miadaptador);


            }
        });


    }

    private void obtenerPeliculas(String s) {


        PeliculasService service = retrofit.create(PeliculasService.class);
        Call<PeliculasRespuesta> peliculasRespuestaCall = service.obtenerListaPeliculas(s.toLowerCase().trim(), "507a25ac");

       try {

            PeliculasRespuesta peliculasRespuesta = peliculasRespuestaCall.execute().body();

           ArrayList<PeliculasAPI> listaPeliculas = peliculasRespuesta.getSearch();
           byte[] sevenItems = new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
           lista = new ArrayList<Peliculas>();

           for (int i = 0; i < listaPeliculas.size(); i++) {

               PeliculasAPI p = listaPeliculas.get(i);

               try {
                   lista.add(new Peliculas(1, 1, p.getTitle(), p.getTitle(),
                           p.getType(), "Santo Domingo", "", recoverImageFromUrl(p.getPoster().toString())));
               } catch (Exception e) {
                   e.printStackTrace();
               }


               System.out.println("Titulo Pelicula: " + p.getTitle());
               Log.d("ListaPeliculasBatman", "Titulo Pelicula: " + p.getTitle());

           }

        } catch (IOException e) {
            e.printStackTrace();
        }


       /* peliculasRespuestaCall.enqueue(new Callback<PeliculasRespuesta>() {
            @Override
            public void onResponse(Call<PeliculasRespuesta> call, Response<PeliculasRespuesta> response) {


                if (response.isSuccessful()) {
                    lista = new ArrayList<Peliculas>();
                    PeliculasRespuesta peliculasRespuesta = response.body();
                    ArrayList<PeliculasAPI> listaPeliculas = peliculasRespuesta.getSearch();
                    byte[] sevenItems = new byte[]{0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};


                    for (int i = 0; i < listaPeliculas.size(); i++) {

                        PeliculasAPI p = listaPeliculas.get(i);

                        try {
                            lista.add(new Peliculas(0, 0, p.getTitle(), p.getTitle(),
                                    p.getType(), "Santo Domingo", "", recoverImageFromUrl(p.getPoster().toString())));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


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
        });*/

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