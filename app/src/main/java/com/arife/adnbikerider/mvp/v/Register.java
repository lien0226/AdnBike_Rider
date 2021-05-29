package com.arife.adnbikerider.mvp.v;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UserModel;
import com.arife.adnbikerider.mvp.m.interactor.User.UserInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserView;
import com.arife.adnbikerider.mvp.p.User.UserPresenterImpl;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class Register extends AppCompatActivity implements UserView,View.OnClickListener {

    private ImageView btnBackLogin;
    private Button regUser;
    private UserModel usuarioModel;
    private UserPresenter userPresenter;
    private RestModel restModel;
    private EditText inputPassword, inputRepeatPassword, inputEmail, inputUser, inputTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.inputRepeatPassword = findViewById(R.id.inputRepeatPassword);
        this.inputTelephone = findViewById(R.id.inputTelephone);
        this.inputUser = findViewById(R.id.inputUser);
        this.regUser = findViewById(R.id.regUser);
        this.btnBackLogin = findViewById(R.id.btnBackLogin);
        this.btnBackLogin.setOnClickListener(this);
        this.regUser.setOnClickListener(this);

        this.userPresenter = new UserPresenterImpl(this, new UserInteractorImpl());

        this.usuarioModel = new UserModel();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackLogin:
                startActivity(new Intent(Register.this,Login.class));
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.regUser:
                Log.e("credenciales", this.inputPassword.getText()+" "+this.inputRepeatPassword.getText());
                if((this.inputPassword.getText().toString()).equals(this.inputRepeatPassword.getText().toString())){
                    this.usuarioModel.setOpcion("N");
                    this.usuarioModel.setId(Integer.parseInt("0"));
                    this.usuarioModel.setEmail(inputEmail.getText().toString());
                    this.usuarioModel.setLogin(inputUser.getText().toString());
                    this.usuarioModel.setPassword(inputPassword.getText().toString());
                    this.usuarioModel.setVerificacion("");
                    this.usuarioModel.setCodEstado("001001");
                    this.userPresenter.onRegisterUser(this.ChargueData("Reg_Users"));
                }else{
                    inputRepeatPassword.setError("contraseñas no coinciden");
                }
                break;
        }
    }

    @Override
    public void onSuccessUser(UserModel userModel) {
        this.usuarioModel.setOpcion("V");
        this.usuarioModel.setId(userModel.getId());
        this.usuarioModel.setVerificacion(userModel.getVerificacion());

        Bundle bundle = new Bundle();
        bundle.putSerializable("UserModel", this.usuarioModel);
        startActivity(new Intent(this, Verify.class).putExtras(bundle));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    @Override
    public void onErrorUser(String error) {
        Toasty.error(this, error, Toasty.LENGTH_SHORT).show();
    }

    public RestModel ChargueData(String command){
        RestModel restModel = new RestModel();
        restModel.setContext(getApplicationContext());
        restModel.setParameters(Charge.getInstance().genRegisterUser(this.usuarioModel));
        restModel.setLink(Link_Base+command);
        return restModel;
    }

    //y si los campos está vacios?

}