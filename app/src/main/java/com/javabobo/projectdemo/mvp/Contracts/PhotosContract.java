package com.javabobo.projectdemo.mvp.Contracts;

import android.content.Context;
import android.graphics.Bitmap;

import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.mvp.Shared.BaseContract;

import java.util.LinkedList;

/**
 * Created by luis on 14/02/2018.
 */

public class PhotosContract {

    public interface View extends BaseContract.View {
        void responseOk(LinkedList<GaleryImage> galeryImages);
        void error();
        void insertImageOk(GaleryImage galeryImage);
        void insertImageError();
        void galeryEmpty();
    }


    public interface Presenter {
        void bringMyImagesFromGalery(Context context);
        void insertImagePicture(Context context, String imageName, Bitmap image);
    }

}
