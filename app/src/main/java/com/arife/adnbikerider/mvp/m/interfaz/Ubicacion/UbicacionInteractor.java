package com.arife.adnbikerider.mvp.m.interfaz.Ubicacion;

import com.arife.adnbikerider.mvc.m.RestModel;

public interface UbicacionInteractor {
    interface OnFinishUbicacion{
        void OnSucces(String msg);
        void OnError(String error);
    }

    void OnRegisterUbicacion(RestModel restModel, OnFinishUbicacion onFinishUbicacion);
}
