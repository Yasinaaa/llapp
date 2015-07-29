package com.example.yasina.llapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.yasina.llapp.Activities.ListDictionariesActivity;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.forPainting.GlobalBitmap;
import com.example.yasina.llapp.forPainting.MainPaint;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;

/**
 * Created by yasina on 22.07.15.
 */
public class AddWordsView extends SherlockFragment implements View.OnClickListener {

    private ActionBar actionBar;

    public static final String TAG = "AddWordsActivity";

    private String firstL, secondL;
    private TextView tvFirstL, tvSecondL;
    private DictionaryDAO dicDAO;
    private EditText mTxtFirstLang, mTxtSecondLang, mTxtExplanation;
    private Button mBtnAdd;
    private ImageView mImage;
    private byte[] img=null;
    private WordsDAO wordsDAO;
    private String name = "";
    private int currentMenuPosition = -1;
    private SlidingMenu menu;
    private Dictionary dic;
    private String tableName = null;
    private String themeName = null;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_words, container, false);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Add Words");

        //
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));

        tvFirstL = (TextView) view.findViewById(R.id.tvFirstLangAddWord);
        tvSecondL = (TextView) view.findViewById(R.id.tvSecondLangAddWord);

        initViews();
        try {
            tableName = getSherlockActivity().getIntent().getExtras().getString("table name");
        }catch (RuntimeException e){

        }

        try {

            this.wordsDAO = new WordsDAO(getSherlockActivity().getApplicationContext());
            dicDAO = new DictionaryDAO(getSherlockActivity().getApplicationContext());

            dic = dicDAO.getDicitonaryById(MainMenuActivity.clickedDictionary);

            StringTokenizer tokenizer = new StringTokenizer(dic.getName(),"-");

            while (tokenizer.hasMoreElements()) {
                firstL = tokenizer.nextToken();
                secondL = tokenizer.nextToken();
                name = firstL + "_" + secondL;
            }
            tvFirstL.setText(firstL);
            tvSecondL.setText(secondL);

        }catch (Exception e1){

            view = inflater.inflate(R.layout.empty, container, false);

            String button1String = "Create dictionary";
            String button2String = "Cancel";

            AlertDialog.Builder ad = new AlertDialog.Builder(getSherlockActivity().getApplicationContext());
            ad.setTitle("Mistake");
            ad.setMessage("You don't have any dictionaries.Please create new one for this.");
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(getSherlockActivity().getApplicationContext(),ListDictionariesActivity.class));
                }
            });
            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    getSherlockActivity().finish();
                }
            });
            ad.setCancelable(true);
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    getSherlockActivity().finish();
                }
            });
            AlertDialog alert = ad.create();
            alert.show();
        }

        try{
            int id = getSherlockActivity().getIntent().getExtras().getInt("id");
            WordsDAO w = new WordsDAO(getSherlockActivity().getApplicationContext());
            Words word = w.getWordById(id);
            tvFirstL.setText(word.getFirstLang());
            tvSecondL.setText(word.getSecondLang());
            mTxtExplanation.setText(word.getExplanation());
            try{
                themeName = getSherlockActivity().getIntent().getExtras().getString("themeName");
                w.deleteWordFromTheme(tableName,word);
            }catch (RuntimeException e){

            }
        }catch (RuntimeException e){

        }
        //

        return view;
    }

    private void initViews() {
        this.mTxtFirstLang = (EditText) view.findViewById(R.id.etNewWord);
        this.mTxtSecondLang = (EditText) view.findViewById(R.id.etNewWordTranslate);
        this.mTxtExplanation =  (EditText) view.findViewById(R.id.etNewWordAssociation);
        this.mImage = (ImageView) view.findViewById(R.id.ivPictureOfWord);
        mImage.setOnClickListener(this);
        this.mBtnAdd = (Button) view.findViewById(R.id.btnADD_addWordActivity);
        mBtnAdd.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnADD_addWordActivity:

                initViews();
                this.wordsDAO = new WordsDAO(getSherlockActivity().getApplicationContext(),name);

                if (mTxtFirstLang != null && mTxtSecondLang != null && mTxtExplanation != null
                        && mImage != null) {

                    Words createdWordsPair = wordsDAO.createWordsPair(
                            mTxtFirstLang.getText().toString(), mTxtSecondLang.getText().toString(), img, mTxtExplanation.getText().toString());

                    if(tableName != null){
                        wordsDAO.addWordToTheme(tableName, createdWordsPair);
                    }


                    Log.d("ADD WORD", "added word pair : " + createdWordsPair.getmId());

                    Toast.makeText(getSherlockActivity().getApplicationContext(), "word pair_created_successfully", Toast.LENGTH_LONG).show();
                    this.mTxtFirstLang.setText("");
                    this.mTxtSecondLang.setText("");
                    this.mTxtExplanation.setText("");
                    mImage.setImageResource(R.drawable.paint);
                } else {
                    Toast.makeText(getSherlockActivity().getApplicationContext(), "empty_fields_message", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.ivPictureOfWord:
                Intent request = new Intent(getSherlockActivity().getBaseContext(), MainPaint.class);
                startActivityForResult(new Intent(getSherlockActivity().getApplicationContext(),MainPaint.class),1);
                break;
            default:
                break;

        }

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap d = GlobalBitmap.img;

        // Log.d("hi",d.getWidth() + " - " + d.getHeight());
        // Log.d("hi","im here");

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            d.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img = bos.toByteArray();

            mImage = (ImageView) view.findViewById(R.id.ivPictureOfWord);
            mImage.setImageResource(R.drawable.checkmark);
        }catch (RuntimeException e){

        }

    }

}
