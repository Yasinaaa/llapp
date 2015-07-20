package com.example.yasina.llapp.Alarm;

/**
 * Created by yasina on 03.05.15.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class AlarmManagerHelper extends BroadcastReceiver{



    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static PendingIntent pIntent;
    public static AlarmDAO dbHelper;

    public static void setAlarms(Context context) {
        cancelAlarms(context);

       dbHelper = new AlarmDAO(context);
        AlarmManager alarmManager;

        List<AlarmModel> alarms =  dbHelper.getAlarms();

        for (AlarmModel alarm : alarms) {
            if (alarm.isEnabled()) {

               pIntent = createPendingIntent(context, alarm);
               Log.d("alala", "set pIntent");

                Calendar calendarFROM = Calendar.getInstance();

                calendarFROM.set(Calendar.MONTH,alarm.getFromMonth());
                Log.d("time", calendarFROM.get(Calendar.MONTH) + " month");
                calendarFROM.set(Calendar.YEAR,alarm.getFromYear());
                Log.d("time", calendarFROM.get(Calendar.YEAR) + " year");

                int day = alarm.getFromDay();
                Log.d("time", day + " day ");
                calendarFROM.set(Calendar.DAY_OF_MONTH,day);

                calendarFROM.set(Calendar.HOUR_OF_DAY, alarm.getFromHours());
                Log.d("time", calendarFROM.get(Calendar.HOUR_OF_DAY) + " hours");
                calendarFROM.set(Calendar.MINUTE, alarm.getFromMinutes());
                Log.d("time", calendarFROM.get(Calendar.MINUTE) +  " minutes");
                calendarFROM.set(Calendar.SECOND, 00);

                int am_pm;
                if(alarm.getFromAM_PM().equals("am")) am_pm = 0;
                else am_pm = 1;
                calendarFROM.set(Calendar.AM_PM, am_pm);
                Log.d("time", calendarFROM.get(Calendar.AM_PM) + " am-pm");

                long time = calendarFROM.getTimeInMillis();
                alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Calendar c = Calendar.getInstance();
                if(c.getTimeInMillis() > time){
                    Log.d("time", "problems with time");
                    while (c.getTimeInMillis() > time){
                        time = time + 60*1000*alarm.getRepeat();
                    }
                    Log.d("time", "new time with repeat " + time + " in millis");
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, time, pIntent);
                dbHelper.close();

                Log.d("time", "calendarFROM.getTimeInMillis()" + calendarFROM.getTimeInMillis() + " in millis");
                Log.d("time", " " + System.currentTimeMillis());

            }
       }
    }


    public static void cancelAlarms(Context context) {
        AlarmDAO dbHelper = new AlarmDAO(context);

        List<AlarmModel> alarms =  dbHelper.getAlarms();

        if (alarms != null) {
            for (AlarmModel alarm : alarms) {
                if (alarm.isEnabled()) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);
                   // dbHelper.delete(alarm.getThemeName());
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                    dbHelper.close();
                }
            }
        }
    }

    public static PendingIntent createPendingIntent(Context context, AlarmModel model) {
      //  Intent values = new Intent(context, AlarmService.class);
       Intent values = new Intent(context, AlarmScreen.class);
         int cur = 0;
       // values.putExtra("alarm",model);

        values.putExtra("alarmTheme", model.getThemeName());
        values.putExtra("toDay",model.getToDay());
        values.putExtra("toMonth", model.getToMonth());
        values.putExtra("toYear",model.getToYear());
        values.putExtra("toHours",model.getToHours());
        values.putExtra("toMinutes",model.getToMinutes());
        int am_pm2;
        if(model.getToAM_PM().equals("am")) am_pm2 = 0;
        else am_pm2 = 1;
        values.putExtra("toAM_PM",am_pm2);
        values.putExtra("fromSleepH",model.getFromSleepHours());
        values.putExtra("fromSleepM",model.getFromSleepMinutes());

        values.putExtra("toSleepH",model.getToSleepHours());
        values.putExtra("toSleepM",model.getToSleepMinutes());

        values.putExtra("repeat",model.getRepeat());
     //   values.putExtra("repeatMin_H",model.getRepeatMin_Hour());
        values.putExtra("alarmTune",model.alarmTone);
        values.putExtra("sleep",false);

        if(model.getFromAM_PM().equals("am")) am_pm2 = 0;
        else am_pm2 = 1;
        values.putExtra("fromSleepAM_PM",am_pm2);

        int am_pm;
        if(model.getToSleep_AM_PM().equals("am")) am_pm = 0;
        else am_pm = 1;
        values.putExtra("toSleepAM_PM",am_pm);

        if(am_pm2==1 && am_pm==0) {
            values.putExtra("add day sleep",true);
        }

        values.putExtra("cur",cur);
       // values.putExtra("name",model.getThemeName());
        //values.putExtra("tune",model.alarmTone);
      //  return PendingIntent.getBroadcast(context,(int) model.getId(), values, PendingIntent.FLAG_UPDATE_CURRENT);

      PendingIntent peng =  PendingIntent.getActivity(context,12345, values, PendingIntent.FLAG_CANCEL_CURRENT);
        return peng;
      //  return PendingIntent.getService(context,(int) model.getId(), values, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}


