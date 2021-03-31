package com.arife.adnbikerider.mvp.m.interfaz.ListGroups;

import com.arife.adnbikerider.mvc.m.GroupModel;

import java.util.List;

public interface ListGroupView {

    void OnSuccesListGroup(List<GroupModel> listgroup);
    void OnErrorListGroup(String error);

}
