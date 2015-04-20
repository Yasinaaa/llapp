package com.example.yasina.llapp.Alarm;

/**
 * Created by yasina on 15.04.15.
 */

        import android.app.Activity;
        import android.content.Intent;
        import android.media.RingtoneManager;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.TimePicker;

        import com.example.yasina.llapp.R;

public class AlarmDetailsActivity extends Activity implements AdapterView.OnItemClickListener, OnItemSelectedListener {

    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    public static final String TAG = "AlarmDetailsActivity";

    private AlarmModel alarmDetails;

 //   private TimePicker timePicker;
    private EditText edtName;
    private CustomSwitch chkWeekly;
    private CustomSwitch chkSunday;
    private CustomSwitch chkMonday;
    private CustomSwitch chkTuesday;
    private CustomSwitch chkWednesday;
    private CustomSwitch chkThursday;
    private CustomSwitch chkFriday;
    private CustomSwitch chkSaturday;
    private TextView txtToneSelection;

    private Spinner spinnerTime;
    private Spinner spinnerTimeName;
    private int clickedTime;
    private String clickedTimeName;
    private Integer[] types;
    private ArrayAdapter<String> adapterTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_details);

        getActionBar().setTitle("Create New Alarm");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerTime = (Spinner) findViewById(R.id.spinnerAlarmTime);
        spinnerTimeName = (Spinner) findViewById(R.id.spinnerAlarmTimeHourMinute);
        spinnerTimeName.setOnItemSelectedListener(this);


        types = new Integer[60];
        for (int i = 1; i < 60; i++) {
            types[i] = i;
        }


        //timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
        //edtName = (EditText) findViewById(R.id.alarm_details_name);
        chkWeekly = (CustomSwitch) findViewById(R.id.alarm_details_repeat_weekly);
        chkSunday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_sunday);
        chkMonday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_monday);
        chkTuesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_tuesday);
        chkWednesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_wednesday);
        chkThursday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_thursday);
        chkFriday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_friday);
        chkSaturday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_saturday);
        txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);

/*       long id = getIntent().getExtras().getLong("id");

       if (id == -1) {
            alarmDetails = new AlarmModel();
        } else {
            alarmDetails = dbHelper.getAlarm(id);

           // timePicker.setCurrentMinute(alarmDetails.timeMinute);
           // timePicker.setCurrentHour(alarmDetails.timeHour);

//            edtName.setText(alarmDetails.name);

            chkWeekly.setChecked(alarmDetails.repeatWeekly);
            chkSunday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
            chkMonday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
            chkTuesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
            chkWednesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
            chkThursday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
            chkFriday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRDIAY));
            chkSaturday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));

            txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
        }

        final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
        ringToneContainer.setOnClickListener(new OnClickListener() {

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

             //   AlarmManagerHelper.cancelAlarms(this);

                if (alarmDetails.id < 0) {
                    dbHelper.createAlarm(alarmDetails);
                } else {
                    dbHelper.updateAlarm(alarmDetails);
                }

               // AlarmManagerHelper.setAlarms(this);

                setResult(RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateModelFromLayout() {
        //alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
        //alarmDetails.timeHour = timePicker.getCurrentHour().intValue();
        alarmDetails.repeatMinutes = clickedTime;
     //   alarmDetails.name = edtName.getText().toString();
        alarmDetails.repeatWeekly = chkWeekly.isChecked();
        alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, chkWednesday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, chkThursday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.FRDIAY, chkFriday.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, chkSaturday.isChecked());
        alarmDetails.isEnabled = true;
    }
*/
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      /*  Spinner spinner = (Spinner) parent;
        int idSPinner = spinner.getId();
        int num = 0;
        switch(idSPinner){

            case R.id.spinnerAlarmTime:
                clickedTime = Integer.parseInt(spinnerTime.getSelectedItem().toString());
                Log.d(TAG, "clickedItem : " + clickedTime);
                break;

            case R.id.spinnerAlarmTimeHourMinute:
                clickedTimeName = spinnerTimeName.getSelectedItem().toString();
                if(clickedTimeName.equalsIgnoreCase("minutes")){
                    num = 60;
                }else{
                    num = 23;
                }
                types = new String[num];
                for(int i = 1; i<num; i++){
                    types[i] = i + "";
                }
                adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types);
                adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTime.setAdapter(adapterTime);
                spinnerTime.setSelection(1);
                Log.d(TAG, "clickedItemName : " + clickedTimeName);
                break;
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
                               long arg3) {

        parent.getItemAtPosition(pos);
        Log.d(TAG, " parent.getItemAtPosition(pos) " +  parent.getItemAtPosition(pos));
        if (pos == 0) {

            types = new Integer[23];
            for(int i = 1; i<23; i++){
                types[i] = i;
            }
           // ArrayAdapter<CharSequence> adapterTime2 = ArrayAdapter.createFromResource(this,R.array.timeHours_array
             //       ,android.R.layout.simple_spinner_item);
            ArrayAdapter<Integer> adapterTime2 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,types);
            adapterTime2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTimeName.setAdapter(adapterTime2);
            Log.d(TAG, "set adapter : timeName");
            spinnerTimeName.setSelection(1);
            Log.d(TAG, "set selection : timeName");

        } else if (pos == 1) {

            types = new Integer[60];
            for(int i = 1; i<60; i++){
                types[i] = i;
            }

            ArrayAdapter<Integer> adapterTime2 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,types);
            adapterTime2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTime.setAdapter(adapterTime2);
            Log.d(TAG, "set adapter : timeName");
            spinnerTime.setSelection(1);
            Log.d(TAG, "set selection : timeName");

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
