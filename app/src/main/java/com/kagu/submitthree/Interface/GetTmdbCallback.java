package com.kagu.submitthree.Interface;

import com.kagu.submitthree.Model.Tmdb;

import java.util.ArrayList;
import java.util.List;

public interface GetTmdbCallback {

    void onError();

    void onSuccess(ArrayList<Tmdb> tmdbs);
}
