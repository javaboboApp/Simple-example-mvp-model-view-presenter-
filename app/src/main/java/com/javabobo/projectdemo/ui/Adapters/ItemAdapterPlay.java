package com.javabobo.projectdemo.ui.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.javabobo.projectdemo.Models.Play;
import com.javabobo.projectdemo.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by luis on 15/02/2018.
 */

public class ItemAdapterPlay extends ArrayAdapter<Play> {

    private ArrayList<Play> plays = new ArrayList<>();
    private ArrayList<Play> playsaux;
    private Context context;

    public ItemAdapterPlay(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public ItemAdapterPlay(Context context, ArrayList<Play> plays) {
        super(context, 0, plays);
        this.context = context;
        //aliasing
        this.plays = plays;
        this.playsaux = new ArrayList<>(plays);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_play, null);
            holder = new ViewHolder();
            holder.nameTeam = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        Play play = plays.get(position);
        holder.nameTeam.setText(play.getTextToShow());
        return convertView;
    }

    // filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        plays.clear();

        for (Play play : playsaux) {
            boolean a = (play.getHomeTeam()).toLowerCase(Locale.getDefault()).equals(charText.toLowerCase());
            boolean b = (play.getAwayTeam()).toLowerCase(Locale.getDefault()).equals(charText.toLowerCase());
            if (a || b) {
                if (a && play.getResult().equals("H") || b && play.getResult().equals("A")) {
                    play.setTextToShow(play.getAwayTeam().toLowerCase().equals(charText.toLowerCase()) ? play.getHomeTeam() : play.getAwayTeam());
                    plays.add(play);
                }
            }
        }

        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView nameTeam;

    }
}
