package com.arife.adnbikerider.mvp.SAVEPERFILUSER;

import com.arife.adnbikerider.mvc.m.RestModel;

public class PresenterSaveUser implements SaveUserPresenter, SaveUserInteractor.OnFinishSaveUser {
    private SaveUserView saveUserView;
    private SaveUserInteractor saveUserInteractor;

    public PresenterSaveUser(SaveUserView saveUserView, SaveUserInteractor saveUserInteractor) {
        this.saveUserView = saveUserView;
        this.saveUserInteractor = saveUserInteractor;
    }

    @Override
    public void OnSuccesSaveUser(String msg) {
        if(saveUserView!=null){
            saveUserView.OnSuccesSaveUser(msg);
        }
    }

    @Override
    public void OnErrorUser(String error) {
        if(saveUserView!=null){
            saveUserView.OnErrorUser(error);
        }
    }

    @Override
    public void OnSaveUser(RestModel restModel) {
        if (saveUserView!=null) {
            this.saveUserInteractor.OnSaveUser(restModel, this);
        }
    }

    @Override
    public void OnDestroy() {
        this.saveUserView=null;
    }
}
