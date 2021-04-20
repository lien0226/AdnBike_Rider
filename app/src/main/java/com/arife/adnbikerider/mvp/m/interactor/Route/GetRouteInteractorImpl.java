package com.arife.adnbikerider.mvp.m.interactor.Route;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvp.m.interfaz.GetRoute.GetRouteInteractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetRouteInteractorImpl implements GetRouteInteractor, ServerRestResponse  {

    private OnFinishGetRoute onFinishGetRoute;

    @Override
    public void OnGetRoute(RestModel restModel, OnFinishGetRoute onFinishGetRoute) {
        this.onFinishGetRoute = onFinishGetRoute;
        restModel.setRestResponse(this);
        Rest.getInstance().sendGetRequestToServer(restModel);
    }

    @Override
    public void toSuccessRest(String Response) {
        if (Response!=null){
            JSONObject jsonObject = null;
            Log.e("tagResponse", Response);
            try {
                jsonObject = new JSONObject(Response);
                JSONArray jsonArray = null;

                if (jsonObject.getString("error").equals("0")){
                    jsonArray = jsonObject.getJSONArray("data");
                    List<RouteModel> listRoute = new ArrayList<>();
                    Log.e("tagarray", jsonArray.toString());
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject job = jsonArray.getJSONObject(i);
                        RouteModel routeModel = new RouteModel();
                        routeModel.setId(job.getInt("IdRuta"));
                        routeModel.setIdGrupo(job.getInt("IdGrupo"));
                        routeModel.setNameRuta(job.getString("NombreRuta"));
                        routeModel.setDescRuta(job.getString("DescripcionRuta"));
                        routeModel.setEstado(job.getString("Estado"));
                        listRoute.add(routeModel);
                    }
                    this.onFinishGetRoute.OnSucces(listRoute);
                }else{
                    this.onFinishGetRoute.OnError(jsonObject.getString("mensaje"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Log.e("tagResponse", Response);
        }
    }

    @Override
    public void toFailedRest(String Response) {
        this.onFinishGetRoute.OnError(Response);
    }
}