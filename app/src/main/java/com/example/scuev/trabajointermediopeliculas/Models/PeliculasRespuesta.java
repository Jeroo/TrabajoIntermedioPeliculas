package com.example.scuev.trabajointermediopeliculas.Models;

import android.os.AsyncTask;

import java.util.ArrayList;

public class PeliculasRespuesta {

    private ArrayList<PeliculasAPI> Search;

    public ArrayList<PeliculasAPI> getSearch() {
        return Search;
    }

    public void setSearch(ArrayList<PeliculasAPI> search) {
        Search = search;
    }

}
