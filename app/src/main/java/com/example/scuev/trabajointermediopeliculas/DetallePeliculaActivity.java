package com.example.scuev.trabajointermediopeliculas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallePeliculaActivity extends AppCompatActivity {

    ImageView imgPelicula;
    TextView titulo;
    TextView descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);


        imgPelicula = (ImageView)findViewById(R.id.imgFotoPelicula);
        titulo = (TextView)findViewById(R.id.txtDetalleTitulo);
        descripcion = (TextView)findViewById(R.id.txtDetalleDescripcion);

        Peliculas objPelicula = (Peliculas)getIntent().getExtras().getSerializable("objPelicula");


        titulo.setText(objPelicula.getTitulo());
        descripcion.setText(objPelicula.getDescripcion());
        imgPelicula.setImageResource(R.drawable.ic_launcher_foreground);
       // imgPelicula.setImageResource(objPelicula.getImagen());
    }
}
