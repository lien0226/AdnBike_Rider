package com.arife.adnbikerider.mvp.p.Ubicacion;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.Ubicacion.UbicacionInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.Ubicacion.UbicacionPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Ubicacion.UbicacionView;

public class UbicacionPresenterImpl implements UbicacionPresenter, UbicacionInteractor.OnFinishUbicacion {
    private UbicacionView ubicacionView;
    private UbicacionInteractor ubicacionInteractor;

    public UbicacionPresenterImpl(UbicacionView ubicacionView, UbicacionInteractor ubicacionInteractor) {
        this.ubicacionView = ubicacionView;
        this.ubicacionInteractor = ubicacionInteractor;
    }

    @Override
    public void OnRegisterUbicacion(RestModel restModel) {
        if (this.ubicacionView!=null){
            this.ubicacionInteractor.OnRegisterUbicacion(restModel, this);
        }
    }

    @Override
    public void OnDestroy() {
        this.ubicacionView = null;
    }
    @Override
    public void OnSucces(String msg) {
        if (this.ubicacionView!=null){
            this.ubicacionView.OnSuccesUbicacion(msg);
        }
    }

    @Override
    public void OnError(String error) {
        if (this.ubicacionView!=null){
            this.ubicacionView.OnErrorUbicacion(error);
        }
    }
}