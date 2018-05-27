package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scuev.trabajointermediopeliculas.Models.PeliculasAPI;

import java.util.ArrayList;
import java.util.List;

import static com.example.scuev.trabajointermediopeliculas.R.drawable.ic_launcher_background;

public class Adaptador extends BaseAdapter {

    Context contexto;
    ArrayList<Peliculas> listaPeliculas;

    public Adaptador(Context contexto,ArrayList<Peliculas> listaPeliculas) {
        this.contexto = contexto;
        this.listaPeliculas = listaPeliculas;
    }

    @Override
    public int getCount() {

        return listaPeliculas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPeliculas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaPeliculas.get(position).getPeliculaId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;
        LayoutInflater inflater = LayoutInflater.from(contexto);

        vista = inflater.inflate(R.layout.itemlistview,null);

        ImageView imagen = (ImageView)vista.findViewById(R.id.imgPelicula);

        TextView titulo = (TextView)vista.findViewById(R.id.txttitulo);

        TextView descripcion = (TextView)vista.findViewById(R.id.txtdescripcion);

        titulo.setText(listaPeliculas.get(position).getTitulo().toString());
        descripcion.setText(listaPeliculas.get(position).getDescripcion().toString());
        imagen.setImageResource(R.drawable.logo);
       // imagen.setImageDrawable(R.drawable.ic_launcher_background);
       // imagen.setImageBitmap(BitmapFactory.decodeByteArray(listaPeliculas.get(position).getImagen(), 0, listaPeliculas.get(position).getImagen().length));

        return vista;


    }

}
