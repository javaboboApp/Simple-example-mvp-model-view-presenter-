package com.javabobo.projectdemo.mvp.Interators;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.javabobo.projectdemo.Network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luis on 17/02/2016.
 */
public  class InteratorJSON {

    private static InteratorJSON interatorJSON = new InteratorJSON();

    private Map<String, String> headers;
    private String bodyContentType;
    protected Uri uriContent;



    public InteratorJSON() {


        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Accept", "application/json");

        bodyContentType = "application/json; charset=utf-8";
    }


    public static InteratorJSON getUniqueInstance(){
        return  interatorJSON;
    }




    public void queryJSON(Context context, String uri ,
                           Response.Listener<JSONObject> jsonListener,
                          Response.ErrorListener errorListener) {

        //  GET

        JsonObjectRequest request;

        request = new JsonObjectRequest(
                uri,null,
                jsonListener,
                errorListener
        );

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    public void postJSON(Context context, String uri ,JSONObject nodyRequest,
                          Response.Listener<JSONObject> jsonListener,
                          Response.ErrorListener errorListener) {

        //  POST

        JsonObjectRequest request;

        request = new JsonObjectRequest(
                Request.Method.POST,
                uri,
                nodyRequest,
                jsonListener,
                errorListener
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return bodyContentType + getParamsEncoding();
            }
        };


        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }






}
