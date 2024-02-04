package com.example.mibarrioamigo;

public class Publicacion {
    private String usuarioId;
    private String contenido;

    public Publicacion() {
        // Constructor vacío requerido para Firebase
    }

    public Publicacion(String usuarioId, String contenido) {
        this.usuarioId = usuarioId;
        this.contenido = contenido;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}

