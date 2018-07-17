package com.javabobo.projectdemo.mvp.Contracts;

import android.content.Context;

import com.javabobo.projectdemo.Models.Play;
import com.javabobo.projectdemo.mvp.Shared.BaseContract;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class SportContract {

    public interface View extends BaseContract.View {
        void responseSportOk(LinkedList<Play> sports);
    }


    public interface Presenter {
        void getSport(Context context);

    }

}
