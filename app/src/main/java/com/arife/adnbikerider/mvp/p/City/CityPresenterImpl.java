package com.arife.adnbikerider.mvp.p.City;

import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityView;

import java.util.ArrayList;
import java.util.List;

public class CityPresenterImpl implements CityPresenter, CityInteractor.OnFinishCity {
    private CityView cityView;
    private CityInteractor cityInteractor;

    public CityPresenterImpl(CityView cityView, CityInteractor cityInteractor) {
        this.cityView = cityView;
        this.cityInteractor = cityInteractor;
    }

    @Override
    public void onGetCity(RestModel restModel) {
        if (cityView!=null){
            this.cityInteractor.OnGetCity(restModel, this);
        }
    }

    @Override
    public void onDestroy() {
        this.cityView = null;
    }

    @Override
    public void OnSucces(ArrayList<CityModel> listCity) {
        if (this.cityView != null){
            this.cityView.OnSuccess(listCity);
        }
    }

    @Override
    public void OnError(String error) {
        if (this.cityView != null){
            this.cityView.OnError(error);
        }
    }


}
