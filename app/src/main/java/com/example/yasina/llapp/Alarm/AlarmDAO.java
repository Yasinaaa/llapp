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
            "repeat","repeatMin_Hour"};

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
                "fromAM_PM TEXT NOT NULL, " +

                "toDay INTEGER NOT NULL, " +
                "toMonth INTEGER NOT NULL, " +
                "toYear INTEGER NOT NULL, " +
                "toHours INTEGER, " +
                "toMinutes INTEGER, " +
                "toAM_PM TEXT NOT NULL, " +

                "fromSleepHours INTEGER, " +
                "fromSleepMinutes INTEGER, " +
                "fromSleep_AM_PM TEXT NOT NULL, " +

                "toSleepHours INTEGER, " +
                "toSleepMinutes INTEGER, " +
                "toSleep_AM_PM TEXT NOT NULL, " +
                "repeat INTEGER NOT NULL, " +
                "repeatMin_Hour TEXT NOT NULL)");
    }

    public void close() {
        mDbHelper.close();
    }

    public void set(String theme, Calendar fromDate, Calendar toDate, Calendar fromSleep, Calendar toSleep, int repeat, int repeatMin_Hour){
        ContentValues values = new ContentValues();
        String am_pm, am_pm2, am_pm3, am_pm4, am_pm5;
        String sql;
        values.put("themeName",theme);

        values.put("fromDay",fromDate.get(Calendar.DAY_OF_MONTH));
        values.put("fromMonth",fromDate.get(Calendar.MONTH));
        values.put("fromYear", fromDate.get(Calendar.YEAR));
        values.put("fromHours", fromDate.get(Calendar.HOUR));
        values.put("fromMinutes",fromDate.get(Calendar.MINUTE));
        if(fromDate.get(Calendar.AM_PM) == 0) am_pm = "am";
        else am_pm = "pm";
        values.put("fromAM_PM",am_pm);

        values.put("toDay",toDate.get(Calendar.DAY_OF_MONTH));
        values.put("toMonth",toDate.get(Calendar.MONTH));
        values.put("toYear",toDate.get(Calendar.YEAR));
        values.put("toHours", toDate.get(Calendar.HOUR));
        values.put("toMinutes",toDate.get(Calendar.MINUTE));
        if(toDate.get(Calendar.AM_PM) == 0) am_pm2 = "am";
        else am_pm2 = "pm";
        values.put("toAM_PM",am_pm2);

        values.put("fromSleepHours", fromSleep.get(Calendar.HOUR));
        values.put("fromSleepMinutes",fromSleep.get(Calendar.MINUTE));
        if(fromSleep.get(Calendar.AM_PM) == 0) am_pm3 = "am";
        else am_pm3 = "pm";
        values.put("fromSleep_AM_PM",am_pm3);

        values.put("toSleepHours", toSleep.get(Calendar.HOUR));
        values.put("toSleepMinutes",toSleep.get(Calendar.MINUTE));
        if(toSleep.get(Calendar.AM_PM) == 0) am_pm4 = "am";
        else am_pm4 = "pm";
        values.put("toSleep_AM_PM",am_pm4);

        values.put("repeat",repeat);
        if (repeatMin_Hour == 1) am_pm5 = "hour";
        else am_pm5 = "min";
        values.put("repeatMin_Hour",am_pm5);
       // long insertId = mDatabase.insert("alarmTable", null, values);
        sql = "INSERT INTO alarmTable(themeName," +
                "fromDay,fromMonth,fromYear,fromHours,fromMinutes,fromAM_PM," +
                "toDay,toMonth,toYear,toHours,toMinutes,toAM_PM," +
                "fromSleepHours,fromSleepMinutes,fromSleep_AM_PM," +
                "toSleepHours,toSleepMinutes, toSleep_AM_PM," +
                "repeat,repeatMin_Hour) " +
                "VALUES(" + "\"" + theme + "\"," +
                fromDate.get(Calendar.DAY_OF_MONTH) + "," + fromDate.get(Calendar.MONTH) + "," +fromDate.get(Calendar.YEAR) + "," + fromDate.get(Calendar.HOUR)  + "," + fromDate.get(Calendar.MINUTE) + ",\"" + am_pm + "\"," +
                toDate.get(Calendar.DAY_OF_MONTH)  + "," + toDate.get(Calendar.MONTH) + "," + toDate.get(Calendar.YEAR) + "," + toDate.get(Calendar.HOUR) + "," + toDate.get(Calendar.MINUTE) + ",\"" +am_pm2 + "\","+
                fromSleep.get(Calendar.HOUR) + "," + fromSleep.get(Calendar.MINUTE) + ",'" + am_pm3 + "'," +
                toSleep.get(Calendar.HOUR) + "," + toSleep.get(Calendar.MINUTE) + ",\"" + am_pm4 + "\""
                + "," + repeat + ",\"" + am_pm5 + "\")";
        mDatabase.execSQL(sql);

    }

    public AlarmModel get(int theme){

        String[] args={theme + ""};
        Cursor cursor = mDatabase.rawQuery("SELECT id,themeName,fromDay,fromMonth,fromYear,fromHours,fromMinutes,fromAM_PM,toDay,toMonth,toYear,toHours,toMinutes,toAM_PM,fromSleepHours,fromSleepMinutes,fromSleep_AM_PM,toSleepHours,toSleepMinutes, toSleep_AM_PM,repeat,repeatMin_Hour FROM alarmTable WHERE id=1",null);

        while (cursor.moveToNext()) {
            String s = cursor.getString(0);

        }

                /*mDatabase.query("alarmTable", mAllColumns,
                "id" + " = " + theme,
                new String[] { String.valueOf(theme) }, null, null, null);*/

        AlarmModel alarmModel = cursorTo(cursor);
        return alarmModel;
    }

    private AlarmModel cursorTo(Cursor cursor) {

        if (cursor != null) {
            cursor.moveToFirst();
            Log.d("aaaaaaaa","not zero");
        }else{
            Log.d("aaaaaaaa","zeroooo");
        }

        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setId(cursor.getInt(0));
        Log.d("aaaaaaaa","setId 0");
        alarmModel.setThemeName(cursor.getString(1));

        alarmModel.setFromDay(cursor.getInt(2));
        alarmModel.setFromMonth(cursor.getInt(3));
        alarmModel.setFromYear(cursor.getInt(4));
        alarmModel.setFromHours(cursor.getInt(5));
        alarmModel.setFromMinutes(cursor.getInt(6));
        alarmModel.setFromAM_PM(cursor.getString(7));

        alarmModel.setToDay(cursor.getInt(8));
        alarmModel.setToMonth(cursor.getInt(9));
        alarmModel.setToYear(cursor.getInt(10));
        alarmModel.setToHours(cursor.getInt(11));
        alarmModel.setToMinutes(cursor.getInt(12));
        alarmModel.setToAM_PM(cursor.getString(13));

        alarmModel.setFromSleepHours(cursor.getInt(14));
        alarmModel.setFromSleepMinutes(cursor.getInt(15));
        alarmModel.setFromSleep_AM_PM(cursor.getString(16));

        alarmModel.setToSleepHours(cursor.getInt(17));
        alarmModel.setToSleepMinutes(cursor.getInt(18));
        alarmModel.setToSleep_AM_PM(cursor.getString(19));
        alarmModel.setRepeat(cursor.getInt(20));
        Log.d("aaaaaaaa","set 20");
        alarmModel.setRepeatMin_Hour(cursor.getString(21));
        Log.d("aaaaaaaa","setId 21");

        return alarmModel;
    }

    public void delete(String theme){
        mDatabase.execSQL("DELETE FROM alarmTable WHERE themeName='" + theme + "'");
    }
}
