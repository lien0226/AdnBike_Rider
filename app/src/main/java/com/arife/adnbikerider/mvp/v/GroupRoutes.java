package com.arife.adnbikerider.mvp.v;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.arife.adnbikerider.AppData.Adapters.ListGroupAdapter;
import com.arife.adnbikerider.AppData.Adapters.ListRouteAdapter;
import com.arife.adnbikerider.AppData.Fragments.FragmentGroups;
import com.arife.adnbikerider.AppData.Fragments.PendingMove;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvp.m.interactor.Route.GetRouteInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.GetRoute.GetRoutePresenter;
import com.arife.adnbikerider.mvp.m.interfaz.GetRoute.GetRouteView;
import com.arife.adnbikerider.mvp.p.Route.GetRoutePresenterImpl;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;

public class GroupRoutes extends AppCompatActivity implements View.OnClickListener, GetRouteView , PendingMove {

    private static GroupModel groupModel;
    private FloatingActionButton create_route;
    private ListRouteAdapter listRouteAdapter;
    private RecyclerView routeRecycler;
    private GetRoutePresenter getRoutePresenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_routes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Nombre Grupo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.create_route = findViewById(R.id.create_route);
        this.create_route.setOnClickListener(this);
        this.swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            groupModel = (GroupModel) bundle.getSerializable("GroupModel");
            Log.e("ID GRUPO", String.valueOf(groupModel.getId()));
        }
        this.getRoutePresenter = new GetRoutePresenterImpl(this, new GetRouteInteractorImpl());
        this.getRoutePresenter.OnGetRoute(this.ChargueData());
        this.routeRecycler = findViewById(R.id.group_route_recycler);

        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRoutePresenter.OnGetRoute(ChargueData());
                listRouteAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_route:

                Bundle bundle = new Bundle();
                bundle.putSerializable("GroupModel", groupModel);
                startActivity(new Intent(GroupRoutes.this, CreateRoute.class).putExtras(bundle));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                this.finish();

                break;
        }
    }

    @Override
    public void OnGetRoute(List<RouteModel> listRoute) {
        this.listRouteAdapter = new ListRouteAdapter(listRoute, this , this);
        this.routeRecycler.setAdapter(listRouteAdapter);
        this.routeRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnError(String error) {
        Log.e("GroupRoutesClass", error);
    }

    public RestModel ChargueData(){
        Log.e("ChargueData: ", String.valueOf(groupModel.getId()));
        RestModel restModel = new RestModel();
        restModel.setContext(this);
        restModel.setLink(Charge.getInstance().genGetRoute(groupModel));
        return restModel;
    }

    @Override
    public void PendingMoveAction() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}