package com.example.yasina.llapp.Train;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.io.ByteArrayInputStream;

/**
 * Created by yasina on 08.04.15.
 */
public class FragmentOfWord extends Fragment {


    static final String TAG = "myLogs";

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SAVE_PAGE_NUMBER = "save_page_number";

    int pageNumber;
    private Words word;

    public FragmentOfWord(Words word){
        this.word = word;
    }

       static FragmentOfWord newInstance(int page,Words wo) {
           Bundle arguments = new Bundle();
           FragmentOfWord fragmentOfWord = new FragmentOfWord(wo);
           arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
           fragmentOfWord.setArguments(arguments);
           return fragmentOfWord;
       }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.list_item_words_pair_2, null);

            TextView txtFirstLang = (TextView) view.findViewById(R.id.txt_word_list_item_wp);
            TextView txtSecondLang = (TextView) view.findViewById(R.id.txt_translate_list_item_wp);
            ImageView ivPicture = (ImageView) view.findViewById(R.id.ivPicture_list_item_wp);
            TextView txtExplanation = (TextView) view.findViewById(R.id.txt_explanation_item_wp);

            txtFirstLang.setText(word.getFirstLang());
            txtSecondLang.setText(word.getSecondLang());

            byte[] outImage = word.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            ivPicture.setImageBitmap(theImage);

            txtExplanation.setText(word.getExplanation());
            return view;
        }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + pageNumber);
    }

}
