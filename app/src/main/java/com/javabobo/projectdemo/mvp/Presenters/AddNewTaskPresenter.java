package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.AddNewTaskContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 14/02/2018.
 */

public class AddNewTaskPresenter extends BasePresenter<AddNewTaskContract.View> implements AddNewTaskContract.Presenter {

    public AddNewTaskPresenter(AddNewTaskContract.View view) {
        this.view = view;
    }




    @Override
    public void addNewTask(Context context, final Task task) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_user", SessionManager.getInstance(context).getCurrentUser().getIdUser());
            jsonObject.put("title", task.getTitle());
            jsonObject.put("finish",task.isFinish());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        InteratorJSON.getUniqueInstance().postJSON(context, CONST.URL_INSERT_TASK, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("state") != 1 ){
                        view.error();
                        return;
                    }
                    task.setId(response.getInt("id"));
                    view.responseAddTaskOk(task);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.error();
                }
                Log.v("response", "response ok");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("response", "error in the request");
                view.error();
            }
        });
    }
}
