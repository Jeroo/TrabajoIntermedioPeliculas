package com.example.scuev.trabajointermediopeliculas;

public class Usuarios {

    private long UsuarioId;
    private String Nombres;
    private String Apellidos;
    private String Usuario;
    private String Clave;

    public Usuarios(long usuarioId, String nombres, String apellidos, String usuario, String clave) {
        UsuarioId = usuarioId;
        Nombres = nombres;
        Apellidos = apellidos;
        Usuario = usuario;
        Clave = clave;
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
