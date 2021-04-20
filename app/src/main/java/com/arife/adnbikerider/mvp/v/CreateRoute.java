package com.arife.adnbikerider.mvp.v;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvp.m.interactor.City.CityInteractorImpl;
import com.arife.adnbikerider.mvp.m.interactor.Route.RouteInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityView;
import com.arife.adnbikerider.mvp.m.interfaz.Route.RoutePresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Route.RouteView;
import com.arife.adnbikerider.mvp.p.City.CityPresenterImpl;
import com.arife.adnbikerider.mvp.p.Route.RoutePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class CreateRoute extends AppCompatActivity implements CityView, RouteView, View.OnClickListener {

    private static GroupModel groupModel;
    private RouteModel routeModel;
    private CityPresenter cityPresenter;
    private RoutePresenter routePresenter;
    private Spinner spinner;
    private EditText nameRoute, descRoute;
    private Button btnSaveRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Crear Ruta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            groupModel = (GroupModel) bundle.getSerializable("GroupModel");
            Log.e("ID GRUPO", String.valueOf(groupModel.getId()));
        }
        this.cityPresenter = new CityPresenterImpl(this , new CityInteractorImpl());
        this.routePresenter = new RoutePresenterImpl(this, new RouteInteractorImpl());
        this.spinner = findViewById(R.id.spinner_city);
        this.nameRoute = findViewById(R.id.name_route);
        this.descRoute = findViewById(R.id.description_route);
        this.btnSaveRoute = findViewById(R.id.btnSaveRoute);

        this.btnSaveRoute.setOnClickListener(this);

        RestModel restModel = new RestModel();
        restModel.setContext(this);
        restModel.setLink(Link_Base+"get_ubigeo");

        this.cityPresenter.onGetCity(restModel);

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
    public void OnSuccess(ArrayList<CityModel> listCity) {

        List<String> cityString = new ArrayList<>();
        for (CityModel cityModel : listCity){
            cityString.add(cityModel.getFormato());
        }

        this.spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityString));
        Log.e("LISTA DE CIUDADES", listCity.toString());
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0){
                    Toasty.error(getApplicationContext(), "Selecciona una opcion", Toasty.LENGTH_SHORT).show();
                }else{
                    //String sCity = parent.getItemAtPosition(position).toString();
                    Log.e("Ciudad ; ",position+" "+listCity.get(position).getCodigo());
                    //cod = listCity.get(position).getCodigo();
                    // Toasty.success(getApplicationContext(), listCity.get(position).toString(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void OnSuccesRoute(String msg) {
        Toasty.success(this, msg);
        startActivity(new Intent(CreateRoute.this,GroupRoutes.class));
        //onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }

    @Override
    public void OnError(String error) {
        Toasty.error(this, error);
    }

    public RestModel ChargueData(RouteModel routeModel){
        RestModel restModel = new RestModel();
        restModel.setContext(getApplicationContext());
        restModel.setParameters(Charge.getInstance().genRoute(routeModel));
        restModel.setLink(Link_Base+"Reg_Ruta");
        return restModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveRoute:
                this.routeModel = new RouteModel();
                this.routeModel.setOpcion("N");
                this.routeModel.setId(0);
                this.routeModel.setIdGrupo(groupModel.getId());
                this.routeModel.setNameRuta(this.nameRoute.getText().toString());
                this.routeModel.setDescRuta(this.descRoute.getText().toString());
                this.routePresenter.OnRegisterRoute(this.ChargueData(routeModel));

                break;
        }
    }
}