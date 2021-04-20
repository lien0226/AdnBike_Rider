package com.arife.adnbikerider.mvp.m.interactor.Route;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.Route.RouteInteractor;

import org.json.JSONException;
import org.json.JSONObject;

public class RouteInteractorImpl implements RouteInteractor, ServerRestResponse {
    private OnFinishRoute onFinishRoute;
    @Override
    public void OnRegisterRoute(RestModel restModel, OnFinishRoute onFinishRoute) {
        this.onFinishRoute = onFinishRoute;
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

                    this.onFinishRoute.OnSucces(jsonObject.getString("mensaje"));
                }else{
                    this.onFinishRoute.OnError(jsonObject.getString("mensaje"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            this.onFinishRoute.OnError("sin datos");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        this.onFinishRoute.OnError(Response);
    }
}
