package com.arife.adnbikerider.mvp.GETROUTEPERSON;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UbicacionModel;

import java.util.List;

public interface RoutePersonInteractor {

    interface OnFinishRoutePerson extends RoutePersonView{
        @Override
        void OnSucessRoutePerson(List<UbicacionModel> ubicacionModels);

        @Override
        void OnError(String Error);
    }

    void OnRoutePerson(RestModel restModel , OnFinishRoutePerson onFinishRoutePerson);

}
