package com.arife.adnbikerider.mvp.m.interactor.Route;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRouteInteractor;

import org.json.JSONException;
import org.json.JSONObject;

public class UnionRouteInteractorImpl implements UnionRouteInteractor, ServerRestResponse {
    private OnFinishUnion onFinishUnion;

    @Override
    public void OnRegisterUnion(RestModel restModel, OnFinishUnion onFinishUnion) {
        this.onFinishUnion = onFinishUnion;
        restModel.setRestResponse(this);
        Rest.getInstance().sendPostRequestServer(restModel);
    }

    @Override
    public void toSuccessRest(String Response) {
        if (Response!=null){
            JSONObject jsonObject = null;
            Log.e("tagResponse", Response);
            try {
                jsonObject = new JSONObject(Response);

                if (jsonObject.getString("error").equals("0")){

                    this.onFinishUnion.OnSucces(jsonObject.getString("mensaje"));
                }else{
                    this.onFinishUnion.OnError(jsonObject.getString("mensaje"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            this.onFinishUnion.OnError("sin datos");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        this.onFinishUnion.OnError(Response);
    }
}
