package com.arife.adnbikerider.mvp.m.interfaz.Login;

import com.arife.adnbikerider.mvc.m.LoginModel;

public interface LoginView {

    void onError(String error);
    void toHome(LoginModel loginModel);

}
