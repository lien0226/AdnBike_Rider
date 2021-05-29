package com.arife.adnbikerider.mvp.SAVEPERFILUSER;

import com.arife.adnbikerider.mvc.m.RestModel;

public interface SaveUserInteractor {
    interface OnFinishSaveUser extends SaveUserView{
        @Override
        void OnSuccesSaveUser(String msg);

        @Override
        void OnErrorUser(String error);
    }

    void OnSaveUser(RestModel restModel, OnFinishSaveUser onFinishSaveUser);
}
