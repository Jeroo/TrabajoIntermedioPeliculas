package com.example.scuev.trabajointermediopeliculas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaPeliculas;
    ArrayList<Peliculas> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaPeliculas = (ListView)findViewById(R.id.lstPeliculas);

        lista = new ArrayList<Peliculas>();

        lista.add(new Peliculas(1,"Titulo1","Pelicula mejor de todas",R.drawable.ic_launcher_background));
        lista.add(new Peliculas(2,"Titulo2","Pelicula mejor 2 de todas",R.drawable.ic_launcher_background));
        lista.add(new Peliculas(3,"Titulo3","Pelicula mejor 3 de todas",R.drawable.ic_launcher_background));

        Adaptador miadaptador = new Adaptador(getApplicationContext(),lista);

        listaPeliculas.setAdapter(miadaptador);

    }
}
