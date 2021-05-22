package com.arife.adnbikerider.mvc.m;

import com.google.android.gms.maps.model.LatLng;

public class UbicacionModel {
    private int IdUbicacion;
    private int IdRuta;
    private String latlong;
    private LatLng ubicacion;
    private Long Time;

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

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

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }
}

