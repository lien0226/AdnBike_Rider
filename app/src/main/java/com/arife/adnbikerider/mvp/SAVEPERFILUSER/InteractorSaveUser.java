package com.arife.adnbikerider.mvp.SAVEPERFILUSER;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.RestModel;

import org.json.JSONException;
import org.json.JSONObject;

public class InteractorSaveUser implements SaveUserInteractor, ServerRestResponse {
    OnFinishSaveUser onFinishSaveUser;
    @Override
    public void toSuccessRest(String Response) {
        if (!Response.isEmpty()){
            try {
                JSONObject jsonObject = new JSONObject(Response);

                if (jsonObject.getString("error").equals("0")){
                    onFinishSaveUser.OnSuccesSaveUser("mensaje");
                }else{
                    onFinishSaveUser.OnErrorUser("error al guardar");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            onFinishSaveUser.OnErrorUser("Response null");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        onFinishSaveUser.OnErrorUser(Response);
    }

    @Override
    public void OnSaveUser(RestModel restModel, OnFinishSaveUser onFinishSaveUser) {
        this.onFinishSaveUser = onFinishSaveUser;
        restModel.setRestResponse(this);
        Rest.getInstance().sendPostRequestServer(restModel);
    }
}
