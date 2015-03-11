package com.example.yasina.llapp.DAO;

/**
 * Created by yasina on 10.03.15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    public static final String TABLE_DICTIONARY = "dictionary";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
   /* public static final String COLUMN_WORD_ID = "wordID";
    public static final String COLUMN_FIRST_LANG = "firstLang";
    public static final String COLUMN_SECOND_LANG = "secondLang";
    private String[] mAllColumns = { COLUMN_WORD_ID, COLUMN_FIRST_LANG, COLUMN_SECOND_LANG};*/


    private static final String DATABASE_NAME = "dictionaries.db";
    private static final int DATABASE_VERSION = 1;

    private static  String SQL_CREATE_TABLE_DICTIONARY = "CREATE TABLE " + TABLE_DICTIONARY + "("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_TYPE + " TEXT NOT NULL)";
           /* + COLUMN_FIRST_LANG + " TEXT NOT NULL, "
            + COLUMN_SECOND_LANG + " TEXT NOT NULL)";*/

    public DBHelper(Context context, String sql) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.SQL_CREATE_TABLE_DICTIONARY = sql;
       // context.deleteDatabase(DATABASE_NAME);
    }
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_DICTIONARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);

       db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);

       onCreate(db);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

  /*  public void addWord(WordsPair wordPair,String firstLang, String secondLang){
        Log.d("addWordsPair", wordPair.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_FIRST_LANG, firstLang);
        values.put(DBHelper.COLUMN_SECOND_LANG, secondLang);

        db.insert(TABLE_DICTIONARY, null, values);
        db.close();
    }

    public WordsPair getWord(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_DICTIONARY,mAllColumns,
                        " id = ?",
                        new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        WordsPair wordsPair = new WordsPair();
        wordsPair.setmId(Long.parseLong(cursor.getString(0)));
        wordsPair.setFirstLang(cursor.getString(1));
        wordsPair.setSecondLang(cursor.getString(2));

        Log.d("getWordsPair(" + id + ")", wordsPair.toString());
        return wordsPair;
    }

    public List<WordsPair> getAllWordsPairs() {
        List<WordsPair> wordsPairs = new LinkedList<WordsPair>();

        String query = "SELECT  * FROM " + TABLE_DICTIONARY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        WordsPair wordsP = null;
        if (cursor.moveToFirst()) {
            do {
                wordsP = new WordsPair();
                wordsP.setmId(Long.parseLong(cursor.getString(0)));
                wordsP.setFirstLang(cursor.getString(1));
                wordsP.setSecondLang(cursor.getString(2));
                wordsPairs.add(wordsP);
            } while (cursor.moveToNext());
        }

        Log.d("getAllwordsPairs()", wordsPairs.toString());
        return wordsPairs;
    }

    public int updateWordsPair(WordsPair wordsPair) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_LANG, wordsPair.getFirstLang());
        values.put(COLUMN_SECOND_LANG, wordsPair.getSecondLang());

        int i = db.update(TABLE_DICTIONARY,
                values,
                COLUMN_WORD_ID +" = ?",
                new String[] { String.valueOf(wordsPair.getmId()) });

        db.close();

        return i;

    }

    public void deleteWordsPair(WordsPair wordsPair) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DICTIONARY,
                COLUMN_WORD_ID+" = ?",
                new String[] { String.valueOf(wordsPair.getmId()) });

        db.close();
        Log.d("deleteWordsPair", wordsPair.toString());

    }*/
}

