package com.arife.adnbikerider.mvp.p.Login;

import com.arife.adnbikerider.mvc.m.LoginModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interactor.Login.LoginInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginView;
import com.arife.adnbikerider.mvp.m.interfaz.Login.OnLoginFinishListener;
import com.arife.adnbikerider.mvp.v.Login;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnFinishLogin {

    private LoginView view;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView view, LoginInteractor loginInteractor) {
        this.view = view;
        this.interactor = loginInteractor;
    }

    @Override
    public void onGetLogin(RestModel restModel) {
        if (view!=null){
            this.interactor.onLogin(restModel, this);
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void onError(String error) {
        if (view!=null){
            this.view.onError(error);
        }
    }

    @Override
    public void onSuccess(LoginModel loginModel) {
        if (view!=null){
            this.view.toHome(loginModel);
        }
    }
}
