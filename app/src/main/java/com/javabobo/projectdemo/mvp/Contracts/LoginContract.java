package com.javabobo.projectdemo.mvp.Contracts;

import android.content.Context;

import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.mvp.Shared.BaseContract;

/**
 * Created by luis on 14/02/2018.
 */

public class LoginContract {

    public interface View extends BaseContract.View {
        void responseUserOk(User user);
        void errorToLoginUser();
    }


    public interface Presenter {
        void loginUser(Context context, User user);

    }

}
