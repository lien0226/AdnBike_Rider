package com.arife.adnbikerider.mvp.GETROUTEPERSON;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UbicacionModel;

import java.util.List;

public class PresenterRoutePerson implements RoutePersonPresenter, RoutePersonInteractor.OnFinishRoutePerson {

    private RoutePersonInteractor routePersonInteractor;
    private RoutePersonView routePersonView;

    public PresenterRoutePerson(RoutePersonInteractor routePersonInteractor, RoutePersonView routePersonView) {
        this.routePersonInteractor = routePersonInteractor;
        this.routePersonView = routePersonView;
    }

    @Override
    public void OnSucessRoutePerson(List<UbicacionModel> ubicacionModels) {
        if (routePersonView!=null){
            this.routePersonView.OnSucessRoutePerson(ubicacionModels);
        }
    }

    @Override
    public void OnError(String Error) {
        if (routePersonView!=null){
            this.routePersonView.OnError(Error);
        }
    }

    @Override
    public void OnRoutePerson(RestModel restModel) {
        if (this.routePersonInteractor!=null){
            this.routePersonInteractor.OnRoutePerson(restModel,this);
        }
    }

    @Override
    public void OnDestroy() {
        this.routePersonView=null;
    }
}
