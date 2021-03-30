package com.arife.adnbikerider.mvp.m.interfaz.Group;

import com.arife.adnbikerider.mvc.m.RestModel;

public interface GroupInteractor {
    interface OnFinishGroup{
        void OnSucces(String msg);
        void OnError(String error);
    }

    void onRegisterGroup(RestModel restModel, OnFinishGroup onFinishGroup);
}
