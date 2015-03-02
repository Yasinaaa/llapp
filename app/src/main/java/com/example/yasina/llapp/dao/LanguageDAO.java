package com.example.yasina.llapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasina.llapp.model.ClassTable;
import com.example.yasina.llapp.model.LanguageTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 02.03.15.
 */
public class LanguageDAO {
    public static final String TAG = "LanguageDAO";
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DBHelper.COLUMN_LANGUAGE_ID, DBHelper.COLUMN_LANGUAGE_NAME};

    public LanguageDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public LanguageTable createLanguageTable(String languageName) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_LANGUAGE_NAME, languageName);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_LANGUAGE, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LANGUAGE, mAllColumns,
                DBHelper.COLUMN_LANGUAGE_ID + " = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();
        LanguageTable languageTable = cursorToLanguageTable(cursor);
        cursor.close();
        return languageTable;
    }

    public void deleteClassByLanguageID(LanguageTable languageTable) {
        long id = languageTable.getLanguageID();

    }

    public List<LanguageTable> getAllLanguageTable() {
        List<LanguageTable> listOfAllLanguages = new ArrayList<LanguageTable>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_LANGUAGE, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LanguageTable languageTable = cursorToLanguageTable(cursor);
                listOfAllLanguages.add(languageTable);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listOfAllLanguages;
    }

    public LanguageTable getLanguageNameByID(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LANGUAGE, mAllColumns,
                DBHelper.COLUMN_LANGUAGE_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        LanguageTable languageTable= cursorToLanguageTable(cursor);
        return languageTable;
    }

    protected LanguageTable cursorToLanguageTable(Cursor cursor) {
        LanguageTable languageTable = new LanguageTable();
        languageTable.setLanguageID(cursor.getLong(0));
        languageTable.setLanguageName(cursor.getString(1));
        return languageTable;
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}

