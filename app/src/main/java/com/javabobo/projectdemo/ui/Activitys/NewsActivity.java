package com.javabobo.projectdemo.ui.Activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.javabobo.projectdemo.Models.New;
import com.javabobo.projectdemo.R;

import butterknife.BindView;

public class NewsActivity extends BaseActivity {

    private New.Item firstNew;
    @BindView(R.id.headline_news)
    TextView titleNew;
    @BindView(R.id.content_new)
    TextView newContent;
    @BindView(R.id.new_image)
    ImageView newImage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.firstNew = (New.Item) getIntent().getExtras().getSerializable("new");
        titleNew.setText(firstNew.getTitle());
        newContent.setText(firstNew.getDescription());

        Glide.with(this)
                .load(firstNew.getImg().getUrl()).centerCrop()
                .into(newImage);
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_news;
    }

    @Override
    public String getTitleActionBar() {
        return "News";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }
}
