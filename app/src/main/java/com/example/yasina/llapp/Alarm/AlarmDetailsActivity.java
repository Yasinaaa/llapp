package com.example.yasina.llapp.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.DAO.DBHelper;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.R;
import com.example.yasina.llapp.Train.MenuTrainActivity;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmDetailsActivity extends Activity {

    private AlarmModel alarm;
    private TextView txtToneSelection;

    private EditText et_fromHours, et_fromMinutes, et_toHours, et_toMinutes, et_repeatTime,
    et_fromDay, et_fromYear, et_toDay, et_toYear, et_fromHours_sleep, et_fromMinutes_sleep, et_toHours_sleep, et_toMinutes_sleep;

    private Spinner timeMonth_from, timeMonth_to, timeAM_PM_from, timeAM_PM_to, timeAM_PM_from_sleep, timeAM_PM_to_sleep,
            repeat_min_hour;
    private TextView theme_name;
    private int cur;
    private String name;
    private Uri alarmTone;
    private int fromAM_PM,  fromMonth, toAM_PM, toMonth, fromAM_PM_sleep, toAM_PM_sleep, rep_min_hour;
    private boolean old_alarm = false;
    private AlarmDAO alarmDAO;
    private boolean upd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        alarmDAO = new AlarmDAO(this);

        try{
            theme_name = (TextView) findViewById(R.id.textView_themeName_Alarm);
            name = getIntent().getExtras().getString("table name");
            String temp = name.replace("_theme","");
            theme_name.setText(temp);
        }catch (Exception e){
            setContentView(R.layout.empty);

            String button1String = "Create dictionary";
            String button2String = "Cancel";

            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Mistake");
            ad.setMessage("You don't have any themes of words. Please create new one for this.");
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                }
            });
            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    finish();
                }
            });
            ad.setCancelable(true);
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {

                }
            });
            AlertDialog alert = ad.create();
            alert.show();
        }
        try{
            upd = getIntent().getExtras().getBoolean("lets go");
            alarm = alarmDAO.get(name);
            txtToneSelection.setText(RingtoneManager.getRingtone(this, Uri.parse(alarm.alarmTone)).getTitle(this));
            old_alarm = true;
            DBHelper b = new DBHelper(this);
            SQLiteDatabase db = b.getWritableDatabase();
            db.execSQL("DROP TABLE alarmTable");
            Log.d("alala","old alarm");

        }catch (RuntimeException e){
            upd = false;
            DBHelper b = new DBHelper(this);
            SQLiteDatabase db = b.getWritableDatabase();
            db.execSQL("DROP TABLE alarmTable");
            Log.d("drop","i'm droped table");


            alarmDAO = new AlarmDAO(this);
            alarm = new AlarmModel();
            alarm.setThemeName(name);
            old_alarm = false;
            Log.d("alala","new alarm");
        }

    /*    try{
            alarm = alarmDAO.get(name);
            txtToneSelection.setText(RingtoneManager.getRingtone(this, Uri.parse(alarm.alarmTone)).getTitle(this));
            old_alarm = true;
            DBHelper b = new DBHelper(this);
            SQLiteDatabase db = b.getWritableDatabase();
            db.execSQL("DROP TABLE alarmTable");
            Log.d("alala","old alarm");

        }catch (RuntimeException e){
           /* DBHelper b = new DBHelper(this);
            SQLiteDatabase db = b.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS alarmTable");*/

          /*  alarmDAO = new AlarmDAO(this);
            alarm = new AlarmModel();
            alarm.setThemeName(name);
            old_alarm = false;
            Log.d("alala","new alarm");

        }*/
        cur = 0;

        getActionBar().setTitle("Create New Alarm");
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        repeat_min_hour = (Spinner) findViewById(R.id.spinner_repeat_min_hour);
        et_fromHours = (EditText) findViewById(R.id.editText_hours);
        et_fromMinutes = (EditText) findViewById(R.id.editText_minutes);
        et_toHours = (EditText) findViewById(R.id.editText_hours_TO);
        et_toMinutes = (EditText) findViewById(R.id.editText_minutes_TO);
        et_repeatTime = (EditText) findViewById(R.id.editText_repeat);
        et_fromDay = (EditText) findViewById(R.id.editText_fromDay);
        timeMonth_from = (Spinner) findViewById(R.id.spinner_fromMonth);
        timeAM_PM_from = (Spinner) findViewById(R.id.spinner_am_pm_From);
        et_fromYear  = (EditText) findViewById(R.id.editText_fromYear);
        et_toDay  = (EditText) findViewById(R.id.editText_ToDay);
        timeMonth_to = (Spinner) findViewById(R.id.spinner_ToMonth);
        et_toYear  = (EditText) findViewById(R.id.editText_ToYear);
        timeAM_PM_to = (Spinner) findViewById(R.id.spinner_am_pm_To);
        timeAM_PM_from_sleep =  (Spinner) findViewById(R.id.spinner_am_pm_sleepFrom);
        timeAM_PM_to_sleep = (Spinner) findViewById(R.id.spinner_am_pm_sleepTO);
        et_fromHours_sleep = (EditText) findViewById(R.id.editText_hours_sleepFrom);
        et_fromMinutes_sleep = (EditText) findViewById(R.id.editText_minutes_sleepFrom);
        et_toHours_sleep = (EditText) findViewById(R.id.editText_hours_sleepTO);
        et_toMinutes_sleep = (EditText) findViewById(R.id.editText_minutes_sleepTO);
        txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);

        if(old_alarm){
            et_fromHours.setText(alarm.getFromHours());
            et_fromMinutes.setText(alarm.getFromMinutes());
            et_fromDay.setText(alarm.getFromDay());
            et_fromYear.setText(alarm.getFromYear());
            if(alarm.getFromAM_PM().equals("am"))
            timeAM_PM_from.setSelection(0);
            else  timeAM_PM_from.setSelection(1);
            timeMonth_from.setSelection(alarm.getFromMonth());

            et_toHours.setText(alarm.getToHours());
            et_toMinutes.setText(alarm.getToMinutes());
            et_toDay.setText(alarm.getToDay());
            et_toYear.setText(alarm.getToYear());
            if(alarm.getToAM_PM().equals("am"))
                timeAM_PM_to.setSelection(0);
            else  timeAM_PM_to.setSelection(1);
            timeMonth_to.setSelection(alarm.getToMonth());

            if(alarm.getFromSleep_AM_PM().equals("am"))
                timeAM_PM_from_sleep.setSelection(0);
            else   timeAM_PM_from_sleep.setSelection(1);

            if(alarm.getToSleep_AM_PM().equals("am"))
                timeAM_PM_to_sleep.setSelection(0);
            else   timeAM_PM_to_sleep.setSelection(1);

            et_fromHours_sleep.setText(alarm.getFromSleepHours());
            et_fromMinutes_sleep.setText(alarm.getFromSleepMinutes());
            et_toHours_sleep.setText(alarm.getToSleepHours());
            et_toMinutes_sleep.setText(alarm.getToSleepMinutes());
           // alarm.alarmTone = "content://media/internal/audio/media/177";
        }else {
            Calendar currentTime = new GregorianCalendar();
            et_fromHours.setText(currentTime.get(Calendar.HOUR) + "");
            et_fromMinutes.setText(currentTime.get(Calendar.MINUTE) + "");
            et_fromDay.setText(currentTime.get(Calendar.DAY_OF_MONTH) + "");
            et_fromYear.setText(currentTime.get(Calendar.YEAR) + "");
            int am_pm = currentTime.get(Calendar.AM_PM);
            timeAM_PM_from.setSelection(am_pm);
            int month = currentTime.get(Calendar.MONTH);
            timeMonth_from.setSelection(month);

            et_toHours.setText(currentTime.get(Calendar.HOUR) + "");
            et_toMinutes.setText(currentTime.get(Calendar.MINUTE) + "");
            et_toDay.setText(currentTime.get(Calendar.DAY_OF_MONTH) + "");
            et_toYear.setText(currentTime.get(Calendar.YEAR) + "");
            timeAM_PM_to.setSelection(am_pm);
            timeMonth_to.setSelection(month);

            timeAM_PM_from_sleep.setSelection(1);
            timeAM_PM_to_sleep.setSelection(0);
            et_fromHours_sleep.setText("10");
            et_fromMinutes_sleep.setText("0");
            et_toHours_sleep.setText("8");
            et_toMinutes_sleep.setText("0");
            alarm.alarmTone = "content://media/internal/audio/media/177";
        }

        final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
        ringToneContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent , 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {

                    try {
                        alarm.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI).toString();
                        txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmTone).getTitle(this));
                       // Log.d("l", data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI).toString());
                    }catch (RuntimeException e){
                        alarm.alarmTone = "content://media/internal/audio/media/177";
                    }

                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                startActivity(new Intent(getApplicationContext(),MenuTrainActivity.class));
                break;
            }
            case R.id.action_save_alarm_details: {
                 setAlarmParameters();

                 AlarmManagerHelper.cancelAlarms(this);

                if(old_alarm) {
                    alarmDAO.updateAlarm(alarm);
                    Log.d("alala","update old alarm");
                }
                else {
                    alarmDAO.createAlarm(alarm);
                    Log.d("alala","create new alarm");
                }
                AlarmManagerHelper.setAlarms(this);
                alarmDAO.close();
                setResult(RESULT_OK);
                //finish();
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        }

       return super.onOptionsItemSelected(item);
    }


    private void setAlarmParameters(){

        int fromHours = Integer.parseInt(et_fromHours.getText().toString());
        int fromMinutes = Integer.parseInt(et_fromMinutes.getText().toString());
        int fromDay =  Integer.parseInt(et_fromDay.getText().toString());
        int fromYear =  Integer.parseInt(et_fromYear.getText().toString());
        String fromAM_PM1 = timeAM_PM_from.getSelectedItem().toString();


        alarm.setFromHours(fromHours);
        alarm.setFromMinutes(fromMinutes);
        alarm.setFromDay(fromDay);
        alarm.setFromYear(fromYear);
        alarm.setFromAM_PM(fromAM_PM1);

        int toHours = Integer.parseInt(et_toHours.getText().toString());
        int toMinutes = Integer.parseInt(et_toMinutes.getText().toString());
        int toDay = Integer.parseInt(et_toDay.getText().toString());
        int toYear = Integer.parseInt(et_toYear.getText().toString());
        String toAM_PM1 = timeAM_PM_to.getSelectedItem().toString();

        int fromMonth = timeMonth_from.getSelectedItemPosition();
        alarm.setFromMonth(fromMonth);
        Log.d("alala","fromMonth  " + fromMonth);

        int toMonth = timeMonth_to.getSelectedItemPosition();
        alarm.setToMonth(toMonth);
        Log.d("alala","toMonth  " + toMonth);

        alarm.setToHours(toHours);
        alarm.setToMinutes(toMinutes);
        alarm.setToDay(toDay);
        alarm.setToYear(toYear);
        alarm.setToAM_PM(toAM_PM1);

        int fromHours_sleep = Integer.parseInt(et_fromHours_sleep.getText().toString());
        int fromMinutes_sleep = Integer.parseInt(et_fromMinutes_sleep.getText().toString());
        String fromAM_PM_sleep = timeAM_PM_from_sleep.getSelectedItem().toString();
        int toHours_sleep = Integer.parseInt(et_toHours_sleep.getText().toString());
        int toMinutes_sleep = Integer.parseInt(et_toMinutes_sleep.getText().toString());
        String toAM_PM_sleep = timeAM_PM_to_sleep.getSelectedItem().toString();

        alarm.setFromSleepHours(fromHours_sleep);
        alarm.setFromSleepMinutes(fromMinutes_sleep);
        alarm.setFromSleep_AM_PM(fromAM_PM_sleep);
        alarm.setToSleepHours(toHours_sleep);
        alarm.setToSleepMinutes(toMinutes_sleep);
        alarm.setToSleep_AM_PM(toAM_PM_sleep);

        int repeat = Integer.parseInt(et_repeatTime.getText().toString());
        if(rep_min_hour == 1) repeat = repeat*60;
        alarm.setRepeat(repeat);
        String rep = repeat_min_hour.getSelectedItem().toString();
        alarm.setRepeatMin_Hour(rep);

        alarm.setEnabled(true);

    }
}

