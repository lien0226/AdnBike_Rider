package com.arife.adnbikerider.mvp.m.interfaz.UnionRoute;

import com.arife.adnbikerider.mvc.m.RestModel;

public interface UnionRouteInteractor {
    interface OnFinishUnion{
        void OnSucces(String msg);
        void OnError(String error);
    }

    void OnRegisterUnion(RestModel restModel, OnFinishUnion onFinishUnion);
}
