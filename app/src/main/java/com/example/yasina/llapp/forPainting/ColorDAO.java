package com.example.yasina.llapp.forPainting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import com.example.yasina.llapp.DAO.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by yasina on 04.05.15.
 */
public class ColorDAO {


        public static final String TAG = "WordsPairDAO";

        private Context mContext;
        private SQLiteDatabase database;
        private DBHelper mDbHelper;
        private String[] mAllColumns = {"id","color"};
        private String TABLE_NAME = "colors";

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


        public ColorDAO(Context context) {
            mDbHelper = new DBHelper(context, TABLE_NAME);
            this.mContext = context;
            try {
                open();
            }
            catch(SQLException e) {
                Log.e(TAG, "SQLException on openning database " + e.getMessage());
                e.printStackTrace();
            }
        }


        public void open() throws SQLException {
            database = mDbHelper.getWritableDatabase();
            database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "color INTEGER)");

       /*     database.execSQL(insert_color + Color.parseColor(red) + insert_color2);
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
            database.execSQL(insert_color + Color.parseColor(black) + insert_color2);*/
        }

        public void close() {
            mDbHelper.close();
        }


        public void add(MyColor color){
            ContentValues values = new ContentValues();
            values.put("color", color.getColor());
            long insertId = database.insert(TABLE_NAME, null, values);
        }

        public void add(int color){
        ContentValues values = new ContentValues();
        values.put("color", color);
        long insertId = database.insert(TABLE_NAME, null, values);
        }

        public void delete(int color){
        database.execSQL("DELETE FROM colors WHERE color=" + color);
        }

        public void addListOfWords(ArrayList<MyColor> forTest){
            for(int i=0;i<forTest.size();i++){
                add(forTest.get(i));
            }
        }

        public void deleteDictionary(MyColor color) {
            System.out.println("the deleted dictionary has the id: " + color);
            database.delete(TABLE_NAME, "color" + " = " + color.getColor(), null);
        }

        public ArrayList<MyColor> getAll() {
            ArrayList<MyColor> listOfcolors = new ArrayList<MyColor>();
            Log.d("allColors","" + TABLE_NAME);
            Cursor cursor = database.query(TABLE_NAME,
                    mAllColumns, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    MyColor col = cursorTo(cursor);
                    listOfcolors.add(col);
                    cursor.moveToNext();
                }
                cursor.close();
            }
            //Log.d("size", "" + listOfcolors.size());
            return listOfcolors;
        }


        public MyColor getWordByColor(MyColor color) {
            Cursor cursor = database.query(TABLE_NAME, mAllColumns,
                    "color" + " = ?",
                    new String[] { String.valueOf(color.getColor()) }, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }

            MyColor col = cursorTo(cursor);
            return col;
        }

        private MyColor cursorTo(Cursor cursor) {
            MyColor color = new MyColor();
            color.setId(cursor.getInt(0));
            color.setColor(cursor.getInt(1));
            return color;
        }


        public void deletebyTableName(String name) {
            database.execSQL("DROP TABLE " + name);
        }

}
