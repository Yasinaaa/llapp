package com.example.yasina.llapp.Alarm;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.yasina.llapp.DAO.DBHelper;

/**
 * Created by yasina on 01.05.15.
 */
public class AlarmDAO {
    public static final String TAG = "AlarmDAO";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {"id","themeName","toDay","toMonth","toYear","toHours","toMinutes","sleepHours","sleepMinutes"};

    public AlarmDAO(Context context) {
        mDbHelper = new DBHelper(context);
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
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS alarmTable("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "themeName TEXT NOT NULL, " +
                "toDay INTEGER NOT NULL, " +
                "toMonth INTEGER NOT NULL, " +
                "toYear INTEGER NOT NULL, " +
                "toHours INTEGER, " +
                "toMinutes INTEGER, " +

                "explanation TEXT NOT NULL)");
        /*
        "id","themeName","toDay","toMonth","toYear","toHours","toMinutes","sleepHours","sleepMinutes"
         */
    }

    public void close() {
        mDbHelper.close();
    }
}
