package com.example.yasina.llapp.Alarm;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmDetailsActivity extends Activity {

    private AlarmModel alarmDetails;
    private TextView txtToneSelection;

    private EditText et_fromHours, et_fromMinutes, et_toHours, et_toMinutes, et_repeatTime,
    et_fromDay, et_fromYear, et_toDay, et_toYear;

    private Spinner timeSize, timeMonth_from, timeMonth_to, timeAM_PM_from, timeAM_PM_to;
    private TextView theme_name;
    public  int cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        cur = 0;
        setContentView(R.layout.activity_details2);

        getActionBar().setTitle("Create New Alarm");
        getActionBar().setDisplayHomeAsUpEnabled(true);

       // timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
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
        timeSize = (Spinner) findViewById(R.id.spinnerAlarmTime);


       // edtName = (EditText) findViewById(R.id.alarm_details_name);
       /* chkWeekly = (CustomSwitch) findViewById(R.id.alarm_details_repeat_weekly);
        chkSunday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_sunday);
        chkMonday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_monday);
        chkTuesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_tuesday);
        chkWednesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_wednesday);
        chkThursday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_thursday);
        chkFriday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_friday);
        chkSaturday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_saturday);*/
        txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);

       // long id = getIntent().getExtras().getLong("id");

      /*  if (id == -1) {
            alarmDetails = new AlarmModel();
        } else {
            alarmDetails = dbHelper.getAlarm(id);*/

            //timePicker.setCurrentMinute(alarmDetails.timeMinute);
            //timePicker.setCurrentHour(alarmDetails.timeHour);
            alarmDetails = new AlarmModel();

           Calendar currentTime = new GregorianCalendar();
           et_fromHours.setText(currentTime.get(Calendar.HOUR)+"");
           et_fromMinutes.setText(currentTime.get(Calendar.MINUTE)+"");
           et_fromDay.setText(currentTime.get(Calendar.DAY_OF_MONTH)+"");
           et_fromYear.setText(currentTime.get(Calendar.YEAR)+"");
           int am_pm = currentTime.get(Calendar.AM_PM);
           Toast.makeText(this,am_pm+"",Toast.LENGTH_LONG).show();
           timeAM_PM_from.setSelection(am_pm);
           int month = currentTime.get(Calendar.MONTH);
           timeMonth_from.setSelection(month);

            et_toHours.setText(currentTime.get(Calendar.HOUR)+"");
            et_toMinutes.setText(currentTime.get(Calendar.MINUTE)+"");
            et_toDay.setText(currentTime.get(Calendar.DAY_OF_MONTH)+ "");
            et_toYear.setText(currentTime.get(Calendar.YEAR)+"");
            timeAM_PM_to.setSelection(am_pm);
            timeMonth_to.setSelection(month);

          /*  et_fromHours.setText(alarmDetails.timeHour);
            et_fromMinutes.setText(alarmDetails.timeHour);

            //edtName.setText(alarmDetails.name);

            chkWeekly.setChecked(alarmDetails.repeatWeekly);
            chkSunday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
            chkMonday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
            chkTuesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
            chkWednesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
            chkThursday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
            chkFriday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRDIAY));
            chkSaturday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));

            txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));*/


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
                    alarmDetails.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
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
                updateModelFromLayout();

                Intent intent = new Intent(this, AlarmScreen.class);
                intent.putExtra("amount",3);
                intent.putExtra("current",cur);

                PendingIntent pendingIntent =   PendingIntent.getActivity(this,
                        12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager a;

                a =(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
              //  AlarmManagerHelper.cancelAlarms(this);

                a.cancel(pendingIntent);


              /*  if (alarmDetails.id < 0) {
                    dbHelper.createAlarm(alarmDetails);
                } else {
                    dbHelper.updateAlarm(alarmDetails);
                }*/

                Calendar calendar = new GregorianCalendar();
                calendar.set(Calendar.HOUR_OF_DAY, alarmDetails.timeHour);
                calendar.set(Calendar.MINUTE, alarmDetails.timeMinute);
                calendar.set(Calendar.SECOND, 00);


                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                boolean alarmSet = false;

              /*  for(int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                    if (alarmDetails.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
                            !(dayOfWeek == nowDay && alarmDetails.timeHour < nowHour) &&
                            !(dayOfWeek == nowDay && alarmDetails.timeHour == nowHour && alarmDetails.timeMinute <= nowMinute)) {
                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                        a.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        //setAlarm(context, calendar, pIntent);
                        alarmSet = true;
                        break;
                    }
                }

                //Else check if it's earlier in the week
                if (!alarmSet) {
                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                        if (alarmDetails.getRepeatingDay(dayOfWeek - 1) && dayOfWeek <= nowDay && alarmDetails.repeatWeekly) {
                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                            calendar.add(Calendar.WEEK_OF_YEAR, 1);

                            // setAlarm(context, calendar, pIntent);
                            alarmSet = true;
                            break;
                        }
                    }
                }*/

                Log.d("now time", SystemClock.elapsedRealtime() + "");
                a.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //    AlarmManagerHelper.setAlarms(this);

                setResult(RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateModelFromLayout() {
        alarmDetails.timeMinute = Integer.parseInt(et_fromMinutes.getText().toString());
                //timePicker.getCurrentMinute().intValue();
        alarmDetails.timeHour = Integer.parseInt(et_fromHours.getText().toString());
       // //timePicker.getCurrentHour().intValue();
        //alarmDetails.name = edtName.getText().toString();
      /*  alarmDetails.repeatWeekly = chkWeekly.isChecked();
        alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, chkWednesday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, chkThursday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.FRDIAY, chkFriday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, chkSaturday.isChecked());*/
        alarmDetails.isEnabled = true;
    }

}

