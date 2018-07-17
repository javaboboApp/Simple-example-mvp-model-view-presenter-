package com.javabobo.projectdemo.ui.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.ImageUtils;
import com.javabobo.projectdemo.Utils.imagepick.ImagePickHelper;
import com.javabobo.projectdemo.Utils.imagepick.OnImagePickedListener;
import com.javabobo.projectdemo.mvp.Contracts.PhotosContract;
import com.javabobo.projectdemo.mvp.Presenters.PhotosPresenter;
import com.javabobo.projectdemo.ui.Adapters.ItemAdapterPhotosGalery;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

public class PhotosActivity extends BaseActivity implements OnImagePickedListener, PhotosContract.View {

    public static final int RESET_CODE = 546;
    @BindView(R.id.list_galery)
    RecyclerView listGalery;
    private PhotosContract.Presenter presenter;

    private ItemAdapterPhotosGalery itemAdapterPhotosGalery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        initRecycleView();
        bringImages();
    }

    private void bringImages() {
        startProgressBar();
        presenter.bringMyImagesFromGalery(this);
    }

    private void initRecycleView() {
        itemAdapterPhotosGalery = new ItemAdapterPhotosGalery(this, new LinkedList<GaleryImage>(), new ItemAdapterPhotosGalery.Listener() {
            @Override
            public void onClickImage(String url, String nameImage) {
                Intent intent = new Intent(PhotosActivity.this, ShowMaxImgActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("name", nameImage);
                startActivityForResult(intent, 11);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        listGalery.setLayoutManager(gridLayoutManager);
        listGalery.setAdapter(itemAdapterPhotosGalery);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK) {
            //Reset the list and bring images again...
            initRecycleView();
            bringImages();
            //TODO CHECK IF IS THE LAS IMAGES IF IS THE LAST IMAGES REMOVE IN THE HOMEACTIVITY ELSE UPDATE.

        }
    }

    private void initPresenter() {
        presenter = new PhotosPresenter(this);

    }

    @OnClick(R.id.add_image)
    public void addImg(View v) {
        new ImagePickHelper().pickAnImage(this, 72);
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_photos;
    }

    @Override
    public String getTitleActionBar() {
        return "Photos";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }

    @Override
    public void onImagePicked(int requestCode, File file) {


        Bitmap yourSelectedImage = BitmapFactory.decodeFile(file.getPath());
        try {
            yourSelectedImage = ImageUtils.checkAngle(file.getPath(), yourSelectedImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String nameImage = System.currentTimeMillis() + ".jpg";
        startProgressBar();
        presenter.insertImagePicture(PhotosActivity.this, nameImage, yourSelectedImage);
    }

    @Override
    public void onImagePickError(int requestCode, Exception e) {

    }

    @Override
    public void onImagePickClosed(int requestCode) {

    }

    @Override
    public void responseOk(LinkedList<GaleryImage> galeryImages) {
        stopProgress();
        itemAdapterPhotosGalery.setCollection(galeryImages);

    }

    @Override
    public void error() {
        stopProgress();
        Toast.makeText(PhotosActivity.this, "Error to brings images", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insertImageOk(GaleryImage galeryImage) {
        stopProgress();
        listGalery.setAdapter(null);
        listGalery.setLayoutManager(null);
        listGalery.setAdapter(itemAdapterPhotosGalery);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        listGalery.setLayoutManager(gridLayoutManager);

        itemAdapterPhotosGalery.addImage(galeryImage);
        itemAdapterPhotosGalery.notifyDataSetChanged();
        Intent intent = new Intent();
        intent.putExtra("img_galery", galeryImage);
        setResult(RESULT_OK, intent);

    }

    @Override
    public void insertImageError() {
        Toast.makeText(PhotosActivity.this, "Error to insert the image", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void galeryEmpty() {
        stopProgress();
    }
}
