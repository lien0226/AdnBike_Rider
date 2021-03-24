package com.arife.adnbikerider.mvp.p.User;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UserModel;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserView;

public class UserPresenterImpl implements UserPresenter, UserInteractor.OnFinishUser {
    private UserView userView;
    private UserInteractor userInteractor;

    public UserPresenterImpl(UserView userView, UserInteractor userInteractor) {
        this.userView = userView;
        this.userInteractor = userInteractor;
    }

    @Override
    public void onRegisterUser(RestModel restModel) {
        if (userView!=null){
            this.userInteractor.OnRegisterUser(restModel, this);
        }
    }

    @Override
    public void onDestroy() {
        this.userView=null;
    }

    @Override
    public void OnSucces(UserModel userModel) {
        if (userModel!=null){
            this.userView.onSuccessUser(userModel);
        }
    }

    @Override
    public void OnError(String error) {
        if (userView!=null){
            this.userView.onErrorUser(error);
        }

    }


}
