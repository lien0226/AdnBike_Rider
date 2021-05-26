package com.arife.adnbikerider.mvp.GETPERFILUSER;

import com.arife.adnbikerider.mvc.m.PerfilModel;

public interface PerfilUserView {
    void OnSuccesPerfil(PerfilModel perfilModel);
    void OnErrorPerfil(String error);
}
