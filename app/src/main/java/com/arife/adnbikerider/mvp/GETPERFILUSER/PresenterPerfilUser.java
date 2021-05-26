package com.arife.adnbikerider.mvp.GETPERFILUSER;

import com.arife.adnbikerider.mvc.m.PerfilModel;
import com.arife.adnbikerider.mvc.m.RestModel;

public class PresenterPerfilUser implements PerfilUserPresenter, PerfilUserInteractor.OnFinishPerfil {
    private PerfilUserView perfilUserView;
    private PerfilUserInteractor perfilUserInteractor;

    public PresenterPerfilUser(PerfilUserView perfilUserView, PerfilUserInteractor perfilUserInteractor) {
        this.perfilUserView = perfilUserView;
        this.perfilUserInteractor = perfilUserInteractor;
    }

    @Override
    public void OnSuccesPerfil(PerfilModel perfilModel) {
        if (this.perfilUserView!=null){
            this.perfilUserView.OnSuccesPerfil(perfilModel);
        }
    }

    @Override
    public void OnErrorPerfil(String error) {
        if (this.perfilUserView!=null){
            this.perfilUserView.OnErrorPerfil(error);
        }
    }

    @Override
    public void OnPerfilUser(RestModel restModel) {
        if (this.perfilUserView!=null){
            this.perfilUserInteractor.OnPerfilUser(restModel, this);
        }
    }

    @Override
    public void OnDestroy() {
        this.perfilUserView=null;
    }
}
