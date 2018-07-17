package com.javabobo.projectdemo.mvp.Contracts;

import android.content.Context;

import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.mvp.Shared.BaseContract;

/**
 * Created by luis on 14/02/2018.
 */

public class AddNewTaskContract {

    public interface View extends BaseContract.View {
        void responseAddTaskOk(Task task);
        void error();
    }


    public interface Presenter {
        void addNewTask(Context context, Task task);

    }

}
