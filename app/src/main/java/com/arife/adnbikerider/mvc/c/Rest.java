package com.arife.adnbikerider.mvc.c;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arife.adnbikerider.mvc.m.RestModel;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Rest {

    private static Rest instance;
    private Rest(){};

    public static Rest getInstance(){
        if (instance == null){
            instance = new Rest();
        }
        return instance;
    }
    final static String TAG = Rest.class.getName();
    public void sendPostRequestServer(RestModel restModel){
        RequestQueue requestQueue = Volley.newRequestQueue(restModel.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, restModel.getLink(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)){
                            Log.e(TAG,response);
                            restModel.getRestResponse().toSuccessRest(response);
                        }else{
                            Log.e(TAG,response);
                            restModel.getRestResponse().toFailedRest("VACIO");
                        }

                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restModel.getRestResponse().toFailedRest(error.getMessage());
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return restModel.getParameters();
                    }
                };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void sendGetRequestToServer(RestModel restModel){
        RequestQueue requestQueue = Volley.newRequestQueue(restModel.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restModel.getLink(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!TextUtils.isEmpty(response)){
                            Log.e(TAG,response);
                            restModel.getRestResponse().toSuccessRest(response);
                        }else{
                            Log.e(TAG,response);
                            restModel.getRestResponse().toFailedRest("VACIO");
                        }

                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                restModel.getRestResponse().toFailedRest(error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return restModel.getParameters();
            }
            /*@Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject js = new JSONObject();
                try {
                    js.put("opcion", "P");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return js.toString().getBytes();
            }*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
