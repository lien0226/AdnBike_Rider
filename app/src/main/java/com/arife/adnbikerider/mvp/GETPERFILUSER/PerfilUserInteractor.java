package com.arife.adnbikerider.mvp.GETPERFILUSER;

import com.arife.adnbikerider.mvc.m.PerfilModel;
import com.arife.adnbikerider.mvc.m.RestModel;

public interface PerfilUserInteractor {
    interface OnFinishPerfil extends PerfilUserView{
        @Override
        void OnSuccesPerfil(PerfilModel perfilModel);

        @Override
        void OnErrorPerfil(String error);
    }

    void OnPerfilUser(RestModel restModel, OnFinishPerfil onFinishPerfil);
}
