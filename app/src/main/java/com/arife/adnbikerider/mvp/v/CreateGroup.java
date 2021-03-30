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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interactor.City.CityInteractorImpl;
import com.arife.adnbikerider.mvp.m.interactor.Group.GroupInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityView;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupView;
import com.arife.adnbikerider.mvp.p.City.CityPresenterImpl;
import com.arife.adnbikerider.mvp.p.Groups.GroupPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class CreateGroup extends AppCompatActivity implements CityView, GroupView, View.OnClickListener {

    private Spinner spinner;
    private CityPresenter cityPresenter;
    private GroupPresenter groupPresenter;
    private Button btnSaveGroup;
    private GroupModel groupModel;
    private EditText groupName, groupDesc;
    private String cod;
    private RadioGroup tipoGrupo;
    private RadioButton publico;
    private RadioButton privado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Crear Grupo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.spinner = findViewById(R.id.spinner_city);
        this.btnSaveGroup = findViewById(R.id.btnSaveGroup);
        this.groupName = findViewById(R.id.name_group);
        this.groupDesc = findViewById(R.id.description_group);
        this.tipoGrupo = findViewById(R.id.rgTipoGrupo);
        this.privado = findViewById(R.id.rbPrivado);
        this.publico = findViewById(R.id.rbPublico);
        this.cod = "";

        this.btnSaveGroup.setOnClickListener(this);

        this.groupPresenter = new GroupPresenterImpl(this, new GroupInteractorImpl());
        this.cityPresenter = new CityPresenterImpl(this, new CityInteractorImpl());

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
                return true;
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
                    cod = listCity.get(position).getCodigo();
                    // Toasty.success(getApplicationContext(), listCity.get(position).toString(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void OnError(String error) {
        Toasty.error(this, error).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveGroup:
                this.groupModel = new GroupModel();
                int idRbSelected = tipoGrupo.getCheckedRadioButtonId();
                if (idRbSelected == privado.getId()) {
                    this.groupModel.setTipo("005001");
                } else if (idRbSelected == publico.getId()) {
                    this.groupModel.setTipo("005002");
                } else {
                    Toasty.error(this, "Selecciona una ciudad");
                }
                this.groupModel.setOpcion("N");
                this.groupModel.setId(0);
                this.groupModel.setGroupName(this.groupName.getText().toString());
                this.groupModel.setGroupDescription(this.groupDesc.getText().toString());
                this.groupModel.setUbigeo(cod);
                this.groupModel.setCodEstado("001001");
                this.groupPresenter.onRegisterGroup(this.ChargueData(groupModel));
                break;

        }
    }

    @Override
    public void OnSuccesGroup(String msg) {
        Toasty.success(this, msg);
        startActivity(new Intent(CreateGroup.this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void OnErrorGroup(String error) {
        Toasty.error(this, error);
    }

    public RestModel ChargueData(GroupModel groupModel){
        RestModel restModel = new RestModel();
        restModel.setContext(getApplicationContext());
        restModel.setParameters(Charge.getInstance().genGroup(groupModel));
        restModel.setLink(Link_Base+"Reg_Group");
        return restModel;
    }
}