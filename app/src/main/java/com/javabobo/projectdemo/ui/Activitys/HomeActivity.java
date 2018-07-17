package com.javabobo.projectdemo.ui.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.genyus.pacpiechart.library.Pacpie;
import com.genyus.pacpiechart.library.PacpieSlice;
import com.javabobo.projectdemo.Models.CurrentLocation;
import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.Models.New;
import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.Models.Weather;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.General;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Contracts.HomeContract;
import com.javabobo.projectdemo.mvp.Presenters.HomePresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private HomeContract.Presenter presenter;

    @BindView(R.id.num_degress)
    TextView numDegress;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.header_news)
    TextView newHeadLine;
    @BindView(R.id.new_content)
    TextView newContent;
    private New.Item firtsNew;
    @BindView(R.id.name_user)
    TextView nameUser;
    @BindView(R.id.image_user)
    ImageView imageUser;
    @BindView(R.id.image_preview)
    ImageView imagePreview;
    @BindView(R.id.pacpieChart)
    Pacpie pacpie;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        getCurrentLocation();
        bringFirtsNewFromBBC();
        initUserInfo();
        bringImagePreview();
        bringClothesData();
    }

    public void toggle(View v) {
        pacpie.toggle();
    }

    private void bringClothesData() {
        presenter.getClothes(this);
    }


    private List<PacpieSlice> generateFakeSlices(LinkedList<Integer> integers) {
        List<PacpieSlice> slices = new ArrayList<>();
        float total = 0;
        for (Integer i : integers
                ) {
            total += i;
        }
        float a1 = ((integers.get(0) / total) * 100), a2 = ((integers.get(1) / total) * 100),
                a3 = ((integers.get(2) / total) * 100), a4 = ((integers.get(3) / total) * 100),
                a5 = ((integers.get(4) / total) * 100), a6 = ((integers.get(5) / total) * 100);

        PacpieSlice slice1 = new PacpieSlice();
        slice1.count(a1);
        slice1.color(getResources().getColor(R.color.slice_color));
        slices.add(slice1);

        PacpieSlice slice2 = new PacpieSlice();
        slice2.count(a2);
        slice2.color(getResources().getColor(R.color.slice_color_2));
        slices.add(slice2);

        PacpieSlice slice3 = new PacpieSlice();
        slice3.count(a3);
        slice3.color(getResources().getColor(R.color.slice_color_3));
        slices.add(slice3);

        //here we let the color value empty (default value inside library will be used)
        PacpieSlice slice4 = new PacpieSlice();
        slice4.count(a4);
        slices.add(slice4);

        PacpieSlice slice5 = new PacpieSlice();
        slice5.count(a5);
        slice5.color(getResources().getColor(R.color.slice_color_4));
        slices.add(slice5);


        PacpieSlice slice6 = new PacpieSlice();
        slice6.count(a6);
        slice6.color(getResources().getColor(R.color.slice_color_6));
        slices.add(slice6);

        return slices;
    }

    @Override
    protected void onResume() {
        super.onResume();

        pacpie.expand();
    }

    private void bringImagePreview() {
        presenter.getFirstImage(this);
    }

    private void initUserInfo() {

        User user = SessionManager.getInstance(this).getCurrentUser();
        nameUser.setText(user.getNameUser());
        String url = CONST.URL_IMG + user.getPathDir() + "/images/profile/profile.jpg";
        Glide.with(this).load(url).centerCrop().into(imageUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //only 1 option is <logout>.
        SessionManager.getInstance(HomeActivity.this).logoutUser();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void bringFirtsNewFromBBC() {
        presenter.getFirtsNew(this);
    }

    private void bringWeatherInfo(CurrentLocation currentLocation) {
        presenter.getWeather(this, currentLocation);

    }

    private void initPresenter() {
        presenter = new HomePresenter(this);
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_home;
    }

    @OnClick(R.id.news)
    public void onClickNews(View v) {
        if (firtsNew == null) return;

        Intent intent = new Intent(HomeActivity.this, NewsActivity.class);
        intent.putExtra("new", firtsNew);
        startActivity(intent);
    }

    @OnClick(R.id.sports)
    public void onClickSports(View v) {
        Intent intent = new Intent(HomeActivity.this, SportActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.photos)
    public void onClickPhotos(View v) {
        Intent intent = new Intent(HomeActivity.this, PhotosActivity.class);
        startActivityForResult(intent,22);
    }

    @OnClick(R.id.tasks)
    public void onClickTasks(View v) {
        Intent intent = new Intent(HomeActivity.this, TasksActivity.class);
        startActivity(intent);
    }

    @Override
    public String getTitleActionBar() {
        return "Hackathon";
    }

    @Override
    public boolean haveArrowBack() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode ==22  ) {
                GaleryImage galeryImage = (GaleryImage) data.getExtras().getSerializable("img_galery");
                String url = CONST.URL_IMG + SessionManager.getInstance(this).getCurrentUser().getPathDir() + "/images/galery/" + galeryImage.getImageName();
                Glide.with(this).load(url).centerCrop().into(imagePreview);
            }else if(requestCode == PhotosActivity.RESET_CODE) {
               //TODO DO SOMETHING...
            }
        }
    }

    @Override
    public void currentLocationOk(CurrentLocation currentLocation) {
        if (currentLocation != null) {
            bringWeatherInfo(currentLocation);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == General.TAG_CODE_PERMISSION_LOCATION && canAccessLocation()) {
            presenter.getLocation(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) && (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
    }

    @Override
    public void responseWeatherOk(Weather weather) {
        numDegress.setText(weather.getTemp());
        city.setText(weather.getCity());
    }

    @Override
    public void msjWeatherError() {

    }

    @Override
    public void msjNewOk(New.Item item) {
        newHeadLine.setText(item.getTitle());
        newContent.setText(item.getDescription());
        this.firtsNew = item;
    }

    @Override
    public void bringGaleryImgOk(GaleryImage galeryImage) {
        String url = CONST.URL_IMG + SessionManager.getInstance(this).getCurrentUser().getPathDir() + "/images/galery/" + galeryImage.getImageName();

        Glide.with(this).load(url).into(imagePreview);
    }

    @Override
    public void distributionList(LinkedList<Integer> distribution) {
        pacpie.setValues(generateFakeSlices(distribution));
        pacpie.expand();
    }

    @Override
    public void distributionError() {

    }

    public void getCurrentLocation() {
        presenter.getLocation(this);
    }
}
