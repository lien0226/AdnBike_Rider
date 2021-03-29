package com.arife.adnbikerider.mvp.m.interfaz.City;

import com.arife.adnbikerider.mvc.m.CityModel;

import java.util.ArrayList;
import java.util.List;

public interface CityView {

    void OnSuccess(ArrayList<CityModel> listCity);
    void OnError(String error);

}
