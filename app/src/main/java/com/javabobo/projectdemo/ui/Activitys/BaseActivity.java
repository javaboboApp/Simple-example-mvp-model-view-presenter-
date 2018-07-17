package com.javabobo.projectdemo.ui.Activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.javabobo.projectdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Nullable //Can be null
    @BindView(R.id.toolbar)
    Toolbar appbar;
    private ActionBar actionBar;
   private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getIdContendView());
        ButterKnife.bind(this);

        if (appbar != null) {
            setSupportActionBar(appbar);
            appbar.setVisibility(View.VISIBLE);
            actionBar = getSupportActionBar();
            String title = getTitleActionBar();
            if (title != null)
                actionBar.setTitle(title);
            if (haveArrowBack()) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

        }
    }

    protected Toolbar getAppbar() {
        return appbar;
    }

    protected void setActionBarTitle(int title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setActionBarTitle(CharSequence title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract int getIdContendView();

    protected void startProgressBar() {
        if(progress != null && progress.isShowing()) return;
        progress= new ProgressDialog(this);
        progress.setMessage("Working... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    protected void stopProgress() {
        if (progress != null && progress.isShowing()) {
            progress.cancel();
        }
    }

    public abstract String getTitleActionBar();

    public abstract boolean haveArrowBack();
}
