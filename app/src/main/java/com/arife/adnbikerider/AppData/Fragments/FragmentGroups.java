package com.arife.adnbikerider.AppData.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arife.adnbikerider.AppData.Adapters.ListGroupAdapter;
import com.arife.adnbikerider.AppData.Sesion;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interactor.Group.ListGroupInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.ListGroups.ListGroupPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.ListGroups.ListGroupView;
import com.arife.adnbikerider.mvp.p.Groups.ListGroupPresenterImpl;
import com.arife.adnbikerider.mvp.v.CreateGroup;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class FragmentGroups extends Fragment implements ListGroupView,View.OnClickListener{
    private View v;
    private FloatingActionButton create_group;
    private ListGroupPresenter listGroupPresenter;
    private RecyclerView groupRecycler;
    private ListGroupAdapter listGroupAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //FragmentGroups fragmentGroups = new FragmentGroups();
        this.v = inflater.inflate(R.layout.groups_fragment, container, false);
        this.create_group = v.findViewById(R.id.create_group);
        this.create_group.setOnClickListener(this);
        this.listGroupPresenter = new ListGroupPresenterImpl(this, new ListGroupInteractorImpl());
        this.listGroupPresenter.onGetGroup(this.ChargueData());
        this.groupRecycler = v.findViewById(R.id.group_recycler);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_group:
                startActivity(new Intent(FragmentGroups.this.getActivity(), CreateGroup.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
        }
    }

    @Override
    public void OnSuccesListGroup(List<GroupModel> listgroup) {
        this.listGroupAdapter = new ListGroupAdapter(listgroup, this.getActivity().getApplicationContext(),this.pendingMove, this.getActivity());
        this.groupRecycler.setAdapter(listGroupAdapter);
        this.groupRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext()));
    }

    @Override
    public void OnErrorListGroup(String error) {
        Log.e("sin datos", error);
    }

    public RestModel ChargueData(){
        RestModel restModel = new RestModel();
        restModel.setContext(this.getActivity().getApplicationContext());
        restModel.setLink(Charge.getInstance().genGetGroup());
        return restModel;
    }

    private PendingMove pendingMove;

    public FragmentGroups(PendingMove pendingMove) {
        this.pendingMove = pendingMove;
    }

}
