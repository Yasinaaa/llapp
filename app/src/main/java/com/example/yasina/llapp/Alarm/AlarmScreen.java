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
        import android.view.MotionEvent;
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

        import java.io.ByteArrayInputStream;
        import java.text.DateFormat;
        import java.util.ArrayList;
        import java.util.Date;

public class AlarmScreen extends Activity {

    public final String TAG = this.getClass().getSimpleName();

    private WakeLock mWakeLock;
    private MediaPlayer mPlayer;
    private String name;
    private  WordsDAO themeWordsDAO;
    private ArrayList<Words> words;
    private Words alarmWord;
    private TextView text1, text2, text3;
    private ImageView im1;
   // public int cur;


    private static final int WAKELOCK_TIMEOUT = 60 * 1000;

    MediaPlayer mp = null;
    TextView e = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alarm_screen);
        int amount =  getIntent().getExtras().getInt("amount");
        int cu =  getIntent().getExtras().getInt("current");
        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);

        if(amount == cu){

            PendingIntent   pendingIntent =   PendingIntent.getActivity(this,
                    12345, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
            am.cancel(pendingIntent);

        }else{
            Intent intent = getIntent();

            String name = "first_theme";
            themeWordsDAO = new WordsDAO(getApplicationContext(),name);
            words = new ArrayList<Words>();
            words =  themeWordsDAO.getAllDictionaries();
            Log.d("Train",words.size()+"");
            alarmWord = words.get(cu);


            text1 = (TextView) findViewById(R.id.txt_word_list_item_wp);
            text2 = (TextView) findViewById(R.id.txt_translate_list_item_wp);
            text3 = (TextView) findViewById(R.id.txt_explanation_item_wp);
            im1 = (ImageView) findViewById(R.id.ivPicture_list_item_wp);

            text1.setText(alarmWord.getFirstLang());
            text2.setText(alarmWord.getSecondLang());

            byte[] outImage = alarmWord.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            im1.setImageBitmap(theImage);

            text3.setText(alarmWord.getExplanation());

       Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        dismissButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // mPlayer.stop();
               finish();
            }
        });

            intent.putExtra("current", cu + 1);
            PendingIntent   pendingIntent =   PendingIntent.getActivity(this,
                    12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +
                    1000 * 15, pendingIntent);
        }


        Log.d("ring alarm","hi");
     //   Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
       // e = (TextView) findViewById(R.id.text);

        //e.setText("num " +cu);

        //   mp = MediaPlayer.create(getBaseContext(), R.raw.audio);

        //   mp = MediaPlayer.create(getBaseContext(), null);

      /*  stopAlarm.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                //    mp.stop();
                finish();
                return false;
            }
        });*/

        // playSound(this, getAlarmUri());
    }

      /*  super.onCreate(savedInstanceState);

        //Setup layout
        // this.setContentView(R.layout.list_item_words_pair_2);
        this.setContentView(R.layout.activity_alarm_screen);


        int amount = getIntent().getExtras().getInt("amount");
        Log.d("amount", amount + "");
        //  int cu =  getIntent().getExtras().getInt("current");

        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);

        if (amount == cur) {
            Log.d("cancel amount", amount + "==" + cur);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    12345, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
            am.cancel(pendingIntent);
        } else {
            Log.d("now cur", cur + "");
            Intent intent = getIntent();

            name = "first_theme";
         /*   Log.d(" Name", name + "");
            themeWordsDAO = new WordsDAO(getApplicationContext(), name);
            words = new ArrayList<Words>();
            words = themeWordsDAO.getAllDictionaries();
            Log.d("Alarm Train", words.size() + "");
            alarmWord = words.get(cur);*/

       /*     cur = cur + 1;
            Log.d("after add cur", cur + "");
            intent.putExtra("current", cur);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +
                    1000 * 15, pendingIntent);
        }

        // name = getIntent().getExtras().getString("table name");


        String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
        int timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
        int timeMinute = getIntent().getIntExtra(AlarmManagerHelper.TIME_MINUTE, 0);
        String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);

        text1 = (TextView) findViewById(R.id.txt_word_list_item_wp);
        text2 = (TextView) findViewById(R.id.txt_translate_list_item_wp);
        text3 = (TextView) findViewById(R.id.txt_explanation_item_wp);
        im1 = (ImageView) findViewById(R.id.ivPicture_list_item_wp);

      //  try {
            text1.setText(alarmWord.getFirstLang());
            text2.setText(alarmWord.getSecondLang());

            byte[] outImage = alarmWord.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            im1.setImageBitmap(theImage);

            text3.setText(alarmWord.getExplanation());
      /*  }catch (Exception e){
            finish();
        }*/

      /*  TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
        tvName.setText(name);

        TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));*/


     /*   Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        dismissButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // mPlayer.stop();
               finish();
            }
        });*/

        //Play alarm tone
     /*   mPlayer = new MediaPlayer();
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

        //Ensure wakelock release
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

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // Acquire wakelock
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
    }*/
  //  }
}

