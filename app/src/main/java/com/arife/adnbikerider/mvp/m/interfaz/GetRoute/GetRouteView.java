package com.arife.adnbikerider.mvp.m.interfaz.GetRoute;

import com.arife.adnbikerider.mvc.m.RouteModel;

import java.util.List;

public interface GetRouteView {
    void OnGetRoute(List<RouteModel> listRoute);
    void OnError(String error);
}
