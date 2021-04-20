package com.arife.adnbikerider.mvp.m.interfaz.GetRoute;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;

import java.util.List;

public interface GetRouteInteractor {
    interface OnFinishGetRoute{
        void OnSucces(List<RouteModel> listRoute);
        void OnError(String Error);
    }

    void OnGetRoute(RestModel restModel, OnFinishGetRoute onFinishGetRoute);
}
