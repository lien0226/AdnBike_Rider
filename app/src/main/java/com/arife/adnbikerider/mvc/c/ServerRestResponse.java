package com.arife.adnbikerider.mvc.c;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ServerRestResponse {
    void toSuccessRest(String Response);
    void toFailedRest(String Response);
}
