package com.arife.adnbikerider.mvp.m.interactor.Ubicacion;

import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.Ubicacion.UbicacionInteractor;

public class UbicacionInteractorImpl implements UbicacionInteractor, ServerRestResponse {
    private OnFinishUbicacion onFinishUbicacion;
    @Override
    public void OnRegisterUbicacion(RestModel restModel, OnFinishUbicacion onFinishUbicacion) {

    }
    @Override
    public void toSuccessRest(String Response) {

    }

    @Override
    public void toFailedRest(String Response) {

    }
}
