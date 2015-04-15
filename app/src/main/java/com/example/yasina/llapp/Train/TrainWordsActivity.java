package com.example.yasina.llapp.Train;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;


public class TrainWordsActivity extends FragmentActivity {

        static final String TAG = "myLogs";
        static final int PAGE_COUNT = 10;

        ViewPager pager;
        PagerAdapter pagerAdapter;

    private WordsDAO themeWordsDAO;
    private ArrayList<Words> words;
    private String name = "";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_train_words);

            name = getIntent().getExtras().getString("table name");
            Log.d(" Name",name+"");
            themeWordsDAO = new WordsDAO(getApplicationContext(),name);
            words = new ArrayList<Words>();
            words =  themeWordsDAO.getAllDictionaries();
            Log.d("Train",words.size()+"");

            pager = (ViewPager) findViewById(R.id.pager);
            pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

            pager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    Log.d(TAG, "onPageSelected, position = " + position);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }


        private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

            public MyFragmentPagerAdapter(FragmentManager fm) {
                super(fm);
            }


            @Override
            public Fragment getItem(int position) {
                Log.d("words.get(position)"," " + words.get(position));
                return FragmentOfWord.newInstance(position,words.get(position));
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

        }

    }

