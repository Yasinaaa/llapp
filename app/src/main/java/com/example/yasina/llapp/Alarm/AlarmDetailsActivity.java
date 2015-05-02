package com.example.yasina.llapp.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.yasina.llapp.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmDetailsActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private AlarmModel alarmDetails;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

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
 //       requestWindowFeature(Window.FEATURE_ACTION_BAR);
        cur = 0;

        getActionBar().setTitle("Create New Alarm");
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 255, 255)));
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

           Calendar currentTime = new GregorianCalendar();
           et_fromHours.setText(currentTime.get(Calendar.HOUR)+"");
           et_fromMinutes.setText(currentTime.get(Calendar.MINUTE)+"");
           et_fromDay.setText(currentTime.get(Calendar.DAY_OF_MONTH)+"");
           et_fromYear.setText(currentTime.get(Calendar.YEAR)+"");
           int am_pm = currentTime.get(Calendar.AM_PM);
           timeAM_PM_from.setSelection(am_pm);
           int month = currentTime.get(Calendar.MONTH);
           timeMonth_from.setSelection(month);

            et_toHours.setText(currentTime.get(Calendar.HOUR)+"");
            et_toMinutes.setText(currentTime.get(Calendar.MINUTE)+"");
            et_toDay.setText(currentTime.get(Calendar.DAY_OF_MONTH)+ "");
            et_toYear.setText(currentTime.get(Calendar.YEAR)+"");
            timeAM_PM_to.setSelection(am_pm);
            timeMonth_to.setSelection(month);

            timeAM_PM_from_sleep.setSelection(1);
            timeAM_PM_to_sleep.setSelection(0);
            et_fromHours_sleep.setText("10");
            et_fromMinutes_sleep.setText("0");
            et_toHours_sleep.setText("8");
            et_toMinutes_sleep.setText("0");

            txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmTone).getTitle(this));


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
                    alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmTone).getTitle(this));
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
                finish();
                break;
            }
            case R.id.action_save_alarm_details: {

                int fromHours = Integer.parseInt(et_fromHours.getText().toString());
                int fromMinutes = Integer.parseInt(et_fromMinutes.getText().toString());
                int fromDay =  Integer.parseInt(et_fromDay.getText().toString());
                int fromYear =  Integer.parseInt(et_fromYear.getText().toString());

                Calendar calendarFrom = Calendar.getInstance();
                Log.d("AlarmScreen1","calendarFROM before set " + calendarFrom.toString());
                calendarFrom.set(Calendar.HOUR_OF_DAY, fromDay);
                calendarFrom.set(Calendar.MINUTE, fromMinutes);
                calendarFrom.set(Calendar.DAY_OF_MONTH, fromMonth);
                calendarFrom.set(Calendar.YEAR, fromYear);
                calendarFrom.set(Calendar.AM_PM, fromAM_PM);
                calendarFrom.set(Calendar.MONTH, fromMonth);
                Log.d("AlarmScreen1","calendarFROM after set " + calendarFrom.toString());

                int toHours = Integer.parseInt(et_toHours.getText().toString());
                int toMinutes = Integer.parseInt(et_toMinutes.getText().toString());
                int toDay = Integer.parseInt(et_toDay.getText().toString());
                int toYear = Integer.parseInt(et_toYear.getText().toString());
               // int toAM_PM = (Integer) timeAM_PM_to.getSelectedItem();
                //int toMonth = (Integer) timeMonth_to.getSelectedItem();
                Calendar calendarTo = Calendar.getInstance();
                Log.d("AlarmScreen1","calendarTo before set  " + calendarTo.toString());
                calendarTo.set(Calendar.HOUR_OF_DAY, toHours);
                calendarTo.set(Calendar.MINUTE, toMinutes);
                calendarTo.set(Calendar.DAY_OF_MONTH, toDay);
                calendarTo.set(Calendar.YEAR, toYear);
                calendarTo.set(Calendar.AM_PM, toAM_PM);
                calendarTo.set(Calendar.MONTH, toMonth);
                Log.d("AlarmScreen1","calendarTo after set  " + calendarTo.toString());

               // int fromAM_PM_sleep = (Integer) timeAM_PM_from_sleep.getSelectedItem();
               // int toAM_PM_sleep = (Integer) timeAM_PM_to_sleep.getSelectedItem();
                int fromHours_sleep = Integer.parseInt(et_fromHours_sleep.getText().toString());
                int fromMinutes_sleep = Integer.parseInt(et_fromMinutes_sleep.getText().toString());
                int toHours_sleep = Integer.parseInt(et_toHours_sleep.getText().toString());
                int toMinutes_sleep = Integer.parseInt(et_toMinutes_sleep.getText().toString());
                Calendar calendar_sleepFROM = Calendar.getInstance();
                Log.d("AlarmScreen1","calendar_sleepFROM before set  " + calendar_sleepFROM.toString());
                calendar_sleepFROM.set(Calendar.HOUR_OF_DAY, fromHours_sleep);
                calendar_sleepFROM.set(Calendar.MINUTE, fromMinutes_sleep);
                calendar_sleepFROM.set(Calendar.AM_PM, fromAM_PM_sleep);
                Log.d("AlarmScreen1","calendar_sleepFROM after set  " + calendar_sleepFROM.toString());

                Calendar calendar_sleepTo = Calendar.getInstance();
                Log.d("AlarmScreen1","calendar_sleepTO before set  " + calendar_sleepTo.toString());
                calendar_sleepTo.set(Calendar.HOUR_OF_DAY, toHours_sleep);
                calendar_sleepTo.set(Calendar.MINUTE, toMinutes_sleep);
                calendar_sleepTo.set(Calendar.AM_PM, toAM_PM);
                Log.d("AlarmScreen1","calendar_sleepTo after set  " + calendar_sleepTo.toString());

                int repeat = Integer.parseInt(et_repeatTime.getText().toString());
                if(rep_min_hour == 1) repeat = repeat*60;

                Intent intent = new Intent(this, AlarmScreen.class);

                try {
                    intent.putExtra("tone", alarmTone.toString());
                }catch (NullPointerException e ){

                    String button1String = "Set ringtone";
                    String button2String = "Cancel";

                    AlertDialog.Builder ad = new AlertDialog.Builder(this);
                    ad.setTitle("Mistake");
                    ad.setMessage("You forget to set ringtone for alarm.");
                    ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                              Intent i = new Intent(getApplicationContext(),AlarmDetailsActivity.class);
                              i.putExtra("table name",name);
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
                intent.putExtra("current",cur);
                intent.putExtra("endDate",calendarTo);
                intent.putExtra("sleepTime_from",calendar_sleepFROM);
                intent.putExtra("sleepTime_to",calendar_sleepTo);
                intent.putExtra("sleep",false);
                intent.putExtra("repeat",repeat);
                intent.putExtra("table name",name);

                PendingIntent pendingIntent =   PendingIntent.getActivity(this,
                        12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager a =(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                a.cancel(pendingIntent);
                Log.d("AlarmScreen1","cancel alarm becouce new alarm");

                a.set(AlarmManager.RTC_WAKEUP, calendarFrom.getTimeInMillis(), pendingIntent);
                Log.d("AlarmScreen1","set alarm " + calendarFrom.get(Calendar.HOUR) + " " + calendarFrom.get(Calendar.MINUTE)
                        + " " + calendarFrom.get(Calendar.AM_PM));
                finish();
            }
        }

       return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;

        switch (spinner.getId()){

            case R.id.spinner_repeat_min_hour:
                rep_min_hour = position;
                break;

            case R.id.spinner_fromMonth:
                fromMonth = position;
                break;

            case R.id.spinner_am_pm_From:
                fromAM_PM = position;
                break;

            case R.id.spinner_ToMonth:
                toMonth = position;
                break;

            case R.id.spinner_am_pm_To:
                toAM_PM = position;
                break;

            case R.id.spinner_am_pm_sleepFrom:
                fromAM_PM_sleep = position;
                break;

            case R.id.spinner_am_pm_sleepTO:
                toAM_PM_sleep = position;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

