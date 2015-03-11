package com.example.yasina.llapp.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasina.llapp.Model.WordsPair;

/**
 * Created by yasina on 10.03.15.
 */
public class WordsPairDAO {

        public static final String TAG = "WordsPairDAO";

        private Context mContext;
        private SQLiteDatabase mDatabase;
        private DBHelper mDbHelper;
        private String[] mAllColumns = {"id","firstLanguage","secondLanguage"};
        private String TABLE_NAME = "";

        public WordsPairDAO(Context context, String TABLE_NAME) {
            mDbHelper = new DBHelper(context, TABLE_NAME);
            this.mContext = context;
            try {
                open();
                this.TABLE_NAME = TABLE_NAME;
            }
            catch(SQLException e) {
                Log.e(TAG, "SQLException on openning database " + e.getMessage());
                e.printStackTrace();
            }
        }

        public WordsPairDAO(Context context) {
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
            mDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, firstLanguage TEXT NOT NULL, secondLanguage TEXT NOT NULL)");
        }

        public void close() {
            mDbHelper.close();
        }

        public WordsPair createWordsPair(String firstLang, String secondLang) {
            ContentValues values = new ContentValues();
            values.put("firstLanguage", firstLang);
            values.put("secondLanguage", secondLang);

            long insertId = mDatabase.insert(TABLE_NAME, null, values);
            Cursor cursor = mDatabase.query(TABLE_NAME,
                    mAllColumns, "id" + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            WordsPair newWordPair = cursorToDictionary(cursor);
            cursor.close();
            return newWordPair;
        }

        public void deleteDictionary(WordsPair wordPair) {
            long id = wordPair.getmId();
            System.out.println("the deleted dictionary has the id: " + id);
            mDatabase.delete(TABLE_NAME, "id" + " = " + id, null);
        }

        public List<WordsPair> getAllDictionaries() {
            List<WordsPair> listDictionaries = new ArrayList<WordsPair>();

            Cursor cursor = mDatabase.query(TABLE_NAME,
                    mAllColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                WordsPair wordPair = cursorToDictionary(cursor);
                listDictionaries.add(wordPair);
                cursor.moveToNext();
            }
            cursor.close();
            return listDictionaries;
        }


    public WordsPair getWordById(long id) {
        Cursor cursor = mDatabase.query(TABLE_NAME, mAllColumns,
                "id" + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        WordsPair wordPair = cursorToDictionary(cursor);
        return wordPair;
    }

        private WordsPair cursorToDictionary(Cursor cursor) {
            WordsPair wordPair = new WordsPair();
            wordPair.setmId(cursor.getLong(0));
            wordPair.setFirstLang(cursor.getString(1));
            wordPair.setSecondLang(cursor.getString(2));
            return wordPair;
        }

    }

