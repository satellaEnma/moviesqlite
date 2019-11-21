package com.kagu.submitthree.DetailActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.MoviePackage.MovieDB;
import com.kagu.submitthree.R;
import com.kagu.submitthree.TvPackage.TvsDB;
import com.kagu.submitthree.databases.DbHelper;

import java.util.ArrayList;

public class DetailTmdbTvActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textTvName, textTvDesc;
    public static final String TV_KEY = "tv_key";
    public static final String MOVIE_KEY = "movie_key";
    public static final String FAV_KEY = "fav_key";
    public static final int REQUEST_1 = 200;
    public static final int REQUEST_INSERT = 100;
    public static final int RESULT_INSERT = 101;
    public static final int RESULT_DEL = 301;
    private Tmdb tmdb;
    private ImageView imageTv;
    private int i;
    private TvsDB tvsDB;
    private MovieDB movieDB;

    private boolean isLove = true;
    private boolean isLoveDel = true;
    private boolean isLoveIns = true;
    private boolean showTmdb1;
    private ArrayList<? extends Parcelable> detilTmdb;

    private FloatingActionButton fabLove;
    private boolean resultInsert;
    private boolean resultDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTmdb1 = getIntent().getBooleanExtra("Tmdb1", true);

        Tmdb movie = getIntent().getParcelableExtra(MOVIE_KEY);
        Tmdb tv = getIntent().getParcelableExtra(TV_KEY);
        if (movie != null) {
            setContentView(R.layout.activity_detail_tmdb_tv);
            String img_movie = "https://image.tmdb.org/t/p/w342" + movie.getPoster_path();

            imageTv = findViewById(R.id.imgDetailTv);
            textTvName = findViewById(R.id.title_detailTv);
            textTvDesc = findViewById(R.id.desc_detailTv);
            fabLove = findViewById(R.id.fab_love);

            Glide.with(this).load(img_movie).apply(new RequestOptions().override(150, 200)).into(imageTv);
            textTvName.setText(movie.getTitle());
            textTvDesc.setText(movie.getOverview());
            movieDB = MovieDB.getMovieDB(getApplicationContext());
            movieDB.open();
            getSupportActionBar().setTitle(movie.getTitle());
            String mTitle = movie.getTitle();
            Log.d("check", "Notice: " + mTitle + movieDB.getFavM());
            if (movieDB.getLove(mTitle)) {
                isLove = false;
                isLoveDel = false;
                fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav));
            } else if (!movieDB.getLove(mTitle)) {
                isLove = true;
                isLoveIns = true;
                fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav2));
            }

            fabLove.setOnClickListener(this);
        } else if (tv != null) {
            setContentView(R.layout.activity_detail_tmdb_tv);
            imageTv = findViewById(R.id.imgDetailTv);
            String img_tv = "https://image.tmdb.org/t/p/w342" + tv.getPoster_path();
            Glide.with(this).load(img_tv).apply(new RequestOptions().override(150, 200)).into(imageTv);
            textTvName = findViewById(R.id.title_detailTv);
            textTvDesc = findViewById(R.id.desc_detailTv);
            fabLove = findViewById(R.id.fab_love);
            fabLove.setOnClickListener(this);

            textTvName.setText(tv.getTvname());
            textTvDesc.setText(tv.getOverview());
            tvsDB = TvsDB.getTvsDB(getApplicationContext());
            tvsDB.openTv();
            getSupportActionBar().setTitle(tv.getTvname());
            String tvTitle = tv.getTvname();
            Log.d("check", "Notice: " + tvTitle + tvsDB.getFavTv());
            if (tvsDB.getLove(tvTitle)) {
                isLove = false;
                isLoveDel = false;
                fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav));
            } else if (!tvsDB.getLove(tvTitle)) {
                isLove = true;
                isLoveIns = true;
                fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav2));
            }
        }

    }

    private void setLove() {
        if (isLove)
            fabLove.setImageResource(R.drawable.ic_fav);

        else
            fabLove.setImageResource(R.drawable.ic_fav2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieDB != null)
            movieDB.close();
        if (tvsDB != null)
            tvsDB.closeTv();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_love) {
            Intent intentLove = new Intent();

            Tmdb movie = getIntent().getParcelableExtra(MOVIE_KEY);
            Tmdb tv = getIntent().getParcelableExtra(TV_KEY);
            if (movie != null) {
                intentLove.putExtra(MOVIE_KEY, movie);
                intentLove.putExtra(FAV_KEY, i);
                if (isLoveIns && isLove) {
                    long result = movieDB.addFavM(movie);
                    movie.setId((int) result);
                    setResult(RESULT_INSERT, intentLove);
                    if (result > 0) {
                        Toast.makeText(this, R.string.fav_add, Toast.LENGTH_SHORT).show();
                        fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav));
                    }
                    else {
                        Toast.makeText(this, R.string.fav_remove, Toast.LENGTH_SHORT).show();
                        fabLove.setImageResource(R.drawable.ic_fav2);
                    }
                } else if (!isLoveDel && !isLove) {
                    long result = movieDB.delFav(movie.getId());
                    if (result > 0) {
                        setResult(RESULT_DEL, intentLove);
                        fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav2));
                        Toast.makeText(this, R.string.fav_remove, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, R.string.fav_remove, Toast.LENGTH_SHORT).show();
                        fabLove.setImageResource(R.drawable.ic_fav);
                    }
                }
            } else if (tv != null) {
                intentLove.putExtra(TV_KEY, tv);
                intentLove.putExtra(FAV_KEY, i);
                if (isLoveIns && isLove) {
                    long result = tvsDB.addFavTv(tv);
                    tv.setId((int) result);
                    setResult(RESULT_INSERT, intentLove);
                    if (result > 0) {
                        Toast.makeText(this, R.string.fav_add, Toast.LENGTH_SHORT).show();
                        fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav));
                    }
                    else {
                        Toast.makeText(this, R.string.fav_remove, Toast.LENGTH_SHORT).show();
                        fabLove.setImageResource(R.drawable.ic_fav2);
                    }
                } else if (!isLoveDel && !isLove) {
                    long result = tvsDB.delFavTv(tv.getId());
                    if (result > 0) {
                        setResult(RESULT_DEL, intentLove);
                        fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav2));
                        Toast.makeText(this, R.string.fav_remove, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, R.string.fav_remove, Toast.LENGTH_SHORT).show();
                        fabLove.setImageResource(R.drawable.ic_fav);
                    }
                }
            }
//            setLove();
        }
    }
}
