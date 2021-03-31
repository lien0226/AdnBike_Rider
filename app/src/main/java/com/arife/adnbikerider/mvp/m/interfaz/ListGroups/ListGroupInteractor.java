package com.arife.adnbikerider.mvp.m.interfaz.ListGroups;

import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;

import java.util.List;

public interface ListGroupInteractor {
    interface OnFinishListGroup{
        void OnSucces(List<GroupModel> groupModels);
        void OnError(String error);
    }

    void onGetGroup(RestModel restModel, OnFinishListGroup onFinishListGroup);
}
