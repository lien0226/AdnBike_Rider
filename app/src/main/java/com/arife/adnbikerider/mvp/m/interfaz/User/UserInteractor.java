package com.arife.adnbikerider.mvp.m.interfaz.User;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UserModel;

public interface UserInteractor {

    interface OnFinishUser{
        void OnSucces(UserModel userModel);
        void OnError(String error);
    }

    void OnRegisterUser(RestModel restModel, OnFinishUser onFinishUser);

}
