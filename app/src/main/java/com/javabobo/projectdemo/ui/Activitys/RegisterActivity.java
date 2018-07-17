package com.javabobo.projectdemo.ui.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.javabobo.projectdemo.AppContoller;
import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.ImageUtils;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.Utils.imagepick.ImagePickHelper;
import com.javabobo.projectdemo.Utils.imagepick.OnImagePickedListener;
import com.javabobo.projectdemo.mvp.Contracts.RegisterContract;
import com.javabobo.projectdemo.mvp.Presenters.RegisterPresenter;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterContract.View, OnImagePickedListener {
    private RegisterContract.Presenter presenter;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.add_picture)
    TextView addPicture;

    private Bitmap yourSelectedImage;

    @BindView(R.id.username_input)
    EditText username;
    @BindView(R.id.email_input)
    EditText email;
    @BindView(R.id.password_input)
    EditText pass_1;
    @BindView(R.id.confirm_password_input)
    EditText pass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();

    }


    private void initPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @OnClick(R.id.button_register)
    public void onClickRegister(View v) {

        if (username.getText().toString().equals("")) {
            username.setError("Username is mandatory");
            return;
        }
        if (email.getText().toString().equals("")) {
            email.setError("Email is mandatory");
            return;
        }
        if (pass_1.getText().toString().equals("")) {
            pass_1.setError("Pass is mandatory");
            return;
        }
        if (!pass_1.getText().toString().equals(pass2.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Error in the password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (yourSelectedImage == null) {
            Toast.makeText(RegisterActivity.this, "The image is mandatory", Toast.LENGTH_SHORT).show();
            return;
        }
        startProgressBar();
        presenter.registerUser(RegisterActivity.this, createUser());
    }

    private User createUser() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setImagenAux(yourSelectedImage);
        user.setPass(pass_1.getText().toString());
        user.setUserName(username.getText().toString());
        return user;
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_register;
    }

    @Override
    public String getTitleActionBar() {
        return "Register new user";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }

    @Override
    public void responseUserOk(User user) {
        stopProgress();
        SessionManager.getInstance(this).createLoginSession(user);
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.container_add_picture)
    public void onClickAddPictured(View v) {
        new ImagePickHelper().pickAnImage(RegisterActivity.this, 5);
    }

    @Override
    public void errorToRegisterUser() {
        stopProgress();
        Toast.makeText(getApplicationContext(), "Error to register", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImagePicked(int requestCode, File file) {
        Glide.with(AppContoller.getController())
                .load(file)
                .centerCrop()

                .listener(new RequestListener<File, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        addPicture.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(img);


        yourSelectedImage = BitmapFactory.decodeFile(file.getPath());
        try {
            yourSelectedImage = ImageUtils.checkAngle(file.getPath(), yourSelectedImage);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onImagePickError(int requestCode, Exception e) {

    }

    @Override
    public void onImagePickClosed(int requestCode) {

    }
}
