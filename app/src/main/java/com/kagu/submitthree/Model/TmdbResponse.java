package com.kagu.submitthree.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TmdbResponse {

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("results")
    @Expose
    private ArrayList<Tmdb> tmdbs;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Tmdb> getTmdbs() {
        return tmdbs;
    }

    public void setTmdbs(ArrayList<Tmdb> tmdbs) {
        this.tmdbs = tmdbs;
    }
}
