package com.example.yasina.llapp.Alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.yasina.llapp.DAO.DBHelper;
import com.example.yasina.llapp.Model.Words;

import java.util.Calendar;

/**
 * Created by yasina on 01.05.15.
 */
public class AlarmDAO {
    public static final String TAG = "AlarmDAOspecial";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {"id","themeName",
            "fromDay","fromMonth","fromYear","fromHours","fromMinutes","fromAM_PM",
            "toDay","toMonth","toYear","toHours","toMinutes","toAM_PM",
            "fromSleepHours","fromSleepMinutes","fromSleep_AM_PM",
            "toSleepHours","toSleepMinutes","toSleep_AM_PM",
            "repeat"};

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
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS alarmTable("  + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "themeName TEXT NOT NULL, " +

                "fromDay INTEGER NOT NULL, " +
                "fromMonth INTEGER NOT NULL, " +
                "fromYear INTEGER NOT NULL, " +
                "fromHours INTEGER, " +
                "fromMinutes INTEGER, " +
                "fromAM_PM BOOL, " +

                "toDay INTEGER NOT NULL, " +
                "toMonth INTEGER NOT NULL, " +
                "toYear INTEGER NOT NULL, " +
                "toHours INTEGER, " +
                "toMinutes INTEGER, " +
                "toAM_PM INTEGER, " +

                "fromSleepHours INTEGER, " +
                "fromSleepMinutes INTEGER, " +
                "fromSleep_AM_PM INTEGER" +

                "toSleepHours INTEGER, " +
                "toSleepMinutes INTEGER, " +
                "toSleep_AM_PM INTEGER" +
                "repeat INTEGER NOT NULL)");
    }

    public void close() {
        mDbHelper.close();
    }

    public void set(String theme, Calendar fromDate, Calendar toDate, Calendar fromSleep, Calendar toSleep, int repeat){
        ContentValues values = new ContentValues();

        values.put("themeName",theme);

        values.put("fromDay",fromDate.get(Calendar.DAY_OF_MONTH));
        values.put("fromMonth",fromDate.get(Calendar.MONTH));
        values.put("fromYear", fromDate.get(Calendar.YEAR));
        values.put("fromHours", fromDate.get(Calendar.HOUR));
        values.put("fromMinutes",fromDate.get(Calendar.MINUTE));
        values.put("fromAM_PM", fromDate.get(Calendar.AM_PM));

        values.put("toDay",toDate.get(Calendar.DAY_OF_MONTH));
        values.put("toMonth",toDate.get(Calendar.MONTH));
        values.put("toYear",toDate.get(Calendar.YEAR));
        values.put("toHours", toDate.get(Calendar.HOUR));
        values.put("toMinutes",toDate.get(Calendar.MINUTE));
        values.put("toAM_PM",toDate.get(Calendar.AM_PM));

        values.put("fromSleepHours", fromSleep.get(Calendar.HOUR));
        values.put("fromSleepMinutes",fromSleep.get(Calendar.MINUTE));
        values.put("fromSleep_AM_PM",fromSleep.get(Calendar.AM_PM));

        values.put("toSleepHours", toSleep.get(Calendar.HOUR));
        values.put("toSleepMinutes",toSleep.get(Calendar.MINUTE));
        values.put("toSleep_AM_PM",toSleep.get(Calendar.AM_PM));

        values.put("repeat",repeat);
        long insertId = mDatabase.insert("alarmTable", null, values);
    }

    public AlarmModel get(String theme){
        Cursor cursor = mDatabase.query("alarmTable", mAllColumns,
                "themeName" + " = ?",
                new String[] { String.valueOf(theme) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        AlarmModel alarmModel = cursorTo(cursor);
        return alarmModel;
    }

    private AlarmModel cursorTo(Cursor cursor) {
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setId(cursor.getInt(0));
        alarmModel.setThemeName(cursor.getString(1));

        alarmModel.setFromDay(cursor.getInt(2));
        alarmModel.setFromMonth(cursor.getInt(3));
        alarmModel.setFromYear(cursor.getInt(4));
        alarmModel.setFromHours(cursor.getInt(5));
        alarmModel.setFromMinutes(cursor.getInt(6));
        alarmModel.setFromAM_PM(cursor.getInt(7));

        alarmModel.setToDay(cursor.getInt(8));
        alarmModel.setToMonth(cursor.getInt(9));
        alarmModel.setToYear(cursor.getInt(10));
        alarmModel.setToHours(cursor.getInt(11));
        alarmModel.setToMinutes(cursor.getInt(12));
        alarmModel.setToAM_PM(cursor.getInt(13));

        alarmModel.setFromSleepHours(cursor.getInt(14));
        alarmModel.setFromSleepMinutes(cursor.getInt(15));
        alarmModel.setFromSleep_AM_PM(cursor.getInt(16));

        alarmModel.setToSleepHours(cursor.getInt(17));
        alarmModel.setToSleepMinutes(cursor.getInt(18));
        alarmModel.setToSleep_AM_PM(cursor.getInt(19));
        alarmModel.setRepeat(cursor.getInt(20));

        return alarmModel;
    }

    public void delete(String theme){
        mDatabase.execSQL("DELETE FROM alarmTable WHERE themeName='" + theme + "'");
    }
}
