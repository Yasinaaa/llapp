package com.example.yasina.llapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.DAO.DBHelper;
import com.example.yasina.llapp.DAO.WordsPairDAO;

import java.util.ArrayList;

/**
 * Created by yasina on 10.03.15.
 */
public class DictionaryActivity extends SherlockFragmentActivity {

    /*public ArrayList<WordsPairDAO> dictionary = new ArrayList<WordsPairDAO>();
    private EditText firstLang, secondLang;
    private String newTableName;
    private static  String SQL_CREATE_TABLE_DICTIONARY1 = "CREATE TABLE ";
    private static  String SQL_CREATE_TABLE_DICTIONARY2 = "(" + DBHelper.COLUMN_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBHelper.COLUMN_FIRST_LANG + " TEXT NOT NULL, "
            + DBHelper.COLUMN_SECOND_LANG + " TEXT NOT NULL)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        firstLang = (EditText) findViewById(R.id.txt_firstLanguage);
        secondLang = (EditText) findViewById(R.id.txt_secondLanguage);
        newTableName = firstLang.getText().toString() + "_" + secondLang.getText().toString();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNewDictionaryTable:
                WordsPairDAO d = new WordsPairDAO(this,SQL_CREATE_TABLE_DICTIONARY1 + newTableName
                + SQL_CREATE_TABLE_DICTIONARY2);
                d.createTable();
                dictionary.add(d);
                break;

            default:
                break;

        }
    }*/
}
