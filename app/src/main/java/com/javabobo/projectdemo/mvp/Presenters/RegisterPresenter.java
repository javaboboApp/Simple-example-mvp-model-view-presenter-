package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.ImageUtils;
import com.javabobo.projectdemo.Utils.ParseJSON;
import com.javabobo.projectdemo.mvp.Contracts.RegisterContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 14/02/2018.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }


    @Override
    public void registerUser(Context context, User user) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user.getNameUser());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("pass", user.getPass());
            jsonObject.put("img", ImageUtils.getBase64Image(user.getImagenAux()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        InteratorJSON.getUniqueInstance().postJSON(context, CONST.URL_INSERT_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("state") != 1 ){
                        view.errorToRegisterUser();
                        return;
                    }
                    User user1 = ParseJSON.readUser(response);
                    view.responseUserOk(user1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.errorToRegisterUser();
                }
                Log.v("response", "response ok");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("response", "error in the request");
                view.errorToRegisterUser();
            }
        });
    }
}
