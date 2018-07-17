package com.javabobo.projectdemo.ui.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.AddNewTaskContract;
import com.javabobo.projectdemo.mvp.Presenters.AddNewTaskPresenter;

import butterknife.BindView;

public class AddNewTaskActivity extends BaseActivity implements AddNewTaskContract.View {
    private AddNewTaskContract.Presenter presenter;
    @BindView(R.id.task_input)
    EditText taskInput;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new AddNewTaskPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (taskInput.getText().toString().equals("")) {
            taskInput.setError("This content can not be empty");
            return super.onOptionsItemSelected(item);
        }
        startProgressBar();
        presenter.addNewTask(AddNewTaskActivity.this, createTask());
        return super.onOptionsItemSelected(item);
    }

    private Task createTask() {
        Task task = new Task();
        task.setIdUser(SessionManager.getInstance(this).getCurrentUser().getIdUser());
        task.setTitle(taskInput.getText().toString());
        return task;
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_add_new_task;
    }

    @Override
    public String getTitleActionBar() {
        return "Add new task";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }

    @Override
    public void responseAddTaskOk(Task task) {
        stopProgress();
        Intent intent = new Intent();
        intent.putExtra("task", task);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void error() {
        stopProgress();
    }
}
