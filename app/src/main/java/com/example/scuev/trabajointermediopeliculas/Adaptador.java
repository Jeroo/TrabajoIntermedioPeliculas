package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adaptador extends BaseAdapter {

    Context contexto;
    List<Peliculas> listaPeliculas;

    public Adaptador(Context contexto, List<Peliculas> listaPeliculas) {
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
        return listaPeliculas.get(position).getId();
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
        imagen.setImageResource(listaPeliculas.get(position).getImagen());

        return vista;


    }
}
