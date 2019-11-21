package com.kagu.submitthree;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kagu.submitthree.FavPackage.FavMain;
import com.kagu.submitthree.MoviePackage.MoviesFragment;
import com.kagu.submitthree.TvPackage.TvFragment;
import com.kagu.submitthree.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    private void setView() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_language) {
            Intent intentMenu = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intentMenu);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new MoviesFragment(), getString(R.string.tabMovie));
        sectionsPagerAdapter.addFragment(new TvFragment(), getString(R.string.tabTv));
        sectionsPagerAdapter.addFragment(new FavMain(), getString(R.string.tabFav));

        viewPager.setAdapter(sectionsPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.notice)).setCancelable(false).setPositiveButton(getString(R.string.notive_y), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        }).setNegativeButton(getString(R.string.notice_n), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}