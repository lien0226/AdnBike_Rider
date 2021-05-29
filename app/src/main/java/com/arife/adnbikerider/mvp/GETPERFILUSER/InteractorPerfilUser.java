package com.arife.adnbikerider.mvp.GETPERFILUSER;

import android.util.Log;

import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.c.ServerRestResponse;
import com.arife.adnbikerider.mvc.m.PerfilModel;
import com.arife.adnbikerider.mvc.m.RestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InteractorPerfilUser implements PerfilUserInteractor, ServerRestResponse {
    private OnFinishPerfil onFinishPerfil;

    @Override
    public void OnPerfilUser(RestModel restModel, OnFinishPerfil onFinishPerfil) {
        this.onFinishPerfil = onFinishPerfil;
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
                        Log.e("TAG",jsonArray.length()+"");
                        JSONObject jsonChild = jsonArray.getJSONObject(0);

                        PerfilModel p = new PerfilModel();
                        p.setUsuario(jsonChild.getString("Usuario"));
                        p.setNombres(jsonChild.getString("Nombres"));
                        p.setApellidos(jsonChild.getString("Apellidos"));
                        p.setCelular(jsonChild.getString("Celular"));
                        p.setDireccion(jsonChild.getString("Direccion"));
                        p.setImagen(jsonChild.getString("Image"));

                        this.onFinishPerfil.OnSuccesPerfil(p);

                }else {
                    this.onFinishPerfil.OnErrorPerfil(jsonObject.getString("mensaje"));
                }

            } catch (JSONException e) {
                Log.e("ITPU", e.toString());
                this.onFinishPerfil.OnErrorPerfil(e.getMessage());
            }

        }else{
            this.onFinishPerfil.OnErrorPerfil("Response vacio");
        }
    }

    @Override
    public void toFailedRest(String Response) {
        this.onFinishPerfil.OnErrorPerfil(Response);
    }
}
