package com.arife.adnbikerider.mvp.m.interactor.Group;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interfaz.ListGroups.ListGroupInteractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListGroupInteractorImpl implements ListGroupInteractor, ServerRestResponse {
    private OnFinishListGroup onFinishListGroup;

    @Override
    public void onGetGroup(RestModel restModel, OnFinishListGroup onFinishListGroup) {
        this.onFinishListGroup = onFinishListGroup;
        restModel.setRestResponse(this);
        Rest.getInstance().sendGetRequestToServer(restModel);
    }

    @Override
    public void toSuccessRest(String Response) {
        if (Response!=null){
            JSONObject jsonObject = null;
            //Log.e("tagResponse", Response);
            try {
                jsonObject = new JSONObject(Response);
                JSONArray jsonArray = null;

                if (jsonObject.getString("error").equals("0")){
                    jsonArray = jsonObject.getJSONArray("data");
                    List<GroupModel> listGroup = new ArrayList<>();
                    //Log.e("tagarray", jsonArray.toString());
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject job = jsonArray.getJSONObject(i);
                        GroupModel groupModel = new GroupModel();
                        groupModel.setId(job.getInt("IdGrupo"));
                        groupModel.setGroupName(job.getString("NombreGrupo"));
                        groupModel.setGroupDescription(job.getString("DescripcionGrupo"));
                        listGroup.add(groupModel);
                    }
                    this.onFinishListGroup.OnSucces(listGroup);
                }else{
                    this.onFinishListGroup.OnError(jsonObject.getString("mensaje"));
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
        this.onFinishListGroup.OnError(Response);
    }
}
