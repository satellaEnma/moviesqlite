package com.kagu.submitthree.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tmdb implements Parcelable {
    private int idMovie;
    private int idTv;
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("poster_path")
    @Expose
    private String poster_path;

    @SerializedName("popularity")
    @Expose
    private String popularity;

    @SerializedName("original_name")
    @Expose
    private String tvname;

    public Tmdb() {

    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getIdTv() {
        return idTv;
    }

    public void setIdTv(int idTv) {
        this.idTv = idTv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTvname() {
        return tvname;
    }

    public void setTvname(String tvname) {
        this.tvname = tvname;
    }

    public static Creator<Tmdb> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.idMovie);
        parcel.writeInt(this.idTv);
        parcel.writeString(this.overview);
        parcel.writeString(this.title);
        parcel.writeString(this.tvname);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.popularity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Tmdb(Parcel in) {
        this.id = in.readInt();
        this.idMovie = in.readInt();
        this.idTv = in.readInt();
        this.overview = in.readString();
        this.title = in.readString();
        this.tvname = in.readString();
        this.poster_path = in.readString();
        this.popularity = in.readString();
    }

    public static final Parcelable.Creator<Tmdb> CREATOR = new Parcelable.Creator<Tmdb>() {
        @Override
        public Tmdb createFromParcel(Parcel in) {
            return new Tmdb(in);
        }

        @Override
        public Tmdb[] newArray(int size) {
            return new Tmdb[size];
        }
    };
}
