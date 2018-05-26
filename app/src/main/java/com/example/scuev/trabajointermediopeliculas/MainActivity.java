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
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ListView listaPeliculas;
    ArrayList<Peliculas> lista;
    SharedPreferences sp;
    Button verPeliculas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaPeliculas = (ListView) findViewById(R.id.lstPeliculas);
        verPeliculas = (Button) findViewById(R.id.btnPeliculasAgregadas);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);


        if(!sp.getBoolean("logged",false)){
            goToLoginActivity();
        }

        String usuario = sp.getString("usuario","");
        Boolean logeado = sp.getBoolean("logged",false);
        Log.d("Usuario",usuario);
        Log.d("logged",logeado.toString());



        lista = new ArrayList<Peliculas>();
       // getPeliculasDesdeOmdbapi("http://www.omdbapi.com/?t=deadpool&apikey=507a25ac");
       // lista = getPeliculasDesdeomdbapi("http://www.omdbapi.com/?t=deadpool&apikey=507a25ac");



        byte[] sevenItems = new byte[] { 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 };

        lista.add(new Peliculas(1, 1, "Pelicula mejor de todas", "Descripcion Pelicula mejor de todas",
                "Raynols", "Santo Domingo", "Pelicula mejor de todas", sevenItems));
        lista.add(new Peliculas(1, 1, "Pelicula mejor de todas", "Descripcion Pelicula mejor de todas",
                "Raynols", "Santo Domingo", "Pelicula mejor de todas", sevenItems));
        lista.add(new Peliculas(1, 1, "Pelicula mejor de todas", "Descripcion Pelicula mejor de todas",
                "Raynols", "Santo Domingo", "Pelicula mejor de todas", sevenItems));
        lista.add(new Peliculas(1, 1, "Pelicula mejor de todas", "Descripcion Pelicula mejor de todas",
                "Raynols", "Santo Domingo", "Pelicula mejor de todas", sevenItems));


        Adaptador miadaptador = new Adaptador(getApplicationContext(), lista);

        listaPeliculas.setAdapter(miadaptador);

        listaPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Peliculas objPelicula = (Peliculas) parent.getItemAtPosition(position);

                Intent pasar = new Intent(getApplicationContext(), DetallePeliculaActivity.class);
                pasar.putExtra("objPelicula", (Serializable) objPelicula);
                startActivity(pasar);

            }
        });

        verPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PeliculasActivity.class);
                startActivity(i);
            }
        });

    }

    public void goToLoginActivity(){
        Intent i = new Intent(this,LogonActivity.class);
        startActivity(i);
    }

    public void getPeliculasDesdeOmdbapi(String urlomdbapi){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // Create URL
                URL githubEndpoint = null;
                try {
                    githubEndpoint = new URL("http://www.omdbapi.com/?t=deadpool&apikey=507a25ac");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                try {
                    HttpsURLConnection myConnection =
                            (HttpsURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Mobile Safari/537.36");
                    myConnection.setRequestProperty("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                    myConnection.setRequestProperty("Contact-Me",
                            "hathibelagal@example.com");

                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");

                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        Date currentTime = Calendar.getInstance().getTime();
                        jsonReader.beginObject(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("Title")) { // Check if desired key
                                // Fetch the value as a String
                                String Title = jsonReader.nextString();



                                // Do something with the value
                                Log.d("GitHubJSON",Title);
                                /*lista.add(new Peliculas(1, 1, Title, "",
                                       "", "", currentTime, null));*/

                                break; // Break out of the loop


                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.close();
                        myConnection.disconnect();


                    } else {
                        // Error handling code goes here
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /*public ArrayList<Peliculas> getPeliculasDesdeomdbapi(String urlomdbapi) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn = null;

        try {
            url = new URL(urlomdbapi);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        try {
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inputLine;

        StringBuffer response = new StringBuffer();

        String json = "";

        while ((inputLine = in.readLine()) != null) {

            response.append(inputLine);

        }
        json = response.toString();

        JSONArray jsonArr = null;

        try {
            jsonArr = new JSONArray(json);
            ArrayList<Peliculas> objPelicula = new ArrayList<Peliculas>();
            Date currentTime = Calendar.getInstance().getTime();
            for (int i = 0; i < jsonArr.length(); i++) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArr.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Salida: ", jsonObject.optString("Title"));

                objPelicula.add(new Peliculas(i, i, jsonObject.optString("Title"), jsonObject.optString("Plot"),
                        jsonObject.optString("Actors"), jsonObject.optString("Country"), currentTime, null));

            }

            return objPelicula;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;

    }*/




}

