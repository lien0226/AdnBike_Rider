package com.arife.adnbikerider.mvp.v;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UserModel;
import com.arife.adnbikerider.mvp.m.interactor.User.UserInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserView;
import com.arife.adnbikerider.mvp.p.User.UserPresenterImpl;
import com.chaos.view.PinView;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class Verify extends AppCompatActivity implements UserView, View.OnClickListener {

    private static UserModel userModel;
    private PinView pinView;
    private Button btnConfirm;
    private UserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        this.pinView = findViewById(R.id.pinView);
        this.btnConfirm = findViewById(R.id.btnConfirm);
        this.btnConfirm.setOnClickListener(this);
        this.userPresenter = new UserPresenterImpl(this, new UserInteractorImpl());

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            userModel = (UserModel) bundle.getSerializable("UserModel");
        }


    }

    @Override
    public void onSuccessUser(UserModel userModel) {
        Toasty.success(this, "Registro exitoso", Toasty.LENGTH_SHORT).show();
        this.finish();
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onErrorUser(String error) {
        Toasty.error(this, error, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfirm:
                this.userPresenter.onRegisterUser(this.ChargueData("Reg_Users"));
                break;
        }
    }

    public RestModel ChargueData(String command){
        RestModel restModel = new RestModel();
        restModel.setContext(getApplicationContext());
        restModel.setParameters(Charge.getInstance().genRegisterUser(userModel));
        restModel.setLink(Link_Base+command);
        return restModel;
    }
}