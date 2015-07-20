package com.example.yasina.llapp.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.yasina.llapp.Model.Words;

/**
 * Created by yasina on 10.03.15.
 */
public class WordsDAO {

        public static final String TAG = "WordsPairDAO";

        private Context mContext;
        private SQLiteDatabase mDatabase;
        private DBHelper mDbHelper;
        private String[] mAllColumns = {"id","firstLanguage","secondLanguage","picture","explanation"};
        private String TABLE_NAME = "";

        public WordsDAO(Context context, String TABLE_NAME) {
            mDbHelper = new DBHelper(context, TABLE_NAME);
            this.mContext = context;
            try {
                this.TABLE_NAME = TABLE_NAME;
                open();
            }
            catch(SQLException e) {
                Log.e(TAG, "SQLException on openning database " + e.getMessage());
                e.printStackTrace();
            }
        }

    public WordsDAO(Context context) {
        mDbHelper = new DBHelper(context, TABLE_NAME);
        this.mContext = context;
        try {
            mDatabase = mDbHelper.getWritableDatabase();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }


        public void open() throws SQLException {
            mDatabase = mDbHelper.getWritableDatabase();
            mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "firstLanguage TEXT NOT NULL, " +
                    "secondLanguage TEXT NOT NULL, " +
                    "picture BLOB NOT NULL, " +
                    "explanation TEXT NOT NULL)");
        }

        public void close() {
            mDbHelper.close();
        }

        public Words createWordsPair(String firstLang, String secondLang, byte[] picture, String explanation) {
            ContentValues values = new ContentValues();
            values.put("firstLanguage", firstLang);
            values.put("secondLanguage", secondLang);
            values.put("picture", picture);
            values.put("explanation", explanation);

            long insertId = mDatabase.insert(TABLE_NAME, null, values);
            Cursor cursor = mDatabase.query(TABLE_NAME,
                    mAllColumns, "id" + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            Words newWordPair = cursorToDictionary(cursor);
            cursor.close();
            return newWordPair;
        }

       public void add(Words words){
           ContentValues values = new ContentValues();
           values.put("firstLanguage", words.getFirstLang());
           values.put("secondLanguage", words.getSecondLang());
           values.put("picture", words.getImage());
           values.put("explanation", words.getExplanation());
           long insertId = mDatabase.insert(TABLE_NAME, null, values);
       }

      public void addListOfWords(ArrayList<Words> forTest){
          for(int i=0;i<forTest.size();i++){
              add(forTest.get(i));
          }
      }

        public void deleteDictionary(Words wordPair, String name) {
            long id = wordPair.getmId();
            System.out.println("the deleted dictionary has the id: " + id);
            mDatabase.delete(name, "id" + " = " + id, null);
        }

        public void deleteFromThemes(Words word){
            ArrayList<String> dirArray = new ArrayList<String>();
            Cursor c = mDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            while (c.moveToNext()) {
                String s = c.getString(0);
                if (s.equals("android_metadata")) {;
                    continue;
                } else {
                    if(s.contains("_theme")) {
                        try {
                            mDatabase.rawQuery("SELECT * FROM " + s + " WHERE " +
                                    "firstLanguage = \"" + word.getFirstLang() + "\", " +
                                    "secondLanguage = \"" + word.getSecondLang() + "\", " +
                                    "explanation = \"" + word.getExplanation() + "\"", null);
                        } catch (RuntimeException e) {

                        }
                    }
                }
            }
        }

        public ArrayList<Words> getAllDictionaries() {
            ArrayList<Words> listDictionaries = new ArrayList<Words>();
            Log.d("allDic","" + TABLE_NAME);
            Cursor cursor = mDatabase.query(TABLE_NAME,
                    mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Words wordPair = cursorToDictionary(cursor);
                listDictionaries.add(wordPair);
                Log.d("Add","" + wordPair.getFirstLang());
                cursor.moveToNext();
            }
            cursor.close();
        }

            Log.d("size","" + listDictionaries.size());
            return listDictionaries;

        }


    public Words getWordById(long id) {
        Cursor cursor = mDatabase.query(TABLE_NAME, mAllColumns,
                "id" + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Words wordPair = cursorToDictionary(cursor);
        return wordPair;
    }

        private Words cursorToDictionary(Cursor cursor) {
            Words wordPair = new Words();
            wordPair.setmId(cursor.getLong(0));
            wordPair.setFirstLang(cursor.getString(1));
            wordPair.setSecondLang(cursor.getString(2));
            wordPair.setImage(cursor.getBlob(3));
            wordPair.setExplanation(cursor.getString(4));
            return wordPair;
        }

    public void addWordToTheme(String theme, Words words){
        ContentValues values = new ContentValues();
        values.put("firstLanguage", words.getFirstLang());
        values.put("secondLanguage", words.getSecondLang());
        values.put("picture", words.getImage());
        values.put("explanation", words.getExplanation());
        long insertId = mDatabase.insert(theme, null, values);
    }

    public void deleteWordFromTheme(String theme, Words words){
        long id = words.getmId();
        mDatabase.delete(theme, "id" + " = " + id, null);
    }

    public ArrayList<String> allThemesTablesNames() {

        ArrayList<String> dirArray = new ArrayList<String>();
        Cursor c = mDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        while (c.moveToNext()) {
            String s = c.getString(0);
            if (s.equals("android_metadata")) {;
                continue;
            } else {
                if(s.contains("_theme")) {
                    String temp = s.replace("_theme","");
                    dirArray.add(temp);
                }
            }
        }
        return dirArray;
    }

    public void deletebyTableName(String name) {
        mDatabase.execSQL("DROP TABLE " + name);
    }
    }

