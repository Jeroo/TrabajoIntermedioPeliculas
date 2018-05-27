package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scuev.trabajointermediopeliculas.utils.PeliculasSQLiteHelper;

public class DetallePeliculaActivity extends AppCompatActivity {

    ImageView imgPelicula;
    TextView titulo;
    TextView descripcion;

    Button agregar;
    Button borrar;
    Button actualizar;

    //Variable de almacenamiento
    PeliculasSQLiteHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);


        imgPelicula = (ImageView)findViewById(R.id.imgFotoPelicula);
        titulo = (TextView)findViewById(R.id.txtDetalleTitulo);
        descripcion = (TextView)findViewById(R.id.txtDetalleDescripcion);
        agregar = (Button)findViewById(R.id.btnAgregarPeliculaAPI);
        borrar = (Button)findViewById(R.id.btnBorrarPelicula);
        actualizar = (Button)findViewById(R.id.btnActualizarPelicula);

        Peliculas objPelicula = (Peliculas)getIntent().getExtras().getSerializable("objPelicula");

        String desde = (String) getIntent().getExtras().getSerializable("desde");

        if (desde.equals("Local")){

            agregar.setVisibility(View.GONE);
        }

        titulo.setText(objPelicula.getTitulo());
        descripcion.setText(objPelicula.getDescripcion());
        imgPelicula.setImageResource(R.drawable.ic_launcher_foreground);
       // imgPelicula.setImageResource(objPelicula.getImagen());

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Peliculas objPeliculaBorrar = (Peliculas)getIntent().getExtras().getSerializable("objPelicula");
                dbHelper = new PeliculasSQLiteHelper(DetallePeliculaActivity.this);
                db = dbHelper.getWritableDatabase();

                if(db != null){

                    dbHelper.borrarPelicula(db,(int)objPeliculaBorrar.getPeliculaId());
                }

                db.close();
                dbHelper.close();

                Toast toast1 = Toast.makeText(getApplicationContext(), "Pelicula borrada correctamente", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0, 0);
                toast1.show();


                goToPeliculasActivity();


            }
        });
    }

    public void goToPeliculasActivity(){
        Intent i = new Intent(this,PeliculasActivity.class);
        startActivity(i);
    }
}
