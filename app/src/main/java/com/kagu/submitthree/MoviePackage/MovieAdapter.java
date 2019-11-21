package com.kagu.submitthree.MoviePackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity;
import com.kagu.submitthree.Interface.GetTmdbCallback;
import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.TmdbMovieViewHolder> {

    private LibraryMovie libraryMovie;
    private MovieAdapter movieAdapter;
    private List<Tmdb> tmdbs;
    private String IMG_URL = "http://image.tmdb.org/t/p/w342";
    private Context context;

    private OnItemClickCallbak onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallbak onItemClickCallbak) {
        if (onItemClickCallbak != null) {
            this.onItemClickCallback = onItemClickCallbak;
        }
    }

    public interface OnItemClickCallbak {
        void onItemClicked(Tmdb tmdb);
    }

    public MovieAdapter(List<Tmdb> tmdbs, Context context) {
        this.tmdbs = tmdbs;
        this.context = context;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(final TmdbMovieViewHolder holder, int position) {

        libraryMovie = LibraryMovie.getInstance();
        holder.bind(tmdbs.get(position));
    }

    class TmdbMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView title, overview, popularity;
        final ImageView image;

        TmdbMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tmdb_name);
            overview = itemView.findViewById(R.id.tmdb_desc);
            image = itemView.findViewById(R.id.imgTmdb);
            popularity = itemView.findViewById(R.id.tmdb_popularity);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Tmdb tmdb = tmdbs.get(position);
            Intent intentClicked = new Intent(context, DetailTmdbTvActivity.class);
            intentClicked.putExtra(DetailTmdbTvActivity.MOVIE_KEY, tmdb);
            intentClicked.putExtra("Tmdb1", false);
            context.startActivity(intentClicked);
        }
        void bind(Tmdb tmdb){
            title.setText(tmdb.getTitle());
            overview.setText(tmdb.getOverview());
            Glide.with(itemView.getContext())
                    .load(IMG_URL + tmdb.getPoster_path())
                    .apply(new RequestOptions().override(100, 100))
                    .into(image);

            popularity.setText(tmdb.getPopularity());

        }
    }

    @Override
    public int getItemCount() {
        return tmdbs.size();
    }

    @NonNull
    @Override
    public TmdbMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tmdb, parent, false);
        return new TmdbMovieViewHolder(views);
    }
}
