package com.arife.adnbikerider.mvc.m;

public class PerfilModel {
    private String Usuario;
    private String Email;
    private String Nombres;
    private String Apellidos;
    private String Celular;
    private String Direccion;

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
}
