package com.arife.adnbikerider.mvp.m.interactor.Ubicacion;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UserModel;
import com.arife.adnbikerider.mvp.m.interfaz.Ubicacion.UbicacionInteractor;

import org.json.JSONException;
import org.json.JSONObject;

public class UbicacionInteractorImpl implements UbicacionInteractor, ServerRestResponse {
    private OnFinishUbicacion onFinishUbicacion;
    @Override
    public void OnRegisterUbicacion(RestModel restModel, OnFinishUbicacion onFinishUbicacion) {
        restModel.setRestResponse(this);
        this.onFinishUbicacion = onFinishUbicacion;
        Rest.getInstance().sendPostRequestServer(restModel);
    }
    @Override
    public void toSuccessRest(String Response) {
        if (Response!=null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(Response);
                if (jsonObject.getString("error").equals("0")){
                    this.onFinishUbicacion.OnSucces("Ruta Subida");
                }else{
                    this.onFinishUbicacion.OnError(jsonObject.getString("mensaje"));
                }
            } catch (JSONException e) {
                Log.e("response", e.getMessage());
                this.onFinishUbicacion.OnError(e.getMessage());
            }

        }else{
            this.onFinishUbicacion.OnError("Response null");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        this.onFinishUbicacion.OnError(Response);
    }
}
