package com.example.scuev.trabajointermediopeliculas;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Peliculas implements Serializable {

    private int PeliculaId;
    private int UsuarioId;
    private String Titulo;
    private String Descripcion;
    private String ActorPrincipal;
    private String Ciudad;
    private String FechaVisionado;
    private byte[] Imagen;

    public Peliculas(int peliculaId, int usuarioId, String titulo, String descripcion, String actorPrincipal, String ciudad, String fechaVisionado, byte[] imagen) {
        PeliculaId = peliculaId;
        UsuarioId = usuarioId;
        Titulo = titulo;
        Descripcion = descripcion;
        ActorPrincipal = actorPrincipal;
        Ciudad = ciudad;
        FechaVisionado = fechaVisionado;
        Imagen = imagen;
    }


    public long getPeliculaId() {
        return PeliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        PeliculaId = peliculaId;
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getActorPrincipal() {
        return ActorPrincipal;
    }

    public void setActorPrincipal(String actorPrincipal) {
        ActorPrincipal = actorPrincipal;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getFechaVisionado() {
        return FechaVisionado;
    }

    public void setFechaVisionado(String fechaVisionado) {
        FechaVisionado = fechaVisionado;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
        Imagen = imagen;
    }
}
