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

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavViewHolder> {
    private final ArrayList<Tmdb> listFavM = new ArrayList<>();
    private final FavMovie favMovie;
    private static final String URL_IMG = "https://image.tmdb.org/t/p/w342";

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tmdb, parent, false);
        return new FavViewHolder(view);
    }

    public void addFavM(Tmdb tmdb) {
        this.listFavM.add(tmdb);
        notifyItemInserted(listFavM.size() - 1);
    }

    public FavMovieAdapter(FavMovie favMovie) {
        this.favMovie = favMovie;
    }

    public void delFavM(int i) {
        this.listFavM.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, listFavM.size());
    }

    public void setListFavM(ArrayList<Tmdb> listFavM) {
        if (listFavM.size() > 0) {
            this.listFavM.clear();
        }
        this.listFavM.addAll(listFavM);
        notifyDataSetChanged();
    }


    public ArrayList<Tmdb> getListFavM() {
        return listFavM;
    }

    @Override
    public int getItemCount() {
        return listFavM.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle;
        TextView textDesc;
        TextView textPopularity;
        ImageView imgPhoto;
        RelativeLayout list_item;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            list_item = itemView.findViewById(R.id.list_item);
            textTitle = itemView.findViewById(R.id.tmdb_name);
            textDesc = itemView.findViewById(R.id.tmdb_desc);
            textPopularity = itemView.findViewById(R.id.tmdb_popularity);
            imgPhoto = itemView.findViewById(R.id.imgTmdb);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intentClicked = new Intent(favMovie.getContext(), DetailTmdbTvActivity.class);
            intentClicked.putExtra(DetailTmdbTvActivity.MOVIE_KEY, listFavM.get(position));
            intentClicked.putExtra(DetailTmdbTvActivity.FAV_KEY, position);
            favMovie.startActivityForResult(intentClicked, DetailTmdbTvActivity.REQUEST_1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, final int position) {
        holder.textTitle.setText(listFavM.get(position).getTitle());
        holder.textDesc.setText(listFavM.get(position).getOverview());
        holder.textPopularity.setText(listFavM.get(position).getPopularity());
        String url_img = URL_IMG + listFavM.get(position).getPoster_path();
        Glide.with(holder.itemView.getContext()).load(url_img).into(holder.imgPhoto);
        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentList = new Intent(favMovie.getContext(), DetailTmdbTvActivity.class);
                intentList.putExtra(DetailTmdbTvActivity.FAV_KEY, position);
                intentList.putExtra(DetailTmdbTvActivity.MOVIE_KEY, listFavM.get(position));
                favMovie.startActivityForResult(intentList, DetailTmdbTvActivity.REQUEST_1);
            }
        });
    }

}
