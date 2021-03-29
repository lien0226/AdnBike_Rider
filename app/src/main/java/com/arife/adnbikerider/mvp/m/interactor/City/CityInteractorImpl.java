package com.arife.adnbikerider.mvp.m.interactor.City;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityInteractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityInteractorImpl implements CityInteractor, ServerRestResponse {
    private OnFinishCity onFinishCity;
    @Override
    public void OnGetCity(RestModel restModel, OnFinishCity onFinishCity) {
        this.onFinishCity = onFinishCity;
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
                    ArrayList<CityModel> listCity = new ArrayList<>();
                    Log.e("tagarray", jsonArray.toString());
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject job = jsonArray.getJSONObject(i);
                        CityModel cityModel = new CityModel();
                        cityModel.setCodigo(job.getString("Codigo"));
                        cityModel.setFormato(job.getString("formato1"));
                        listCity.add(cityModel);
                    }
                    this.onFinishCity.OnSucces(listCity);
                }else{
                    this.onFinishCity.OnError(jsonObject.getString("mensaje"));
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
        this.onFinishCity.OnError(Response);
    }
}
