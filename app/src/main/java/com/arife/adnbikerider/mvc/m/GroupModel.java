package com.arife.adnbikerider.mvc.m;

import com.arife.adnbikerider.AppData.Adapters.OnImageResponse;

import java.io.Serializable;

public class GroupModel implements Serializable {
    private String opcion;
    private int id;
    private String GroupName;
    private String GroupDescription;
    private String Ubigeo;
    private String tipo;
    private String codEstado;
    private String Image;
    private OnImageResponse onImageResponse;

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupDescription() {
        return GroupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        GroupDescription = groupDescription;
    }

    public String getUbigeo() {
        return Ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        Ubigeo = ubigeo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public OnImageResponse getOnImageResponse() {
        return onImageResponse;
    }

    public void setOnImageResponse(OnImageResponse onImageResponse) {
        this.onImageResponse = onImageResponse;
    }
}
