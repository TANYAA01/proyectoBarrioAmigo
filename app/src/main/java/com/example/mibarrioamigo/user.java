package com.example.mibarrioamigo;

import java.io.Serializable;

public class user implements Serializable {

    private String nombres;
    private String apellidos;
    private String cedula;
    private String celular;
    private String direccion;
    private String referencia;
    private String email;
    private String contrasena;

    // Constructor vacío necesario para algunas operaciones
    public user() {
        // Constructor vacío por defecto requerido por Firebase
    }

    // Constructor con todos los campos
    public user(String nombres, String apellidos, String cedula, String celular,
                String direccion, String referencia, String email, String contrasena) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.celular = celular;
        this.direccion = direccion;
        this.referencia = referencia;
        this.email = email;
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
