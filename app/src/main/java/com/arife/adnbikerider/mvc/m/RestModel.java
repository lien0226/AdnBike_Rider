package com.arife.adnbikerider.mvc.m;

import android.content.Context;

import com.arife.adnbikerider.mvc.c.ServerRestResponse;

import org.json.JSONObject;

import java.util.Map;

public class RestModel {

    Context context;
    Map parameters;
    String link;
    ServerRestResponse restResponse;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ServerRestResponse getRestResponse() {
        return restResponse;
    }

    public void setRestResponse(ServerRestResponse restResponse) {
        this.restResponse = restResponse;
    }

}
