package com.arife.adnbikerider.mvc.m;

public class UbicacionModel {
    private int IdUbicacion;
    private int IdRuta;
    private int IdPersona;
    private int Latitud;
    private int Longitud;

    public int getIdUbicacion() {
        return IdUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        IdUbicacion = idUbicacion;
    }

    public int getIdRuta() {
        return IdRuta;
    }

    public void setIdRuta(int idRuta) {
        IdRuta = idRuta;
    }

    public int getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(int idPersona) {
        IdPersona = idPersona;
    }

    public int getLatitud() {
        return Latitud;
    }

    public void setLatitud(int latitud) {
        Latitud = latitud;
    }

    public int getLongitud() {
        return Longitud;
    }

    public void setLongitud(int longitud) {
        Longitud = longitud;
    }
}

