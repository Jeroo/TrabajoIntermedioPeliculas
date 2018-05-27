package com.example.scuev.trabajointermediopeliculas.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

public class PeliculasSQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "PeliculasDB.db";
    public static final int DB_VERSION = 1;
    Cursor fila;

    public PeliculasSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    private final String CREAR_TABLA_USUARIO = "CREATE TABLE usuarios (UsuarioId INTEGER PRIMARY KEY  AUTOINCREMENT, usuario TEXT, Nombres TEXT, Apellidos TEXT,Clave TEXT)";
    private final String CREAR_TABLA_PELICULAS = "CREATE TABLE peliculas (PeliculaId INTEGER PRIMARY KEY  AUTOINCREMENT, UsuarioId INTEGER, Titulo TEXT, Descripcion TEXT, ActorPrincipal TEXT,Ciudad TEXT,FechaVisionado TEXT,Imagen BLOB)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_USUARIO);
        db.execSQL(CREAR_TABLA_PELICULAS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS peliculas");
        onCreate(db);
    }

    /* ------------------ CRUD USUARIO -----------------------*/

    public void guardarUsuario(SQLiteDatabase db, String usuario,String Nombres, String Apellidos,String Clave){

        ContentValues nuevoUsuario = new ContentValues();

        //nuevoUsuario.put("UsuarioId", UsuarioId);
        nuevoUsuario.put("usuario", usuario);
        nuevoUsuario.put("Nombres", Nombres);
        nuevoUsuario.put("Apellidos", Apellidos);
        nuevoUsuario.put("Clave", Clave);

        db.insert("usuarios",null, nuevoUsuario);
    }


    public void actualizarUsuario(SQLiteDatabase db, int UsuarioId, String usuario,String Nombres, String Apellidos,String Clave){

        ContentValues actualizarUsuario = new ContentValues();

       // actualizarUsuario.put("UsuarioId", UsuarioId);
        actualizarUsuario.put("usuario", usuario);
        actualizarUsuario.put("Nombres", Nombres);
        actualizarUsuario.put("Apellidos", Apellidos);
        actualizarUsuario.put("Clave", Clave);

        String[] args = new String[]{Integer.toString(UsuarioId)};

        db.update("usuarios", actualizarUsuario,"UsuarioId=?",args);
    }

    public void borrarUsuario(SQLiteDatabase db, int UsuarioId){

        db.execSQL("DELETE FROM usuarios WHERE UsuarioId="+UsuarioId);

        String[] args = new String[]{Integer.toString(UsuarioId)};

        db.delete("usuarios","UsuarioId=?",args);

    }

    public Cursor obtenerUsuario(SQLiteDatabase db, String usuario){

        String[] columns = new String[]{"UsuarioId", "usuario","Nombres", "Apellidos"};
        String[] args = new String[]{usuario};

        return  db.query("usuarios",columns, "usuario=?", args,null,null,null);

    }

    public Cursor obtenerTodosUsuarios(SQLiteDatabase db){

        String[] columns = new String[]{"UsuarioId", "usuario","Nombres", "Apellidos"};

        return  db.query("usuarios",columns, null, null,null,null,"UsuarioId ASC");

    }

    /* ------------------ CRUD PELICULAS -----------------------*/

    public void guardarPelicula(SQLiteDatabase db, int UsuarioId, String Titulo,String Descripcion, String ActorPrincipal,String Ciudad,String FechaVisionado,byte[] Imagen){

        ContentValues nuevaPelicula = new ContentValues();

        nuevaPelicula.put("UsuarioId", UsuarioId);
        nuevaPelicula.put("Titulo", Titulo);
        nuevaPelicula.put("Descripcion", Descripcion);
        nuevaPelicula.put("ActorPrincipal", ActorPrincipal);
        nuevaPelicula.put("Ciudad", Ciudad);
        nuevaPelicula.put("FechaVisionado", FechaVisionado);
        nuevaPelicula.put("Imagen", Imagen);

        db.insert("peliculas",null, nuevaPelicula);
    }


    public void actualizarPelicula(SQLiteDatabase db,int PeliculaId, int UsuarioId, String Titulo,String Descripcion, String ActorPrincipal,String Ciudad,String FechaVisionado,byte[] Imagen){

        ContentValues actualizarPelicula = new ContentValues();

        actualizarPelicula.put("UsuarioId", UsuarioId);
        actualizarPelicula.put("Titulo", Titulo);
        actualizarPelicula.put("Descripcion", Descripcion);
        actualizarPelicula.put("ActorPrincipal", ActorPrincipal);
        actualizarPelicula.put("Ciudad", Ciudad);
        actualizarPelicula.put("FechaVisionado", FechaVisionado);
        actualizarPelicula.put("Imagen", Imagen);

        String[] args = new String[]{Integer.toString(PeliculaId)};

        db.update("peliculas", actualizarPelicula,"PeliculaId=?",args);
    }

    public void borrarPelicula(SQLiteDatabase db, int PeliculaId){

        db.execSQL("DELETE FROM peliculas WHERE PeliculaId="+PeliculaId);

        String[] args = new String[]{Integer.toString(PeliculaId)};

        db.delete("peliculas","PeliculaId=?",args);

    }

    public Cursor obtenerPelicula(SQLiteDatabase db, String Titulo,int usuarioId){

        String[] columns = new String[]{"peliculaId", "UsuarioId","Titulo", "Descripcion", "ActorPrincipal", "Ciudad", "FechaVisionado", "Imagen"};
        String[] args = new String[]{Titulo};

        fila = db.rawQuery("select peliculaId,UsuarioId,Titulo,Descripcion,ActorPrincipal,Ciudad,FechaVisionado,Imagen from peliculas where UsuarioId="+usuarioId,null);

        return fila;
       // return  db.query("peliculas",columns, "LOWER(Titulo)=?", args,null,null,null);

    }

    public Cursor obtenerTodasPeliculas(SQLiteDatabase db,int usuarioId){

        String[] columns = new String[]{"peliculaId", "UsuarioId","Titulo", "Descripcion", "ActorPrincipal", "Ciudad", "FechaVisionado", "Imagen"};

       // return  db.query("peliculas",columns, null, null,null,null,"peliculaId ASC");

        fila = db.rawQuery("select peliculaId,UsuarioId,Titulo,Descripcion,ActorPrincipal,Ciudad,FechaVisionado,Imagen from peliculas where UsuarioId="+usuarioId,null);

        return fila;

    }

    public Cursor login(SQLiteDatabase db, String usuario, String clave){

        String[] columns = new String[]{"UsuarioId", "usuario","Nombres", "Apellidos"};
        String[] args = new String[]{usuario};

        fila = db.rawQuery("select UsuarioId,usuario,Nombres,Apellidos from usuarios where LOWER(usuario)='"+usuario+"' and  clave='"+clave+"'",null);

        return fila;

    }

}
