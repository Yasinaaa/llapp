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
        import java.util.GregorianCalendar;

public class AlarmScreen
        extends Activity {

    public final String TAG = this.getClass().getSimpleName() + "1";

    private WakeLock mWakeLock;
    private MediaPlayer mPlayer;
    private String name;
    private WordsDAO themeWordsDAO;
    private ArrayList<Words> words;
    private Words alarmWord;
    private TextView text1, text2, text3;
    private ImageView im1;
    private Calendar calendarEND, calendar_sleepFROM, calendar_sleepTo;
    private boolean sleep;
    private int repeat;
    private static final int WAKELOCK_TIMEOUT = 60 * 1000;
    private String tone;
    private int size;
    private AlarmModel alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alarm_screen);
       /* name = getIntent().getExtras().getString("table name");
        repeat = getIntent().getExtras().getInt("repeat");
        int cu = getIntent().getExtras().getInt("current");
        calendarEND = (Calendar) getIntent().getExtras().get("endDate");
        calendar_sleepFROM = (Calendar) getIntent().getExtras().get("sleepTime_from");
        calendar_sleepTo = (Calendar) getIntent().getExtras().get("sleepTime_to");
        sleep = getIntent().getExtras().getBoolean("sleep");
        Log.d(TAG,"sleep " + sleep);
        tone =  getIntent().getExtras().getString("tone");*/
        alarm = (AlarmModel) getIntent().getExtras().get("alarm");
        tone = alarm.alarmTone;

        themeWordsDAO = new WordsDAO(getApplicationContext(), alarm.getThemeName());
        words = new ArrayList<Words>();
        words = themeWordsDAO.getAllDictionaries();
        Log.d("Train", words.size() + "");
       // alarmWord = words.get(cu);
        alarmWord = words.get(0);
        size = words.size();

       /* AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Log.d("AlarmScreen1","calendar from AlarmScreen before set  " + calendar.toString());
        Log.d("AlarmScreen1", "calendarEND before equlas  " + calendarEND.toString());
        Log.d("AlarmScreen1","equlas?  " + calendar.equals(calendarEND));*/

     /*   if (calendar.equals(calendarEND)) {
            Log.d(TAG,"cancel alarm becouce equals");
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                   12345, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
            am.cancel(pendingIntent);
            pendingIntent.cancel();
            finish();

        } else {
            Intent intent = getIntent();
            if(!sleep){
                sleep = false;
                init();

                cu = cu + 1;
                if (!calendar.equals(calendarEND)) {
                    if (cu == size) cu = 0;

                    intent.putExtra("current", cu);
                    intent.putExtra("endDate", calendarEND);
                    intent.putExtra("sleepTime_from", calendar_sleepFROM);
                    intent.putExtra("sleepTime_to", calendar_sleepTo);
                    intent.putExtra("repeat", repeat);
                    intent.putExtra("sleep", false);
                    intent.putExtra("table name", name);

                    PendingIntent pendingIntent = PendingIntent.getActivity(this,
                            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    Log.d(TAG, "set alarm (usual)");



            Log.d(TAG, "equlas  " + calendar.get(Calendar.HOUR) + " and " + calendar_sleepFROM.get(Calendar.HOUR) +
                   " also " + calendar.get(Calendar.MINUTE) + " and " + calendar_sleepFROM.get(Calendar.MINUTE));

            if (calendar.get(Calendar.HOUR) == calendar_sleepFROM.get(Calendar.HOUR) &&
                    calendar.get(Calendar.MINUTE) + repeat == calendar_sleepFROM.get(Calendar.MINUTE)) {

                Log.d(TAG, "cancel alarm becouce 22:00");
                if (cu == size) cu = 0;

                intent.putExtra("current", cu);
                //intent.putExtra("endDate", calendarEND);
                //intent.putExtra("sleepTime_from", calendar_sleepFROM);
                //intent.putExtra("sleepTime_to", calendar_sleepTo);
                intent.putExtra("sleep", true);
                //intent.putExtra("repeat", repeat);
                //intent.putExtra("table name", name);
                am.set(AlarmManager.RTC_WAKEUP, calendar_sleepTo.getTimeInMillis(), pendingIntent);
                Log.d(TAG, "set alarm " + calendar_sleepTo.get(Calendar.HOUR) + " " + calendar_sleepTo.get(Calendar.MINUTE)
                        + " " + calendar_sleepTo.get(Calendar.AM_PM));

            }else
                am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +
                        1000 * 60 * repeat, pendingIntent);

                }

            }
        }*/
        init();
        Log.d("alala", "hi i'm in alarm screen");

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
        im1.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 200, 120, false));
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