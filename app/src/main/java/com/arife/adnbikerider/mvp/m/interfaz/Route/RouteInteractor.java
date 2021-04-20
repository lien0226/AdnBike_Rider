package com.arife.adnbikerider.mvp.m.interfaz.Route;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;

import java.util.List;

public interface RouteInteractor {
    interface OnFinishRoute{
        void OnSucces(String msg);
        void OnError(String error);
    }

    void OnRegisterRoute(RestModel restModel, OnFinishRoute onFinishRoute);
}
