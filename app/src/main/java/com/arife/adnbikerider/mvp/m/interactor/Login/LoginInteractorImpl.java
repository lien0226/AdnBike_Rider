package com.arife.adnbikerider.mvp.m.interactor.Login;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.LoginModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.Login.LoginInteractor;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginInteractorImpl implements LoginInteractor, ServerRestResponse {
    private OnFinishLogin onFinishLogin;
    @Override
    public void onLogin(RestModel restModel, OnFinishLogin onFinishLogin) {
        restModel.setRestResponse(this);
        if (TextUtils.isEmpty(restModel.getParameters().get("loggin").toString())){
            this.onFinishLogin.onError("Ingresa un usuario");
            return;
        }
        if (TextUtils.isEmpty(restModel.getParameters().get("password").toString())){
            this.onFinishLogin.onError("Ingresa un password");
            return;
        }
        this.onFinishLogin = onFinishLogin;
        Rest.getInstance().sendPostRequestServer(restModel);
    }

    @Override
    public void toSuccessRest(String Response) {
        if (Response!=null){
            JSONObject jsonArray = null;
            try {
                jsonArray = new JSONObject(Response);

                if (jsonArray.getString("error").equals("0")){
                    LoginModel loginModel = new LoginModel();
                    loginModel.setUsername(jsonArray.getString("login"));
                    loginModel.setResponse(jsonArray.getInt("error"));
                    loginModel.setName(jsonArray.getString("name"));
                    this.onFinishLogin.onSuccess(loginModel);
                }else{
                    this.onFinishLogin.onError(jsonArray.getString("mensaje"));
                }

            }catch (JSONException e){
                this.onFinishLogin.onError(e.getMessage());
                //Log.e(TAG, e.getMessage());
            }
        }else{
            this.onFinishLogin.onError("Response null");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        if (Response!=null){
            this.onFinishLogin.onError(Response);
        }
    }
}
