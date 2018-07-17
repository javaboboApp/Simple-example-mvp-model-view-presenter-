package com.javabobo.projectdemo.ui.Activitys;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.javabobo.projectdemo.Models.Play;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.mvp.Contracts.SportContract;
import com.javabobo.projectdemo.mvp.Presenters.SportPresenter;
import com.javabobo.projectdemo.ui.Adapters.ItemAdapterPlay;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;

public class SportActivity extends BaseActivity implements SportContract.View {
    @BindView(R.id.team_input)
    EditText teamInput;
    @BindView(R.id.input_team_container)
    TextInputLayout textInputLayout;
    private SportContract.Presenter presenter;

    @BindView(R.id.list_view)
    ListView listView;

    private LinkedList<Play> plays;
    private boolean inside;//false for default...
    private ItemAdapterPlay itemAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        super.onCreate(savedInstanceState);
        initPresenter();
        bringPlays();

    }


    private void bringPlays() {
        presenter.getSport(this);
    }

    private void initPresenter() {
        presenter = new SportPresenter(this);
    }

    @Override
    public int getIdContendView() {
        return R.layout.activity_sport;
    }

    @Override
    public String getTitleActionBar() {
        return "Sports";
    }

    @Override
    public boolean haveArrowBack() {
        return true;
    }

    @Override
    public void responseSportOk(LinkedList<Play> plays) {
        this.plays = plays;
        initListener();
    }

    private void initListener() {
        this.itemAdapter = new ItemAdapterPlay(this, new ArrayList<>(plays));
        listView.setAdapter(itemAdapter);
        teamInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && plays != null) {
                    itemAdapter.filter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
