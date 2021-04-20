package com.arife.adnbikerider.mvp.p.Route;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRouteInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRoutePresenter;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRouteView;

public class UnionRoutePresenterImpl implements UnionRoutePresenter, UnionRouteInteractor.OnFinishUnion {
    private UnionRouteView unionRouteView;
    private UnionRouteInteractor unionRouteInteractor;

    public UnionRoutePresenterImpl(UnionRouteView unionRouteView, UnionRouteInteractor unionRouteInteractor) {
        this.unionRouteView = unionRouteView;
        this.unionRouteInteractor = unionRouteInteractor;
    }

    @Override
    public void OnRegisterUnion(RestModel restModel) {
        if (unionRouteView!=null){
            this.unionRouteInteractor.OnRegisterUnion(restModel, this);
        }
    }

    @Override
    public void OnDestroy() {
        this.unionRouteView = null;
    }

    @Override
    public void OnSucces(String msg) {
        this.unionRouteView.OnSuccesUnion(msg);
    }

    @Override
    public void OnError(String error) {
        this.unionRouteView.OnErrorUnion(error);
    }
}
