package com.example.yasina.llapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;

import java.io.InputStream;

public class LearnWordsActivity extends Activity{
        //extends SherlockFragment {

  /*  private ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.learn_words, container, false);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(getString(R.string.learn_words_fragment));



        return view;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_words);

       /* ImageView i = (ImageView)findViewById(R.id.picpic_learn_word);
        WordsDAO d = new WordsDAO(this);
        Words w = d.getWordById(2);
        byte[] im = w.getImage();



        Bitmap bitmap = BitmapFactory.decodeByteArray(im, 0, im.length);
        i.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 200, 120, false));*/
       // i.setImageBitmap(bitmap);

    }
}
