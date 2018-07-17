package com.javabobo.projectdemo.ui.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.mvp.Contracts.TaskContract;
import com.javabobo.projectdemo.mvp.Presenters.TaskPresenter;
import com.javabobo.projectdemo.ui.Adapters.ItemAdapterTasks;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

public class TasksActivity extends BaseActivity implements TaskContract.View {
    @BindView(R.id.list_task)
    ListView listTask;
    private ItemAdapterTasks itemAdapterTasks;
    private TaskContract.Presenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        bringAllMyTasks();

    }

    private void bringAllMyTasks() {

        startProgressBar();
        presenter.getAllMyTasks(TasksActivity.this);
    }

    private void initPresenter() {
          presenter = new TaskPresenter(this);
    }

    @OnClick({R.id.add_task})
    public void addTask(View v){
        Intent intent = new Intent(this, AddNewTaskActivity.class);
        startActivityForResult(intent, 55);
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_tasks;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 55 && resultCode == RESULT_OK && data != null ){
            Task task = (Task) data.getExtras().getSerializable("task");
            if(itemAdapterTasks == null){
                itemAdapterTasks = new ItemAdapterTasks(TasksActivity.this, new ArrayList<Task>( ), new ItemAdapterTasks.Listener() {
                    @Override
                    public void onCheckboxChange(int id,boolean b) {
                       startProgressBar();
                        presenter.updateTask(TasksActivity.this, id,b ? 1: 0);

                    }
                });
                listTask.setAdapter(itemAdapterTasks);
            }
            itemAdapterTasks.addTask(task);
        }
    }

    @Override
    public String getTitleActionBar() {
        return "Tasks";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }

    @Override
    public void updateTaskOk() {
        stopProgress();
       // Toast.makeText(TasksActivity.this, "The task has been updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bringTaskOk(LinkedList<Task> tasks) {
        stopProgress();
        itemAdapterTasks = new ItemAdapterTasks(TasksActivity.this, new ArrayList<>( tasks), new ItemAdapterTasks.Listener() {
            @Override
            public void onCheckboxChange(int id,boolean b) {

                presenter.updateTask(TasksActivity.this, id,b ? 1: 0);

            }
        });
        listTask.setAdapter(itemAdapterTasks);

    }

    @Override
    public void error() {
        stopProgress();

        //Toast.makeText(TasksActivity.this, "Error to update the task", Toast.LENGTH_SHORT).show();
    }
}
