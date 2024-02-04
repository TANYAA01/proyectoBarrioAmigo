package com.example.mibarrioamigo;

public class Evento {

    private String id;
    private String tipo;
    private String lugar;
    private String fecha;
    private String hora;

    private String imageUrl;

    public Evento() {
        // Constructor vacío requerido por Firebase
    }

    public Evento(String id, String tipo, String lugar, String fecha, String hora) {
        this.id = id;
        this.tipo = tipo;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Agrega este método para obtener la URL de la imagen
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter para el ID del evento
    public void setId(String id) {
        this.id = id;
    }

    // Getter para el ID del evento
    public String getId() {
        return id;
    }

    // Setter para el tipo de evento
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter para el tipo de evento
    public String getTipo() {
        return tipo;
    }

    // Setter para el lugar del evento
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // Getter para el lugar del evento
    public String getLugar() {
        return lugar;
    }

    // Setter para la fecha del evento
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // Getter para la fecha del evento
    public String getFecha() {
        return fecha;
    }

    // Setter para la hora del evento
    public void setHora(String hora) {
        this.hora = hora;
    }

    // Getter para la hora del evento
    public String getHora() {
        return hora;
    }
}
