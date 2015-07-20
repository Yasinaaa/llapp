package com.example.yasina.llapp.Alarm;

        import android.app.Activity;
        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.PowerManager;
        import android.os.PowerManager.WakeLock;
        import android.os.SystemClock;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.example.yasina.llapp.DAO.WordsDAO;
        import com.example.yasina.llapp.Model.Words;
        import com.example.yasina.llapp.R;
        import java.util.ArrayList;
        import java.util.Calendar;

public class AlarmScreen extends Activity {

    public final String TAG = this.getClass().getSimpleName() + "1";

    private WakeLock mWakeLock;
    private MediaPlayer mPlayer;
    private String name;
    private WordsDAO themeWordsDAO;
    private ArrayList<Words> words;
    private Words alarmWord;
    private TextView text1, text2, text3;
    private ImageView im1;
    private int repeat, toAM_PM, fromSleep_AM_PM, toSleepAM_PM;
    private static final int WAKELOCK_TIMEOUT = 60 * 1000;
    private String tone;
    private int size;
    private boolean addDaySleep, sleep;
    private boolean cancel = false;
    private AlarmModel model;
    private  AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alarm_screen);

        model = new AlarmModel();
        sleep = getIntent().getExtras().getBoolean("sleep");
        model.setToDay(getIntent().getExtras().getInt("toDay"));
        model.setToMonth(getIntent().getExtras().getInt("toMonth"));
        model.setToYear(getIntent().getExtras().getInt("toYear"));
        model.setToHours(getIntent().getExtras().getInt("toHours"));
        model.setToMinutes(getIntent().getExtras().getInt("toMinutes"));
        toAM_PM = getIntent().getExtras().getInt("toAM_PM");
        model.setFromSleepHours(getIntent().getExtras().getInt("fromSleepH"));
        model.setFromSleepMinutes(getIntent().getExtras().getInt("fromSleepM"));
        fromSleep_AM_PM = getIntent().getExtras().getInt("fromSleepAM_PM");
        model.setToHours(getIntent().getExtras().getInt("toSleepH"));
        model.setToMinutes(getIntent().getExtras().getInt("toSleepM"));
        toSleepAM_PM = getIntent().getExtras().getInt("toSleepAM_PM");
        model.setRepeat(getIntent().getExtras().getInt("repeat"));
        model.alarmTone = getIntent().getExtras().getString("alarmTune");
        addDaySleep = getIntent().getExtras().getBoolean("add day sleep");

        Calendar calendarTO = Calendar.getInstance();
        calendarTO.set(Calendar.DAY_OF_MONTH, getIntent().getExtras().getInt("toDay"));
        calendarTO.set(Calendar.MONTH, getIntent().getExtras().getInt("toMonth"));
        calendarTO.set(Calendar.YEAR, getIntent().getExtras().getInt("toYear"));
        calendarTO.set(Calendar.HOUR, getIntent().getExtras().getInt("toHours"));
        calendarTO.set(Calendar.MINUTE, getIntent().getExtras().getInt("toMinutes"));
        calendarTO.set(Calendar.AM_PM, getIntent().getExtras().getInt("toAM_PM"));
        calendarTO.set(Calendar.SECOND, 00);

        Calendar calendarSleepFROM = Calendar.getInstance();
        calendarSleepFROM.set(Calendar.HOUR, getIntent().getExtras().getInt("fromSleepH"));
        calendarSleepFROM.set(Calendar.MINUTE, getIntent().getExtras().getInt("fromSleepM"));
        calendarSleepFROM.set(Calendar.AM_PM, getIntent().getExtras().getInt("fromSleepAM_PM"));
        calendarSleepFROM.set(Calendar.SECOND, 00);

        Calendar calendarSleepTO = Calendar.getInstance();
        if (addDaySleep) {
            calendarSleepTO.add(Calendar.DAY_OF_MONTH, 1);
            Log.d("TimeInmmm", "add day");
        }
        calendarSleepTO.set(Calendar.HOUR, getIntent().getExtras().getInt("toSleepH"));
        calendarSleepTO.set(Calendar.MINUTE, getIntent().getExtras().getInt("toSleepM"));
        calendarSleepTO.set(Calendar.AM_PM, getIntent().getExtras().getInt("toSleepAM_PM"));
        calendarSleepTO.set(Calendar.SECOND, 00);

        model.setRepeat(getIntent().getExtras().getInt("repeat"));
        model.alarmTone = getIntent().getExtras().getString("alarmTune");

        int cur = getIntent().getExtras().getInt("cur");
        Log.d("TimeInmmm", "cur=" + cur);
        tone = model.alarmTone;
        name = getIntent().getExtras().getString("alarmTheme");
        model.setThemeName(name);
        Log.d("TimeInmmm", "alarmTheme=" + name);
        repeat = model.getRepeat();

        themeWordsDAO = new WordsDAO(getApplicationContext(), name);
        words = new ArrayList<Words>();
        words = themeWordsDAO.getAllDictionaries();
        Log.d("Train", words.size() + "");

       // init();

        am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        if (cur == words.size()) {
            cur = 0;
            Log.d("TimeInmmm", "cur=" + cur);
        }
        alarmWord = words.get(cur);
        size = words.size();

        Calendar c = Calendar.getInstance();
        Log.d("TimeInmmm", calendarTO.getTimeInMillis() + " calendarTO.getTimeInMillis()");
        Log.d("TimeInmmm", c.getTimeInMillis() + " c.getTimeInMillis()");
        if(calendarTO.getTimeInMillis() < c.getTimeInMillis()){
            Log.d("TimeInmmm","this is the end");
            Intent values = getIntent();
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    12345, values, PendingIntent.FLAG_CANCEL_CURRENT);

            am.cancel(pendingIntent);
            pendingIntent.cancel();
            themeWordsDAO.close();
            finish();
        }
        else{
        Log.d("TimeInmmm", " i'm hereeeeee");
        Intent values = getIntent();


          //  PendingIntent pendingIntent =  PendingIntent.getService(getBaseContext(),(int) model.getId(), values, PendingIntent.FLAG_UPDATE_CURRENT);

        //cu = cu + 1;

        values.putExtra("cur", cur + 1);
            Log.d("TimeInmmm", "next cur=" + cur);
        values.putExtra("alarmTheme", model.getThemeName());
        values.putExtra("toDay", model.getToDay());
        values.putExtra("toMonth", model.getToMonth());
        values.putExtra("toYear", model.getToYear());
        values.putExtra("toHours", model.getToHours());
        values.putExtra("toMinutes", model.getToMinutes());
        values.putExtra("toAM_PM", toAM_PM);
        values.putExtra("fromSleepH", model.getFromSleepHours());
        values.putExtra("fromSleepM", model.getToSleepMinutes());
        values.putExtra("fromSleepAM_PM", fromSleep_AM_PM);
        values.putExtra("toSleepH", model.getToSleepHours());
        values.putExtra("toSleepM", model.getToSleepMinutes());
        values.putExtra("toSleepAM_PM", toSleepAM_PM);
        values.putExtra("repeat", model.getRepeat());
        values.putExtra("alarmTune", model.alarmTone);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    12345, values, PendingIntent.FLAG_CANCEL_CURRENT);


        if (c.getTimeInMillis() + 1000 * 60 * repeat >= calendarSleepFROM.getTimeInMillis() && (c.getTimeInMillis() <= calendarSleepTO.getTimeInMillis()) &&
                c.getTimeInMillis() < calendarTO.getTimeInMillis()) {
            values.putExtra("sleep", true);
            init();
            am.set(AlarmManager.RTC_WAKEUP,
                    calendarSleepTO.getTimeInMillis(), pendingIntent);
            Log.d("TimeInmmm", "next " + calendarSleepTO.get(Calendar.HOUR) + " "
                    + calendarSleepTO.get(Calendar.MINUTE) + " " +
                    calendarSleepTO.get(Calendar.AM_PM));
        } else {

            values.putExtra("sleep", false);
            init();
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                    1000 * 60 * repeat, pendingIntent);
            Log.d("TimeInmmm", "just add");
        }

     }
     Log.d("alala", "hi i'm in model screen");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            Log.i(TAG, "Wakelock aquired!!");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }

    private void init(){
        text1 = (TextView) findViewById(R.id.txt_word_list_item_wp);
        text2 = (TextView) findViewById(R.id.txt_translate_list_item_wp);
        text3 = (TextView) findViewById(R.id.txt_explanation_item_wp);
        im1 = (ImageView) findViewById(R.id.ivPicture_list_item_wp);

        text1.setText(alarmWord.getFirstLang());
        text2.setText(alarmWord.getSecondLang());

        byte[] outImage = alarmWord.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(outImage, 0, outImage.length);
       // im1.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 400, 320, false));
        im1.setImageBitmap(bitmap);
        text3.setText(alarmWord.getExplanation());

        Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        dismissButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mPlayer.stop();
                sleep = false;
                finish();
            }
        });

        mPlayer = new MediaPlayer();
        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                }
            }
        };

        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);

    }

}