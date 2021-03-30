package com.arife.adnbikerider.mvp.m.interactor.Group;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupInteractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupInteractorImpl implements GroupInteractor, ServerRestResponse {
    private OnFinishGroup onFinishGroup;

    @Override
    public void onRegisterGroup(RestModel restModel, OnFinishGroup onFinishGroup) {
        this.onFinishGroup = onFinishGroup;
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

                    this.onFinishGroup.OnSucces(jsonObject.getString("mensaje"));
                }else{
                    this.onFinishGroup.OnError(jsonObject.getString("mensaje"));
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
        this.onFinishGroup.OnError(Response);
    }
}
