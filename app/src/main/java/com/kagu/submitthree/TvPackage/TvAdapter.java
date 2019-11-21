package com.kagu.submitthree.TvPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TmdbTvViewHolder> {

    private TvAdapter tvAdapter;
    private LibraryTv libraryTv;
    private OnItemClickCallback onItemClickCallback;
    private List<Tmdb> tmdbs;
    private final Context context;

    public interface OnItemClickCallback {
        void onItemClicked(Tmdb tmdb);
    }


    public TvAdapter(List<Tmdb> tmdbs, Context context) {
        this.context = context;
        this.tmdbs = tmdbs;
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TmdbTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tmdb, parent, false);
        return new TmdbTvViewHolder(views);
    }

    class TmdbTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView title, overview, popularity;
        final ImageView imageTv;

        TmdbTvViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tmdb_name);
            overview = itemView.findViewById(R.id.tmdb_desc);
            imageTv = itemView.findViewById(R.id.imgTmdb);
            popularity = itemView.findViewById(R.id.tmdb_popularity);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Tmdb tmdb = tmdbs.get(position);
            Intent intentClicked = new Intent(context, DetailTmdbTvActivity.class);
            intentClicked.putExtra(DetailTmdbTvActivity.TV_KEY, tmdb);
            intentClicked.putExtra("Tmdb1", false);
            context.startActivity(intentClicked);
        }

        void bind(Tmdb tmdb){
            title.setText(tmdb.getTvname());
            overview.setText(tmdb.getOverview());
            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w342" + tmdb.getPoster_path())
                    .apply(new RequestOptions().override(100, 100))
                    .into(imageTv);
            popularity.setText(tmdb.getPopularity());
        }
    }


    @Override
    public int getItemCount() {
        return tmdbs.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final TmdbTvViewHolder holder, int position) {
        libraryTv = LibraryTv.getInstance();
        holder.bind(tmdbs.get(position));
    }
}
