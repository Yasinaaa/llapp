package com.example.yasina.llapp.Train;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yasina.llapp.Activities.ListDictionariesActivity;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;


public class TrainWordsActivity extends FragmentActivity {

    private final String TAG = "myLogs";
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private WordsDAO themeWordsDAO;
    private ArrayList<Words> words;
    private String name = "";


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                startActivity(new Intent(getApplicationContext(), MenuTrainActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_train_words, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_train_words);

            getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));
            getActionBar().setTitle("Training");

            try {
                name = getIntent().getExtras().getString("table name");
                Log.d(" Name", name + "");
                themeWordsDAO = new WordsDAO(getApplicationContext(), name);
                words = new ArrayList<Words>();
                words = themeWordsDAO.getAllDictionaries();
                Log.d("Train", words.size() + "");

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
            }catch (Exception e){
                setContentView(R.layout.empty);

                String button1String = "Create dictionary";
                String button2String = "Cancel";

                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("Mistake");
                ad.setMessage("You don't have any themes of words. Please create new one for this.");
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        finish();
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {

                    }
                });
                AlertDialog alert = ad.create();
                alert.show();
            }
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
                return words.size();
            }

        }

    }

