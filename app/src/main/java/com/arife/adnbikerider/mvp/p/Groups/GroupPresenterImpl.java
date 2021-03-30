package com.arife.adnbikerider.mvp.p.Groups;

import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupView;

public class GroupPresenterImpl implements GroupPresenter, GroupInteractor.OnFinishGroup {
    private GroupView groupView;
    private GroupInteractor groupInteractor;

    public GroupPresenterImpl(GroupView groupView, GroupInteractor groupInteractor) {
        this.groupView = groupView;
        this.groupInteractor = groupInteractor;
    }

    @Override
    public void onRegisterGroup(RestModel restModel) {
        if (groupView != null){
            this.groupInteractor.onRegisterGroup(restModel, this);
        }
    }

    @Override
    public void onDestroy() {
        this.groupView = null;
    }

    @Override
    public void OnSucces(String msg) {
        if (groupView!=null){
            this.groupView.OnSuccesGroup(msg);
        }
    }

    @Override
    public void OnError(String error) {
        if (groupView!=null){
            this.groupView.OnErrorGroup(error);
        }
    }
}
