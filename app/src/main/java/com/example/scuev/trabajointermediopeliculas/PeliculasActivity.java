package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PeliculasActivity extends AppCompatActivity {

    ListView listaPeliculas;
    ArrayList<Peliculas> lista;
    SharedPreferences sp;
    Button agregarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        listaPeliculas = (ListView) findViewById(R.id.lstPeliculas);
        agregarPelicula = (Button) findViewById(R.id.btnAgregarPelicula);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);


        if(!sp.getBoolean("logged",false)){
            goToLoginActivity();
        }

        agregarPelicula.setOnClickListener(new View.OnClickListener() {
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
}
