package com.example.yasina.llapp.Notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class NotificationWordActivity extends Activity {

    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;
    private String name = "";

    private WordsDAO wordsDAO;
    private   Words w = new Words();
    public static final String TAG = "NotificationWordActivity";
    private  ArrayList<Words> listOfTableThemeNames;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notification_word);

            listOfTableThemeNames = new ArrayList<Words>();
            name = getIntent().getExtras().getString("table name");
            wordsDAO = new WordsDAO(getApplicationContext(),name);
            listOfTableThemeNames =  wordsDAO.getAllDictionaries();
            Log.d(TAG,"" + listOfTableThemeNames.size());


           /* for(int i=0;i<listOfTableThemeNames.size();i++)
            {
               w = listOfTableThemeNames.get(i);
               // displayNotification(w);
                Log.d(TAG,"" + w.getFirstLang() + " " + w.getSecondLang());
                break;
            }*/

           Button startBtn = (Button) findViewById(R.id.hello);
            startBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    w = listOfTableThemeNames.get(0);
                    displayNotification(w);
                }
            });
/*
            Button cancelBtn = (Button) findViewById(R.id.cancelNote);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    cancelNotification();
                }
            });

            Button updateBtn = (Button) findViewById(R.id.updateNote);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    updateNotification();
                }
            });*/
        }
        protected void displayNotification(Words word) {

            Log.i("Start", "notification");
            NotificationCompat.Builder  mBuilder =
                    new NotificationCompat.Builder(this);

            mBuilder.setContentTitle(word.getFirstLang());
            mBuilder.setContentText(word.getExplanation());
            mBuilder.setTicker(word.getExplanation());
            byte[] outImage = word.getImage();
            Log.d("byte[] outImage", "=word.getImage()");
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Log.d("ByteArrayInputStream imageStream ", "= new ByteArrayInputStream(outImage);");
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            Log.d("Bitmap theImage", " = BitmapFactory.decodeStream(imageStream);");
          //  mBuilder.setLargeIcon(theImage);
            mBuilder.setSmallIcon(R.drawable.teacher);
            Log.d("mBuilder.setLargeIcon(theImage)", " ");
            mBuilder.setNumber(++numMessages);
            Log.d("mBuilder.setLargeIcon(theImage)", "");

            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();

            String[] events = new String[3];
            events[0] = new String(word.getFirstLang());
            events[1] = new String(word.getSecondLang());
            events[2] = new String(word.getExplanation());
           /* events[3] = new String("This is 4th line...");
            events[4] = new String("This is 5th line...");
            events[5] = new String("This is 6th line...");*/


            inboxStyle.setBigContentTitle("Big Title Details:");
            for (int i=0; i < events.length; i++) {

                inboxStyle.addLine(events[i]);
                Log.d("inboxStyle.addLine", ""+events[i]);
            }
            mBuilder.setStyle(inboxStyle);
            Log.d(" mBuilder.setStyle", "");

/*
            Intent resultIntent = new Intent(this, NotificationView.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(NotificationView.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);*/

            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            mNotificationManager.notify(notificationID, mBuilder.build());
        }

        protected void cancelNotification() {
            Log.i("Cancel", "notification");
            mNotificationManager.cancel(notificationID);
        }

        protected void updateNotification() {
            Log.i("Update", "notification");


            NotificationCompat.Builder  mBuilder =
                    new NotificationCompat.Builder(this);

            mBuilder.setContentTitle("Updated Message");
            mBuilder.setContentText("You've got updated message.");
            mBuilder.setTicker("Updated Message Alert!");
            mBuilder.setSmallIcon(R.drawable.education);


            mBuilder.setNumber(++numMessages);

         /*   Intent resultIntent = new Intent(this, NotificationView.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(NotificationView.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);

            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            mNotificationManager.notify(notificationID, mBuilder.build());*/
        }

    }


