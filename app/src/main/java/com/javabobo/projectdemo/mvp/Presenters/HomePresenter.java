package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.javabobo.projectdemo.Models.CurrentLocation;
import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.Models.New;
import com.javabobo.projectdemo.Models.Weather;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.General;
import com.javabobo.projectdemo.Utils.ParseJSON;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.HomeContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;
import com.javabobo.projectdemo.mvp.Interators.InteratorRSS;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getLocation(Context context) {
        General.getLocation(context, new General.Listener() {
            @Override
            public void onSucess(Location location) {
                view.currentLocationOk(new CurrentLocation(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onFailured() {

            }
        });

    }

    @Override
    public void getWeather(Context context, CurrentLocation currentLocation) {

        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + currentLocation.getLat() + "&lon=" +
                currentLocation.getLon() + "&appid=d0a10211ea3d36b0a6423a104782130e";

        InteratorJSON.getUniqueInstance().queryJSON(context, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("response", response.toString());
                try {
                    Weather weather = ParseJSON.readWeather(response);
                    view.responseWeatherOk(weather);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("response", error.toString());
            }
        });


    }

    @Override
    public void getFirtsNew(Context context) {
        String url = "http://feeds.bbci.co.uk/news/rss.xml";

        InteratorRSS.getUniqueInstance().queryRSS(context, url, new Response.Listener<New>() {
            @Override
            public void onResponse(New response) {
                Log.v("response", response.toString());
                New.Item item = response.getChannel().getItems().get(0);
                view.msjNewOk(item);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("response", error.toString());
            }
        });

    }

    @Override
    public void getSport(Context context) {

    }

    @Override
    public void getFirstImage(Context context) {
        InteratorJSON.getUniqueInstance().queryJSON(context, CONST.URL_GET_IMG_GALERY + "?id_dir=" +
                SessionManager.getInstance(context).getCurrentUser().getPathDir(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Error or list empry
                    if (response.getInt("state") != 2 && response.getInt("state") != 3) {


                        LinkedList<GaleryImage> galeryImages = ParseJSON.readImages(response);
                        view.bringGaleryImgOk(galeryImages.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    @Override
    public void getClothes(Context context) {
        String url = "https://therapy-box.co.uk/hackathon/clothing-api.php?username=" + SessionManager.getInstance(context).getCurrentUser().getNameUser();

        InteratorJSON.getUniqueInstance().queryJSON(context, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LinkedList<Integer> distribution = ParseJSON.readClothes(response);
                    view.distributionList(distribution);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.distributionError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.distributionError();
            }
        });

    }
}
