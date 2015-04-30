package com.example.yasina.llapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentTabColor extends SherlockFragment {

   // private static final String COLOR = "color";
      private static final String WORD = "bla";
      private int word;
    //private int color;

    public FragmentTabColor() {
    }

  /*  public void setColor(int color) {
        this.color = color;
    }*/
    public void setHowAddWords(int word)
    {
        this.word = word;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null)
          //  color = savedInstanceState.getInt(COLOR);
              word = savedInstanceState.getInt(WORD);

       // View view = inflater.inflate(R.layout.fragment_second_color, container, false);
        View view = inflater.inflate(R.layout.add_words, container, false);
        //if(word == 0)
              //  view = inflater.inflate(R.layout.add_words_document, container, false);
        //else
          //      view = inflater.inflate(R.layout.add_words, container, false);

        //ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

      //  imageView.setBackgroundColor(color);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
       // outState.putInt(COLOR, color);
        outState.putInt(WORD,word);
    }

}
