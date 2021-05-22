package com.arife.adnbikerider.mvp.GETROUTEPERSON;

import android.util.Log;

import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.UbicacionModel;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InteractorRoutePerson implements RoutePersonInteractor, ServerRestResponse {

    private OnFinishRoutePerson onFinishRoutePerson;

    @Override
    public void OnRoutePerson(RestModel restModel, OnFinishRoutePerson onFinishRoutePerson) {

        this.onFinishRoutePerson = onFinishRoutePerson;

        restModel.setRestResponse(this);
        Rest.getInstance().sendGetRequestToServer(restModel);

    }

    @Override
    public void toSuccessRest(String Response) {
        if (!Response.isEmpty()){

            try {

                JSONObject jsonObject = new JSONObject(Response);

                if (jsonObject.getString("error").equals("0")){

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    List<UbicacionModel> ubicacionModels = new ArrayList<>();

                    for (int i = 0 ; i < jsonArray.length() ; i++){

                        JSONObject position = jsonArray.getJSONObject(i);

                        UbicacionModel u = new UbicacionModel();
                        Log.e(position.getDouble("Longitud")+"",position.getDouble("Latitud")+"");
                        u.setUbicacion(new LatLng(position.getDouble("Longitud"),position.getDouble("Latitud")));
                        u.setTime(position.getLong("Dtime"));

                        ubicacionModels.add(u);

                    }

                    this.onFinishRoutePerson.OnSucessRoutePerson(ubicacionModels);

                }else {
                    this.onFinishRoutePerson.OnError(jsonObject.getString("mensaje"));
                }

            } catch (JSONException e) {
                this.onFinishRoutePerson.OnError(e.getMessage());
            }

        }else{
            this.onFinishRoutePerson.OnError("Response vacio");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        this.onFinishRoutePerson.OnError(Response);
    }


}
