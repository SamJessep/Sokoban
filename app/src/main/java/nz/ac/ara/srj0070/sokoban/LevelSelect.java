package nz.ac.ara.srj0070.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nz.ac.ara.srj0070.model.Game;
import nz.ac.ara.srj0070.model.interfaces.IGame;

public class LevelSelect extends AppCompatActivity {

    IGame mGame;
    List<IGame> games;
    ImageButton ibtn_back;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    public static Intent makeIntent(StartMenu context) {
        return new Intent(context, LevelSelect.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        games = new ArrayList<>();
        mGame = new Game();
        mGame = fetchlevels(mGame);
        games.add(mGame);
        games.add(new Game());
        games.add(mGame);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 1);
        mViewPager = findViewById(R.id.level_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ibtn_back = findViewById(R.id.btn_back);

        ibtn_back.setOnClickListener(v -> finish());
        TabLayout tabLayout = findViewById(R.id.tl_levelTypes);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private IGame fetchlevels(IGame game) {
        sqliteHelper levelDB = null;
        try {
            levelDB = new sqliteHelper(this);
        } catch (Exception e) {
            Log.d("db error", e.toString());
        }
        return levelDB.getAllLevels(game);
    }

    private void drawLevelList() {


    }

    private void drawLevelSelect() {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = LevelList.newInstance(games.get(position));
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tb_default);
                case 1:
                    return getResources().getString(R.string.tb_community);
                case 2:
                    return getResources().getString(R.string.tb_myLevels);
            }
            return null;
        }
    }
}
