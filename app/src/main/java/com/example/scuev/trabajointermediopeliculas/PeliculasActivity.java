package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scuev.trabajointermediopeliculas.utils.PeliculasSQLiteHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class PeliculasActivity extends AppCompatActivity {

    ListView listaPeliculas;
    ArrayList<Peliculas> lista;
    SharedPreferences sp;
    Button agregarPelicula;
    Button buscarPelicula;
    EditText txtbuscar;
    Adaptador miadaptador;

    PeliculasSQLiteHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        listaPeliculas = (ListView) findViewById(R.id.lstPeliculas);
        agregarPelicula = (Button) findViewById(R.id.btnAgregarPelicula);
        buscarPelicula = (Button) findViewById(R.id.btnBuscarPeliculasLocal);
        txtbuscar = (EditText)findViewById(R.id.txtBuscarPeliculasLocal);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);


        if(!sp.getBoolean("logged",false)){
            goToLoginActivity();
        }

        agregarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PeliculasActivity.this,AgregarPeliculaActivity.class);
                startActivity(i);
            }
        });



        dbHelper = new PeliculasSQLiteHelper(PeliculasActivity.this);
        db = dbHelper.getReadableDatabase();
        if(db != null) {
            lista = new ArrayList<Peliculas>();
            Cursor fila = dbHelper.obtenerTodasPeliculas(db);


            if (fila.moveToFirst()){

                do {

                    lista.add(new Peliculas(fila.getInt(0), fila.getInt(1),
                            fila.getString(2), fila.getString(3),
                            fila.getString(4), fila.getString(5), fila.getString(6), fila.getBlob(7)));


                }while (fila.moveToNext());
            }


        }
        miadaptador = new Adaptador(getApplicationContext(), lista);

        listaPeliculas.setAdapter(miadaptador);

        listaPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Peliculas objPelicula = (Peliculas) parent.getItemAtPosition(position);

                Intent pasar = new Intent(getApplicationContext(), DetallePeliculaActivity.class);
                pasar.putExtra("objPelicula", (Serializable) objPelicula);
                pasar.putExtra("desde", "Local");
                startActivity(pasar);

            }
        });


        buscarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper = new PeliculasSQLiteHelper(PeliculasActivity.this);
                db = dbHelper.getReadableDatabase();
                if(db != null) {
                    lista = new ArrayList<Peliculas>();
                    Cursor fila = dbHelper.obtenerPelicula(db,txtbuscar.getText().toString().toLowerCase());


                    if (fila.moveToFirst()){

                        do {

                            lista.add(new Peliculas(fila.getInt(0), fila.getInt(1),
                                    fila.getString(2), fila.getString(3),
                                    fila.getString(4), fila.getString(5), fila.getString(6), fila.getBlob(7)));


                        }while (fila.moveToNext());
                    }


                }

                if (lista.size() > 0){

                    Toast toast1 = Toast.makeText(getApplicationContext(), "Cantidad de Peliculas encontradas: "+lista.size(), Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0, 0);
                    toast1.show();

                    Adaptador miadaptador = new Adaptador(getApplicationContext(), lista);

                    listaPeliculas.setAdapter(miadaptador);

                    listaPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Peliculas objPelicula = (Peliculas) parent.getItemAtPosition(position);

                            Intent pasar = new Intent(getApplicationContext(), DetallePeliculaActivity.class);
                            pasar.putExtra("objPelicula", (Serializable) objPelicula);
                            pasar.putExtra("desde", "Local");
                            startActivity(pasar);

                        }
                    });

                }else{


                    Toast toast1 = Toast.makeText(getApplicationContext(), "No hubo coincidencias", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0, 0);
                    toast1.show();
                }




            }
        });
    }

    public void goToLoginActivity(){
        Intent i = new Intent(this,LogonActivity.class);
        startActivity(i);
    }
}
