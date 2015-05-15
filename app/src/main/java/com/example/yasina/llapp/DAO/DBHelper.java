package com.example.yasina.llapp.DAO;

/**
 * Created by yasina on 10.03.15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    public static final String TABLE_DICTIONARY = "dictionary";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    private static final String DATABASE_NAME = "dictionaries.db";
    private static final int DATABASE_VERSION = 1;

    private String SQL_CREATE_TABLE_DICTIONARY = "CREATE TABLE " + TABLE_DICTIONARY + "("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_TYPE + " TEXT NOT NULL)";

    private String SQL_CREATE_COLOR_TABLE = "CREATE TABLE IF NOT EXISTS colors("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "color INTEGER)";


    String red = "#FFFF0000";
    String brawn = "#FF660000";
    String orange = "#FFFF6600";
    String yellow = "#FFFFCC00";
    String green = "#FF009900";
    String turquoise = "#FF009999";
    String blue = "#FF0000FF";
    String pink = "#FFFF6666";
    String purple = "#FF990099";
    String white = "#FFFFFFFF";
    String grey = "#FF787878";
    String black = "#FF000000";

    private String insert_color = "INSERT INTO colors(color) VALUES (";
    private String insert_color2 = ")";


    public DBHelper(Context context, String sql) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.SQL_CREATE_TABLE_DICTIONARY = sql;
    }
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(SQL_CREATE_TABLE_DICTIONARY);
        database.execSQL(SQL_CREATE_COLOR_TABLE);

        database.execSQL(insert_color + Color.parseColor(red) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(brawn) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(orange) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(yellow) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(green) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(turquoise) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(blue) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(pink) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(purple) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(white) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(grey) + insert_color2);
        database.execSQL(insert_color + Color.parseColor(black) + insert_color2);
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

