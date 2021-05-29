package com.arife.adnbikerider.mvp.m.interactor.User;

import android.text.TextUtils;
import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UserModel;
import com.arife.adnbikerider.mvp.m.interfaz.User.UserInteractor;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInteractorImpl implements UserInteractor, ServerRestResponse {
    private OnFinishUser onFinishUser;

    @Override
    public void OnRegisterUser(RestModel restModel, OnFinishUser onFinishUser) {
        restModel.setRestResponse(this);
        this.onFinishUser = onFinishUser;
        if (restModel.getParameters().get("opcion").equals("N")){
            if (TextUtils.isEmpty(restModel.getParameters().get("id").toString())){
                this.onFinishUser.OnError("sin id regitro");
                return;
            }else if(TextUtils.isEmpty(restModel.getParameters().get("email").toString())){
                this.onFinishUser.OnError("ingrese su correo");
                return;
            }else if(TextUtils.isEmpty(restModel.getParameters().get("login").toString())){
                this.onFinishUser.OnError("ingrese un nombre de usuario");
                return;
            }else if(TextUtils.isEmpty(restModel.getParameters().get("password").toString())){
                this.onFinishUser.OnError("ingrese una contraseña");
                return;
            }
        }else if(restModel.getParameters().get("opcion").equals("V")){
            if (TextUtils.isEmpty(restModel.getParameters().get("email").toString())){
                this.onFinishUser.OnError("ingrese un correo");
                return;
            }else if(TextUtils.isEmpty(restModel.getParameters().get("verificacion").toString())){
                this.onFinishUser.OnError("ingrese el codigo de verificacion");
                return;
            }
        }
        Rest.getInstance().sendPostRequestServer(restModel);
    }

    @Override
    public void toSuccessRest(String Response) {
        if (Response!=null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(Response);
                if (jsonObject.getString("error").equals("0")){
                    if(jsonObject.getString("opcion").equals("N")){
                        UserModel usuarioModel = new UserModel();
                        usuarioModel.setId(jsonObject.getInt("Id"));
                        usuarioModel.setVerificacion(jsonObject.getString("codverify"));
                        this.onFinishUser.OnSucces(usuarioModel);
                    }else{
                        UserModel usuarioModel = new UserModel();
                        usuarioModel.setId(jsonObject.getInt("Id"));
                        this.onFinishUser.OnSucces(usuarioModel);
                    }
                }else{
                    this.onFinishUser.OnError(jsonObject.getString("mensaje"));
                }
            } catch (JSONException e) {
                Log.e("response", e.getMessage());
                this.onFinishUser.OnError(e.getMessage());
            }

        }else{
            this.onFinishUser.OnError("Response null");
        }
        //chamo igual crashea conectare para ver el log
    }

    @Override
    public void toFailedRest(String Response) {
        if (Response!=null){
            Log.e("response", "failed rest");
            this.onFinishUser.OnError(Response);
        }
    }


}
