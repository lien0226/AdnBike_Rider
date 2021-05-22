package com.arife.adnbikerider.mvp.GETROUTEPERSON;

import com.arife.adnbikerider.mvc.m.UbicacionModel;

import java.util.List;

public interface RoutePersonView {
    void OnSucessRoutePerson(List<UbicacionModel> ubicacionModels);//pera :V
    void OnError(String Error);
}
