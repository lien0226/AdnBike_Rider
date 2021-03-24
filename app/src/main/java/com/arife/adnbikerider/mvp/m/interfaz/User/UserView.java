package com.arife.adnbikerider.mvp.m.interfaz.User;

import com.arife.adnbikerider.mvc.m.UserModel;

public interface UserView {

    void onSuccessUser(UserModel userModel);
    void onErrorUser(String error);

}
