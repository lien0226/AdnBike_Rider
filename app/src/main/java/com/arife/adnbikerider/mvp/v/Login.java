package com.arife.adnbikerider.mvp.v;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arife.adnbikerider.AppData.Sesion;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.LoginModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interactor.Login.LoginInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginView;
import com.arife.adnbikerider.mvp.p.Login.LoginPresenterImpl;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class Login extends AppCompatActivity implements LoginView,View.OnClickListener {

    private ImageView btnAdd;
    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;
    private RelativeLayout ContentLogin;
    private EditText inputUser, inputPass;
    private ProgressBar progressBar;
    private LoginModel loginModel;
    private Sesion sesion;
    private Button Iniciar;
    final static String TAG = Login.class.getName();
    private static Activity actividad;

    public static void Finish(){
        actividad.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenterImpl(this, new LoginInteractorImpl());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.sesion = Sesion.getInstance(this);
        this.loginModel = this.sesion.readSesion();

        if (this.loginModel!=null){
            setContentView(R.layout.activity_splash);
            Log.e(TAG,"no null");
            RestModel restModel = new RestModel();
            restModel.setContext(getApplicationContext());
            restModel.setParameters(Charge.getInstance().genLogin(this.sesion.readSesion()));
            restModel.setLink(Link_Base+"user_login");
            this.loginPresenter.onGetLogin(restModel);

        }else{
            Log.e(TAG,"null");
            setContentView(R.layout.activity_login);
            this.Iniciar = findViewById(R.id.Iniciar);
            this.progressBar = findViewById(R.id.progressBar);
            this.inputUser = findViewById(R.id.inputUser);
            this.inputPass = findViewById(R.id.inputPass);
            this.ContentLogin = findViewById(R.id.ContentLogin);
            this.btnAdd = findViewById(R.id.btnRegister1);
            this.btnAdd.setOnClickListener(this);
            this.Iniciar.setOnClickListener(this);
            this.loginModel = new LoginModel();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister1:
                startActivity(new Intent(Login.this,Register.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.Iniciar:
                if (!IsEmpty(this.inputUser) && !IsEmpty(this.inputPass)){
                    TransitionManager.beginDelayedTransition(this.ContentLogin);
                    ShowHide(false);
                    this.loginPresenter.onGetLogin(this.ChargueData());
                }else{
                    Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onError(String error) {
        Log.e(TAG,error);
        if (this.ContentLogin!=null){
            TransitionManager.beginDelayedTransition(this.ContentLogin);
            ShowHide(true);
            Toasty.error(this, error, Toasty.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toHome(LoginModel loginModel) {
        this.sesion.saveSesion(this.loginModel);
        startActivity(new Intent(Login.this,MainActivity.class));
        finish();
    }

    public RestModel ChargueData(){
        this.loginModel.setUsername(this.inputUser.getText().toString());
        this.loginModel.setPassword(this.inputPass.getText().toString());
        RestModel restModel = new RestModel();
        restModel.setContext(getApplicationContext());
        restModel.setParameters(Charge.getInstance().genLogin(this.loginModel));
        restModel.setLink(Link_Base+"user_login");
        return restModel;
    }

    private void ShowHide(boolean action){
        this.inputUser.setEnabled(action);
        this.inputPass.setEnabled(action);
        if (action){
            this.progressBar.setVisibility(View.GONE);
        }else{
            this.progressBar.setVisibility(View.VISIBLE);
        }

    }

    private boolean IsEmpty(EditText editText){
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }
}