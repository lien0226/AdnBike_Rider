package com.arife.adnbikerider.mvp.m.interfaz.User;

import com.arife.adnbikerider.mvc.m.RestModel;

public interface UserPresenter {

    void onRegisterUser(RestModel restModel);
    void onDestroy();

}
