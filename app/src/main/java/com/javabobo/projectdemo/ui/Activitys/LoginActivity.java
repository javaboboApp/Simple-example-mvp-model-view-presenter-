package com.javabobo.projectdemo.ui.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.LoginContract;
import com.javabobo.projectdemo.mvp.Presenters.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.username_input)
    EditText username;
    @BindView(R.id.password_input)
    EditText pass;

    private LoginContract.Presenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SessionManager.getInstance(LoginActivity.this).isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        initPresenter();

    }

    private void initPresenter() {
        presenter = new LoginPresenter(this);
    }

    @OnClick(R.id.sign_up)
    public void onClickSignUp(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_login)
    public void onClickLogin(View v) {
        if (username.getText().length() == 0) {
            username.setError("Username is mandatory");
            return;
        }
        if (pass.getText().length() == 0) {
            pass.setError("Username is mandatory");
            return;
        }
        startProgressBar();
        presenter.loginUser(LoginActivity.this, createUser());
    }


    private User createUser() {
        User u = new User();
        u.setUserName(username.getText().toString());
        u.setPass(pass.getText().toString());
        return u;
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_login;
    }

    @Override
    public String getTitleActionBar() {
        return "Login";
    }

    @Override
    public boolean haveArrowBack() {
        return false;
    }

    @Override
    public void responseUserOk(User user) {
        stopProgress();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void errorToLoginUser() {
        stopProgress();
        Toast.makeText(LoginActivity.this, "Error to login", Toast.LENGTH_SHORT).show();
    }
}
