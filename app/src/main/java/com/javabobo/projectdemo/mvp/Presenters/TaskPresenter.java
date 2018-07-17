package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.ParseJSON;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.TaskContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class TaskPresenter extends BasePresenter<TaskContract.View> implements TaskContract.Presenter {

    public TaskPresenter(TaskContract.View view) {
        this.view = view;
    }


    @Override
    public void getAllMyTasks(Context context) {
        InteratorJSON.getUniqueInstance().queryJSON(context, CONST.URL_GET_TASKS + "?id_user=" +
                SessionManager.getInstance(context).getCurrentUser().getIdUser(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("state")== 2){
                        view.error();
                        return;
                    }
                    LinkedList<Task> tasks = ParseJSON.readAllTask(response);
                    view.bringTaskOk(tasks);

                } catch (JSONException e) {
                    e.printStackTrace();
                    view.error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.error();
            }
        });
    }

    @Override
    public void updateTask(Context context, int idTask,int finish) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_user", SessionManager.getInstance(context).getCurrentUser().getIdUser());
            jsonObject.put("finish",finish);
            jsonObject.put("id_task",idTask);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        InteratorJSON.getUniqueInstance().postJSON(context, CONST.URL_UPDATE_TASK, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("state") != 1 ){
                        view.error();
                        return;
                    }
                    view.updateTaskOk();
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
