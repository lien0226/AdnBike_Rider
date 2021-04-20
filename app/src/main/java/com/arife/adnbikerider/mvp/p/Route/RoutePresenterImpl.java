package com.arife.adnbikerider.mvp.p.Route;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvp.m.interfaz.Route.RouteInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.Route.RoutePresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Route.RouteView;

import java.util.List;

public class RoutePresenterImpl implements RoutePresenter, RouteInteractor.OnFinishRoute {
    private RouteView routeView;
    private RouteInteractor routeInteractor;

    public RoutePresenterImpl(RouteView routeView, RouteInteractor routeInteractor) {
        this.routeView = routeView;
        this.routeInteractor = routeInteractor;
    }

    @Override
    public void OnRegisterRoute(RestModel restModel) {
        if (this.routeView!=null){
            this.routeInteractor.OnRegisterRoute(restModel, this);
        }
    }

    @Override
    public void OnDestroy() {
        this.routeView=null;
    }

    @Override
    public void OnSucces(String msg) {
        if (this.routeView!=null){
            this.routeView.OnSuccesRoute(msg);
        }
    }

    @Override
    public void OnError(String error) {
        if (this.routeView!=null){
            this.routeView.OnError(error);
        }
    }
}
