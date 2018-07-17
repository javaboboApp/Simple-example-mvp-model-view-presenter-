package com.javabobo.projectdemo.mvp.Contracts;

import android.content.Context;

import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.mvp.Shared.BaseContract;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class TaskContract {

    public interface View extends BaseContract.View {
        void updateTaskOk();
        void bringTaskOk(LinkedList<Task> tasks);
        void error();
    }


    public interface Presenter {
        void getAllMyTasks(Context context);
        void updateTask(Context context, int idTask,int finish);

    }

}
