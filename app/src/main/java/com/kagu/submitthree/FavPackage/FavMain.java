package com.kagu.submitthree.FavPackage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kagu.submitthree.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMain extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {


    public FavMain() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loadFav(new FavMovie());
        BottomNavigationView bottomNavMain = view.findViewById(R.id.tab_fav);
        bottomNavMain.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFav(Fragment favMovie) {
        if (favMovie != null) {
            getFragmentManager().beginTransaction().replace(R.id.fav_main, favMovie).commit();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fav_main, container, false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragfav = null;
        switch (menuItem.getItemId()) {
            case R.id.fav_tab_tv:
                fragfav = new FavTv();
                break;
            case R.id.fav_tab_movie:
                fragfav = new FavMovie();
                break;

        }
        return loadFav(fragfav);
    }
}
