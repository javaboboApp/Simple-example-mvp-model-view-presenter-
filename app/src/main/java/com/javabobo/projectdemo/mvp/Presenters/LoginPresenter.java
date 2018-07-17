package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.ParseJSON;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.LoginContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 14/02/2018.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }


    @Override
    public void loginUser(final Context context, User user) {
        InteratorJSON.getUniqueInstance().queryJSON(context, CONST.URL_GET_USER + "?username=" + user.getNameUser() + "&pass=" + user.getPass(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("state")== 2){
                        view.errorToLoginUser();
                        return;
                    }
                    User u = ParseJSON.readUser(response);
                    SessionManager.getInstance(context).createLoginSession(u);
                    view.responseUserOk(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.errorToLoginUser();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.errorToLoginUser();
            }
        });
    }
}
