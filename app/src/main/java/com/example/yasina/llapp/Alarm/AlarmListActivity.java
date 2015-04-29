package com.example.yasina.llapp.Alarm;


        import android.app.Activity;
        import android.app.AlarmManager;
        import android.app.AlertDialog;
        import android.app.ListActivity;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.DialogInterface.OnClickListener;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.Window;

        import com.example.yasina.llapp.R;

public class AlarmListActivity extends ListActivity {

    private AlarmListAdapter mAdapter;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    private Context mContext;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        name = getIntent().getExtras().getString("table name");
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_alarm_list);

        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());

        setListAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_new_alarm: {
                startAlarmDetailsActivity(-1);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mAdapter.setAlarms(dbHelper.getAlarms());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setAlarmEnabled(long id, boolean isEnabled) {
        //AlarmManagerHelper.cancelAlarms(this);


        Intent intent = new Intent(this, AlarmScreen.class);
        intent.putExtra("amount",3);
        //  intent.putExtra("current",cur);

        PendingIntent pendingIntent =   PendingIntent.getActivity(this,
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager a;

        a =(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        //Log.d("now time",SystemClock.elapsedRealtime() + "");


        AlarmModel model = dbHelper.getAlarm(id);
        model.isEnabled = isEnabled;
        dbHelper.updateAlarm(model);

        a.cancel(pendingIntent);

      //  AlarmManagerHelper.setAlarms(this);
    }

    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    public void deleteAlarm(final long id) {
        final long alarmId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent intent = new Intent();
        final PendingIntent pendingIntent =   PendingIntent.getActivity(this,12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        final AlarmManager a;

        a =(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        builder.setMessage("Please confirm")
                .setTitle("Delete set?")
                .setCancelable(true)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel Alarms
                       // AlarmManagerHelper.cancelAlarms(mContext);

                        a.cancel(pendingIntent);
                        //Delete alarm from DB by id
                        dbHelper.deleteAlarm(alarmId);
                        //Refresh the list of the alarms in the adaptor
                        mAdapter.setAlarms(dbHelper.getAlarms());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        //Set the alarms
                       // AlarmManagerHelper.setAlarms(mContext);
                    }
                }).show();
    }
}


