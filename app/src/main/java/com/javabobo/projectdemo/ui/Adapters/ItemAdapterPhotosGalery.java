package com.javabobo.projectdemo.ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.R;
import com.javabobo.projectdemo.Utils.CONST;
import com.javabobo.projectdemo.Utils.SessionManager;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luis on 15/02/2018.
 */

public class ItemAdapterPhotosGalery extends RecyclerView.Adapter<ItemAdapterPhotosGalery.MyViewHolder> {
    private final Context context;
    private final User user;
    private final Listener listener;
    private LinkedList<GaleryImage> images;

    public interface Listener{
        void onClickImage(String url, String n);
    }
    public ItemAdapterPhotosGalery(Context context, LinkedList<GaleryImage> galeryImages, ItemAdapterPhotosGalery.Listener listener) {
        this.images = galeryImages;
        this.context = context;
        this.user = SessionManager.getInstance(context).getCurrentUser();
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(context).
                inflate(R.layout.item_recicle_view_galery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GaleryImage galeryImage = images.get(position);
        final String url = CONST.URL_IMG+ user.getPathDir()+"/images/galery/"+ galeryImage.getImageName();
        Glide.with(context).load(url).centerCrop().into(holder.vImage);

        holder.vImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickImage(url,galeryImage.getImageName());
            }
        });

    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
        holder.vImage.setImageResource(0);
    }

    public void setCollection(LinkedList<GaleryImage> galeryImages){
        this.images.clear();
        this.images.addAll(galeryImages);
        notifyDataSetChanged();
    }
    public void addImage(GaleryImage galeryImage){
        images.addFirst(galeryImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        public ImageView vImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

}
