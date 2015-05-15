package com.example.yasina.llapp.Alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.example.yasina.llapp.DAO.DBHelper;
import com.example.yasina.llapp.Model.Words;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            "repeat","repeatMin_Hour","alarmTone","enabled"};

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
        mDatabase = mDbHelper.getReadableDatabase();

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
                "repeatMin_Hour TEXT NOT NULL, " +
                "alarmTone TEXT, " +
                "enabled INTEGER)" );
    }

    public void close() {
        mDbHelper.close();
    }


    public AlarmModel get(String theme){

        String[] args={theme + ""};
        AlarmModel alarmModel = null;
        Cursor cursor = mDatabase.rawQuery("SELECT id,themeName,fromDay,fromMonth,fromYear,fromHours,fromMinutes,fromAM_PM,toDay,toMonth,toYear,toHours,toMinutes,toAM_PM,fromSleepHours,fromSleepMinutes,fromSleep_AM_PM,toSleepHours,toSleepMinutes, toSleep_AM_PM,repeat,repeatMin_Hour,alarmTone,enabled FROM alarmTable WHERE themeName=\""+theme+"\"",null);

        if(cursor.moveToNext()){
            alarmModel = cursorTo(cursor);
            cursor.close();
        }
        return alarmModel;
    }

    public AlarmModel getByID(){

        AlarmModel alarmModel = null;
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM alarmTable WHERE id=1",null);
                //"SELECT id,themeName,fromDay,fromMonth,fromYear,fromHours,fromMinutes,fromAM_PM,toDay,toMonth,toYear,toHours,toMinutes,toAM_PM,fromSleepHours,fromSleepMinutes,fromSleep_AM_PM,toSleepHours,toSleepMinutes, toSleep_AM_PM,repeat,repeatMin_Hour,alarmTone,enabled FROM alarmTable WHERE id=1",null);
        //AlarmModel alarmModel = cursorTo(cursor);

        if(cursor.moveToNext()){
            alarmModel = cursorTo(cursor);
            cursor.close();
        }

        return alarmModel;
    }

    private AlarmModel cursorTo(Cursor cursor) {

        AlarmModel alarmModel = new AlarmModel();

        alarmModel.setId(cursor.getInt(0));
        Log.d("alala", "setId 0");
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
        alarmModel.setRepeatMin_Hour(cursor.getString(21));
        Log.d("alala", "setId 21");
        alarmModel.alarmTone = cursor.getString(22);
        Log.d("alala","set 22");
        alarmModel.setEnabled(cursor.getInt(23) == 0 ? false:true);
        Log.d("alala","set 23");

        return alarmModel;
    }

    public void delete(String theme){
        mDatabase.execSQL("DELETE FROM alarmTable WHERE themeName='" + theme + "'");
    }

    public List<AlarmModel> getAlarms(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String select = "SELECT * FROM alarmTable";

        Cursor cursor = db.rawQuery(select, null);

        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

     /*   if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AlarmModel alarmModel = cursorTo(cursor);
                alarmList.add(alarmModel);
                Log.d("Add","" + alarmModel.getThemeName());
                cursor.moveToNext();
            }*/
        while (cursor.moveToNext()) {
            alarmList.add(cursorTo(cursor));
        }

        if (!alarmList.isEmpty()) {
            cursor.close();
            return alarmList;
        }


        return  alarmList;
    }

    public void createAlarm(AlarmModel model) {
        ContentValues values = add(model);
         mDatabase.insert("alarmTable", null, values);
    }

    public void updateAlarm(AlarmModel model) {
        ContentValues values = add(model);
        mDatabase.update("alarmTable", values, "themeName" + " = ?", new String[] { String.valueOf(model.getThemeName()) });
    }

    private ContentValues add(AlarmModel alarmModel){
        ContentValues values = new ContentValues();

        values.put("themeName",alarmModel.getThemeName());
        values.put("fromDay",alarmModel.getFromDay());
        values.put("fromMonth",alarmModel.getFromMonth());
        values.put("fromYear",alarmModel.getFromYear() );
        values.put("fromHours", alarmModel.getFromHours());
        values.put("fromMinutes",alarmModel.getFromMinutes());
        values.put("fromAM_PM",alarmModel.getFromAM_PM());
        values.put("toDay",alarmModel.getToDay());
        values.put("toMonth",alarmModel.getToMonth());
        values.put("toYear",alarmModel.getToYear());
        values.put("toHours", alarmModel.getToHours());
        values.put("toMinutes",alarmModel.getToMinutes());
        values.put("toAM_PM",alarmModel.getToAM_PM());
        values.put("fromSleepHours", alarmModel.getFromSleepHours());
        values.put("fromSleepMinutes",alarmModel.getFromSleepMinutes());
        values.put("fromSleep_AM_PM",alarmModel.getFromSleep_AM_PM());
        values.put("toSleepHours", alarmModel.getToSleepHours());
        values.put("toSleepMinutes",alarmModel.getToSleepMinutes());
        values.put("toSleep_AM_PM",alarmModel.getToSleep_AM_PM());
        values.put("repeat",alarmModel.getRepeat());
        values.put("repeatMin_Hour",alarmModel.getRepeatMin_Hour());
        values.put("alarmTone",alarmModel.alarmTone.toString());
        values.put("enabled",alarmModel.isEnabled());
        Log.d("alala","add values of alarm");
        int al;
        if(alarmModel.isEnabled()) al = 1;
        else al = 0;

        // long insertId = mDatabase.insert("alarmTable", null, values);
       /* String sql = "INSERT INTO alarmTable(themeName," +
                "fromDay,fromMonth,fromYear,fromHours,fromMinutes,fromAM_PM," +
                "toDay,toMonth,toYear,toHours,toMinutes,toAM_PM," +
                "fromSleepHours,fromSleepMinutes,fromSleep_AM_PM," +
                "toSleepHours,toSleepMinutes, toSleep_AM_PM," +
                "repeat,repeatMin_Hour,alarmTone,enabled) " +
                "VALUES(" + "\"" + alarmModel.getThemeName() + "\","+
                alarmModel.getFromDay() + "," + alarmModel.getFromMonth() +
                "," + alarmModel.getFromYear() + "," + alarmModel.getFromHours()  + ","
                + alarmModel.getFromMinutes() + ",\"" + alarmModel.getFromAM_PM() + "\","
                +alarmModel.getToDay()  + "," + alarmModel.getToMonth() + ","
                + alarmModel.getToYear() + "," + alarmModel.getToHours() + ","
                + alarmModel.getToMinutes() + ",\"" + alarmModel.getToAM_PM() + "\","+
                alarmModel.getFromSleepHours() + "," + alarmModel.getFromSleepMinutes() + ",'" + alarmModel.getFromSleep_AM_PM() + "'," +
                alarmModel.getToSleepHours() + "," + alarmModel.getToSleepMinutes() + ",\"" + alarmModel.getToSleep_AM_PM() + "\""
                + "," + alarmModel.getRepeat() + ",\"" + alarmModel.getRepeatMin_Hour() + "\""
                +  ",\"" + alarmModel.alarmTone.toString() + "\"" +
                "," + al  +")";
        mDatabase.execSQL(sql);*/
        return values;
    }

}
