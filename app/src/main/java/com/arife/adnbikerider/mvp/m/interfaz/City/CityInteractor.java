package com.arife.adnbikerider.mvp.m.interfaz.City;

import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.RestModel;

import java.util.ArrayList;
import java.util.List;

public interface CityInteractor {
    interface OnFinishCity{
        void OnSucces(ArrayList<CityModel> listCity);
        void OnError(String error);
    }

    void OnGetCity(RestModel restModel, OnFinishCity onFinishCity);
}
