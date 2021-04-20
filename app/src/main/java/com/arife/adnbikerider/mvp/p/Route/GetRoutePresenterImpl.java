package com.arife.adnbikerider.mvp.p.Route;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvp.m.interfaz.GetRoute.GetRouteInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.GetRoute.GetRoutePresenter;
import com.arife.adnbikerider.mvp.m.interfaz.GetRoute.GetRouteView;

import java.util.List;

public class GetRoutePresenterImpl implements GetRoutePresenter, GetRouteInteractor.OnFinishGetRoute {

    private GetRouteView getRouteView;
    private GetRouteInteractor getRouteInteractor;

    public GetRoutePresenterImpl(GetRouteView getRouteView, GetRouteInteractor getRouteInteractor) {
        this.getRouteView = getRouteView;
        this.getRouteInteractor = getRouteInteractor;
    }

    @Override
    public void OnGetRoute(RestModel restModel) {
        if (getRouteView != null){
            this.getRouteInteractor.OnGetRoute(restModel, this);
        }
    }

    @Override
    public void OnDestroy() {
        this.getRouteView=null;
    }

    @Override
    public void OnSucces(List<RouteModel> listRoute) {
        if (getRouteView!=null){
            this.getRouteView.OnGetRoute(listRoute);
        }
    }

    @Override
    public void OnError(String Error) {
        if (getRouteView!=null){
            this.getRouteView.OnError(Error);
        }
    }
}
