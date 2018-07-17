package com.javabobo.projectdemo.mvp.Presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.ImageUtils;
import com.javabobo.projectdemo.Utils.ParseJSON;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.PhotosContract;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;
import com.javabobo.projectdemo.mvp.Shared.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class PhotosPresenter extends BasePresenter<PhotosContract.View> implements PhotosContract.Presenter {

    public PhotosPresenter(PhotosContract.View view) {
        this.view = view;
    }


    @Override
    public void bringMyImagesFromGalery(Context context) {
        InteratorJSON.getUniqueInstance().queryJSON(context, CONST.URL_GET_IMG_GALERY + "?id_dir=" +
                SessionManager.getInstance(context).getCurrentUser().getPathDir(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("state") == 2) {
                        view.error();
                        return;
                    } else if (response.getInt("state") != 3) {
                        //list is empty


                        LinkedList<GaleryImage> galeryImages = ParseJSON.readImages(response);
                        view.responseOk(galeryImages);

                    }else view.galeryEmpty();
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.error();
            }
        });
    }

    @Override
    public void insertImagePicture(Context context, final String imageName, Bitmap image) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("img_name", imageName);
            jsonObject.put("id_dir", SessionManager.getInstance(context).getCurrentUser().getPathDir());

            jsonObject.put("img", ImageUtils.getBase64Image(image));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        InteratorJSON.getUniqueInstance().postJSON(context, CONST.URL_INSERT_IMG_GALERY, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("state") != 1) {
                        view.insertImageError();
                        return;
                    }
                    GaleryImage galeryImage = new GaleryImage();
                    galeryImage.setNameImage(imageName);
                    view.insertImageOk(galeryImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.insertImageError();
                }
                Log.v("response", "response ok");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("response", "error in the request");
                view.insertImageError();
            }
        });
    }
}
