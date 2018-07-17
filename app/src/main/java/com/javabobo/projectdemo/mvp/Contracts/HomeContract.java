package com.javabobo.projectdemo.mvp.Contracts;

import android.content.Context;

import com.javabobo.projectdemo.Models.CurrentLocation;
import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.Models.New;
import com.javabobo.projectdemo.Models.Weather;
import com.javabobo.projectdemo.mvp.Shared.BaseContract;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class HomeContract {

    public interface View extends BaseContract.View {
        void currentLocationOk(CurrentLocation currentLocation);
        void responseWeatherOk(Weather weather);
        void msjWeatherError();
        void msjNewOk(New.Item item);
        void bringGaleryImgOk(GaleryImage galeryImage);
        void distributionList(LinkedList<Integer> distribution);
        void distributionError();
    }


    public interface Presenter {
        void getLocation(Context context);
        void getWeather(Context context, CurrentLocation currentLocation);
        void getFirtsNew(Context context);
        void getSport(Context context);
        void getFirstImage(Context context);
        void getClothes(Context context);

    }

}
