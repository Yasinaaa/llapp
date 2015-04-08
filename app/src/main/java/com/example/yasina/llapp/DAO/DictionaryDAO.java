package com.example.yasina.llapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasina.llapp.Model.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 11.03.15.
 */
public class DictionaryDAO {

    public static final String TAG = "DictionaryDAO";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {DBHelper.COLUMN_NAME_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_TYPE};
    private String sql = "rus_eng_wordsTable";
    private String SQL_CREATE_TABLE_DICTIONARY = "CREATE TABLE IF NOT EXISTS " + sql + " ("
            + DBHelper.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBHelper.COLUMN_NAME + " TEXT NOT NULL, "
            + DBHelper.COLUMN_TYPE + " TEXT NOT NULL)";

    public DictionaryDAO(Context context,String name) {
        mDbHelper = new DBHelper(context);
        sql = name;
        this.mContext = context;
        try {
            open();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DictionaryDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
        createTable();

       // mDatabase.execSQL("DROP DATABASE dictionaries" );
    }

    public void close() {
        mDbHelper.close();
    }

    public void createTable(){
        mDatabase.execSQL(SQL_CREATE_TABLE_DICTIONARY);
    }

    public Dictionary createDictionary(String dictionaryName, String type) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, dictionaryName);
        values.put(DBHelper.COLUMN_TYPE, type);

        long insertId = mDatabase.insert(DBHelper.TABLE_DICTIONARY, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DICTIONARY,
                mAllColumns, DBHelper.COLUMN_NAME_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Dictionary newDicitonary = cursorToDictionary(cursor);
        cursor.close();
        return newDicitonary;
    }

    public void deleteDictionary(Dictionary dictionary) {
        long id = dictionary.getmId();
        System.out.println("the deleted dictionary has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_DICTIONARY, DBHelper.COLUMN_NAME_ID + " = " + id, null);
    }

    public List<Dictionary> getAllDictionaries() {
        List<Dictionary> listDictionaries = new ArrayList<Dictionary>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_DICTIONARY,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dictionary dictionary = cursorToDictionary(cursor);
            listDictionaries.add(dictionary);
            cursor.moveToNext();
        }
        cursor.close();
        return listDictionaries;
    }


    public Dictionary getDicitonaryById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DICTIONARY,mAllColumns,
                DBHelper.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Dictionary dictionary = cursorToDictionary(cursor);
        return dictionary;
    }

    private Dictionary cursorToDictionary(Cursor cursor) {
        Dictionary dictionary = new Dictionary();
        dictionary.setmId(cursor.getLong(0));
        dictionary.setName(cursor.getString(1));
        dictionary.setType(cursor.getString(2));
        return dictionary;
    }

}


