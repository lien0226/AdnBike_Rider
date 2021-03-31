package com.arife.adnbikerider.mvp.p.Groups;

import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.ListGroups.ListGroupInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.ListGroups.ListGroupPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.ListGroups.ListGroupView;

import java.util.List;

public class ListGroupPresenterImpl implements ListGroupPresenter, ListGroupInteractor.OnFinishListGroup {
    private ListGroupView listGroupView;
    private ListGroupInteractor listGroupInteractor;

    public ListGroupPresenterImpl(ListGroupView listGroupView, ListGroupInteractor listGroupInteractor) {
        this.listGroupView = listGroupView;
        this.listGroupInteractor = listGroupInteractor;
    }

    @Override
    public void onGetGroup(RestModel restModel) {
        if (listGroupView != null){
            listGroupInteractor.onGetGroup(restModel, this);
        }
    }

    @Override
    public void onDestroy() {
        listGroupView = null;
    }

    @Override
    public void OnSucces(List<GroupModel> groupModels) {
        if (listGroupView != null){
            this.listGroupView.OnSuccesListGroup(groupModels);
        }
    }

    @Override
    public void OnError(String error) {
        if (listGroupView != null){
            this.listGroupView.OnErrorListGroup(error);
        }
    }
}
