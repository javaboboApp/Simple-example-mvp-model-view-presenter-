package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;
import android.util.Log;

import com.javabobo.projectdemo.Models.Play;
import com.javabobo.projectdemo.mvp.Contracts.SportContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorCVS;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by luis on 14/02/2018.
 */

public class SportPresenter extends BasePresenter<SportContract.View> implements SportContract.Presenter {

    public SportPresenter(SportContract.View view) {
        this.view = view;
    }


    @Override
    public void getSport(Context context) {
        String url = "http://www.football-data.co.uk/mmz4281/1718/I1.csv";
        InteratorCVS.getUniqueInstance().queryCVS(url, new InteratorCVS.Listener() {
            @Override
            public void onSucess(List<Play> listPlays) {
                Log.v("response", "" + listPlays.size());
                view.responseSportOk(new LinkedList<Play>(listPlays));
            }

            @Override
            public void onFailured() {
                Log.v("response", "error to bring plays");
            }
        });
    }
}
