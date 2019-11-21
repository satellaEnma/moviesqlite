package com.kagu.submitthree.FavPackage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity;
import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.R;

import java.util.ArrayList;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavViewHolder> {
    private final ArrayList<Tmdb> listFavTv = new ArrayList<>();
    private final FavTv favTv;
    private static final String URL_IMG = "https://image.tmdb.org/t/p/w342";


    public ArrayList<Tmdb> getListFavTv() {
        return listFavTv;
    }

    public FavTvAdapter(FavTv favTv) {
        this.favTv = favTv;
    }

    public void addFavTv(Tmdb tmdb) {
        this.listFavTv.add(tmdb);
        notifyItemInserted(listFavTv.size() - 1);
    }

    public void setListFavTv(ArrayList<Tmdb> listFavTv) {
        if (listFavTv.size() > 0) {
            this.listFavTv.clear();
        }
        this.listFavTv.addAll(listFavTv);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tmdb, parent, false);
        return new FavViewHolder(view);
    }

    public void delFavTv(int i) {
        this.listFavTv.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, listFavTv.size());
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, final int position) {
        holder.textDesc.setText(listFavTv.get(position).getOverview());
        holder.textPopularity.setText(listFavTv.get(position).getPopularity());
        holder.textTitle.setText(listFavTv.get(position).getTvname());
        String url_img = URL_IMG + listFavTv.get(position).getPoster_path();
        Glide.with(holder.itemView.getContext()).load(url_img).into(holder.imgPhoto);
        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentList = new Intent(favTv.getContext(), DetailTmdbTvActivity.class);
                intentList.putExtra(DetailTmdbTvActivity.FAV_KEY, position);
                intentList.putExtra(DetailTmdbTvActivity.TV_KEY, listFavTv.get(position));
                favTv.startActivityForResult(intentList, DetailTmdbTvActivity.REQUEST_1);
            }
        });
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDesc;
        TextView textPopularity;
        ImageView imgPhoto;
        RelativeLayout list_item;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            textDesc = itemView.findViewById(R.id.tmdb_desc);
            textTitle = itemView.findViewById(R.id.tmdb_name);
            textPopularity = itemView.findViewById(R.id.tmdb_popularity);
            imgPhoto = itemView.findViewById(R.id.imgTmdb);
            list_item = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public int getItemCount() {
        return listFavTv.size();
    }

}
