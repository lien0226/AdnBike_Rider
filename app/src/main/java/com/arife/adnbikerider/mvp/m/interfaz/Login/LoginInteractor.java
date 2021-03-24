package com.arife.adnbikerider.mvp.m.interfaz.Login;

import com.arife.adnbikerider.mvc.m.LoginModel;
import com.arife.adnbikerider.mvc.m.RestModel;

public interface LoginInteractor {
    interface OnFinishLogin{
        void onError(String error);
        void onSuccess(LoginModel loginModel);
    }
    void onLogin(RestModel restModel, OnFinishLogin onFinishLogin);
}
