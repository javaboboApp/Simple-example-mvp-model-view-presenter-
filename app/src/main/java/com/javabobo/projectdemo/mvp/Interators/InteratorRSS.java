package com.javabobo.projectdemo.mvp.Interators;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.javabobo.projectdemo.Models.New;
import com.javabobo.projectdemo.Network.SimpleXmlRequest;
import com.javabobo.projectdemo.Network.VolleySingleton;

/**
 * Created by luis on 14/02/2018.
 */

public class InteratorRSS {

    private static InteratorRSS interatorRSS = new InteratorRSS();

    /* Contructor  que obliga a pasaer el Content contentResolver */
    private  InteratorRSS() {

    }

    public static InteratorRSS getUniqueInstance(){
        return  interatorRSS;
    }



    public void queryRSS(Context context, String uri ,
                          Response.Listener<New> jsonListener,
                          Response.ErrorListener errorListener) {


        SimpleXmlRequest<New> simpleRequest = new SimpleXmlRequest<New>(Request.Method.GET, uri, New.class,
               jsonListener,
                errorListener
        );

        VolleySingleton.getInstance(context).addToRequestQueue(simpleRequest);
    }

}
