package com.javabobo.projectdemo.ui.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.SessionManager;
import com.javabobo.projectdemo.mvp.Interators.InteratorJSON;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowMaxImgActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView image;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getExtras().getString("url");
         name = getIntent().getExtras().getString("name");
        Glide.with(this).load(url).into(image);
    }

    @OnClick(R.id.delete)
    public void onClickDelete(View v){
        InteratorJSON.getUniqueInstance().queryJSON(this, CONST.URL_REMOVE_IMG_GALERY + "?id_dir=" +
                SessionManager.getInstance(this).getCurrentUser().getPathDir() + "&name_image="+name, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("state") == 1) {
                        setResult(RESULT_OK);
                        finish();
                        return;
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
    public int getIdContendView() {
        return R.layout.activity_show_max_img;
    }

    @Override
    public String getTitleActionBar() {
        return "";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }
}
