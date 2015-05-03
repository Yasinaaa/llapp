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

    public static void setAlarms(Context context) {
        cancelAlarms(context);

        AlarmDAO dbHelper = new AlarmDAO(context);
        AlarmManager alarmManager;

        List<AlarmModel> alarms =  dbHelper.getAlarms();

        for (AlarmModel alarm : alarms) {
            if (alarm.isEnabled()) {

               PendingIntent pIntent = createPendingIntent(context, alarm);

                    Intent values = new Intent(context, AlarmScreen.class);
                    values.putExtra("alarm",alarm);
                 //   PendingIntent pIntent = PendingIntent.getActivity(context,
                   //     12345, values, PendingIntent.FLAG_CANCEL_CURRENT);
                        //PendingIntent.getBroadcast(context, 0, values, 0);
//
               // PendingIntent pIntent =  PendingIntent.getService(context, alarm.getId(), values, PendingIntent.FLAG_UPDATE_CURRENT);
                Log.d("alala", "set pIntent");

                Calendar calendarFROM = Calendar.getInstance();
                calendarFROM.setTimeInMillis(System.currentTimeMillis());

                calendarFROM.set(Calendar.DAY_OF_MONTH,alarm.getFromMonth());
                calendarFROM.set(Calendar.MONTH,alarm.getFromMonth());
                calendarFROM.set(Calendar.YEAR,alarm.getFromYear());
                calendarFROM.set(Calendar.HOUR_OF_DAY, alarm.getFromHours());
                calendarFROM.set(Calendar.MINUTE, alarm.getFromMinutes());
                calendarFROM.set(Calendar.SECOND, 00);
                int am_pm;
                if(alarm.getFromAM_PM().equals("am")) am_pm = 0;
                else am_pm = 1;
                calendarFROM.set(Calendar.AM_PM,am_pm);

                alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendarFROM.getTimeInMillis(), pIntent);


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

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
        Intent values = new Intent(context, AlarmService.class);
        values.putExtra("alarm",model);

        return PendingIntent.getService(context, model.getId(), values, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}


