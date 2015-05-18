package com.example.yasina.llapp;

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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.Activities.AddDictionaryActivity;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.DictionariesSpinner;
import com.example.yasina.llapp.Alarm.AlarmDAO;
import com.example.yasina.llapp.Alarm.AlarmDetailsActivity;
import com.example.yasina.llapp.Alarm.AlarmManagerHelper;
import com.example.yasina.llapp.Alarm.AlarmModel;
import com.example.yasina.llapp.DAO.DBHelper;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Train.MenuTrainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

public class MainMenuActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static int currentMenuPosition = -1;
    private int position;
    public static final String TAG = "MainMenuActivity";
    public static int idDictionary;
    private SlidingMenu menu;
    private Spinner dictionariesSpinner;
    private DictionaryDAO dictionaryDAO;
    private DictionariesSpinner mAdapter;
    public static long clickedDictionary;
    private TextView theme_name, alarmTheme_to, alarmTheme_from, alarmTheme_repeat, sleepFrom, sleepTo;
    private TextView text_alarmTheme_to, text_alarmTheme_from, text_theme_name, text_sleepFrom, text_sleepTo, text_alarm, text_sleep;
    private AlarmDAO alarmDAO;
    private View div1,div2,div3;
    private Button add, alarmUpd;
    private View v;
    private AlarmModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 255, 255)));
        getActionBar().setTitle("Main");
        init();

       /* DBHelper b = new DBHelper(this);
        SQLiteDatabase db = b.getWritableDatabase();
        db.execSQL("DROP TABLE alarmTable");*/


        try {
            alarmDAO = new AlarmDAO(this);
            model = alarmDAO.getByID();
            alarmDAO.close();

            String temp = model.getThemeName().replace("_theme","");
            theme_name.setText(temp);

            text_theme_name.setText("Theme");
            text_alarmTheme_to.setText("To");
            text_alarmTheme_from.setText("From");
            text_sleepFrom.setText("From");
            text_sleepTo.setText("To");
            text_alarm.setText("Alarm");
            text_sleep.setText("Sleep");

            alarmTheme_from.setText(model.getFromDay() + "." + (model.getFromMonth() + 1) + "." + model.getFromYear() + " " + model.getFromHours() + ":" + model.getFromMinutes() + " " + model.getFromAM_PM());
            alarmTheme_to.setText(model.getToDay() + "." + (model.getToMonth() + 1) + "." + model.getToYear() + " " + model.getToHours() + ":" + model.getToMinutes() + " " + model.getToAM_PM());
            alarmTheme_repeat.setText("Every " + model.getRepeat() + " " + model.getRepeatMin_Hour());
            sleepFrom.setText(model.getFromSleepHours() + ":" + model.getFromSleepMinutes() + " " + model.getFromSleep_AM_PM());
            sleepTo.setText(model.getToSleepHours() + ":" + model.getToSleepMinutes() + " " + model.getToSleep_AM_PM());
        }catch(RuntimeException e){
            div1.setVisibility(View.INVISIBLE);
            div2.setVisibility(View.INVISIBLE);
            div3.setVisibility(View.INVISIBLE);
            alarmUpd.setVisibility(View.INVISIBLE);
        }

        dictionaryDAO = new DictionaryDAO(this);
        try {
            List<Dictionary> listDictionaries = dictionaryDAO.getAllDictionaries();
            if (listDictionaries != null) {
                mAdapter = new DictionariesSpinner(this, listDictionaries);
                dictionariesSpinner.setAdapter(mAdapter);
                dictionariesSpinner.setOnItemSelectedListener(this);
            }
        }catch (RuntimeException e){
            setContentView(R.layout.first);
            getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));
            getActionBar().setTitle("Welcome!");
            Button create = (Button) findViewById(R.id.btnCreate_dic);
        }

     //   alarmDAO = new AlarmDAO(this);
            menu = new SlidingMenu(this);
            menu.setMode(SlidingMenu.LEFT);
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            menu.setFadeDegree(0.35f);
            menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
            menu.setMenu(R.layout.sidemenu);
            menu.setBackgroundColor(0xFF333333);
            menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
            menu.setSelectorDrawable(R.drawable.sidemenu_items_background);

            ((ListView) findViewById(R.id.sidemenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    menuToggle();

                   if (currentMenuPosition != position)
                        changeFragment(position);

                    currentMenuPosition = position;
                }
            });

         /*   if (currentMenuPosition != -1) {
                ((ListView) findViewById(R.id.sidemenu)).setItemChecked(currentMenuPosition, true);
            }*/

            String[] items = {"Main","All Words",getString(R.string.add_words_fragment),"Train Words Theme"//,"Notification","Alarm"
                    /*, getString(R.string.add_languages_fragmnet),
                    getString(R.string.new_words_theme_fragment), getString(R.string.create_new_lang_connection_fragment), getString(R.string.add_teacher_fragment),
                    getString(R.string.all_words_fragment), getString(R.string.create_test_fragment), getString(R.string.settings_fragment)*/
                    };

            ((ListView) findViewById(R.id.sidemenu)).setAdapter(
                    new ArrayAdapter<Object>(
                            this,
                            R.layout.sidemenu_item,
                            R.id.text,
                            items
                    )
            );

            this.menu = menu;

            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayShowCustomEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                if(menu.isMenuShowing()){
                    menu.toggle(true);
                    return false;
                }
            }
            return super.onKeyDown(keyCode, event);
        }

    private void changeFragment(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(),AddWordsActivity.class));
              //  showFragment(new AddWordsActivity());
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), MenuTrainActivity.class));
                break;

        }
    }

    /*private void showFragment(Fragment currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()//.show(currentFragment)
               .replace(R.id.container, currentFragment)

                .commit();
    }*/

    public SlidingMenu getMenu() {
        return menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuToggle();
                return true;

            case R.id.ibAddDictionary:
                startActivity(new Intent(getApplicationContext(),AddDictionaryActivity.class));
                break;

            case R.id.ibDeleteDictionary:
                DictionaryDAO dicDAO = new DictionaryDAO(getApplicationContext());
                dicDAO.deleteDictionary(clickedDictionary);
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
                break;

            case R.id.mibUpdateDicitonary:
                Intent i = new Intent(getApplicationContext(),AddDictionaryActivity.class);
                i.putExtra("id",clickedDictionary);
                i.putExtra("position",position);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuToggle(){
        if(menu.isMenuShowing())
            menu.showContent();
        else
            menu.showMenu();
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
        /*    case R.id.ibAddDictionary:
                startActivity(new Intent(getApplicationContext(),AddDictionaryActivity.class));
                // startActivity(new Intent(getApplicationContext(),ListDictionariesActivity.class));
                break;
*/
            case R.id.btnCreate_dic:
                startActivity(new Intent(getApplicationContext(),AddDictionaryActivity.class));
                break;

          /*  case R.id.ibChangeDictionary:
                showDialog();
            */

            case R.id.alarmUpdate:
                showDialog();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        clickedDictionary = dictionariesSpinner.getSelectedItemId();
        position = dictionariesSpinner.getSelectedItemPosition();
      //  if(clickedDictionary == 0)
        v = view;
        Log.d(TAG, "clickedItem : " + clickedDictionary);
    }



    private void showDialog() {
        String button1String = "Nothing";
        String button2String = "Delete";
        String button3String = "Change";

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Change");
        ad.setMessage("What do you wanna do with this alarm?");
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                finish();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                AlarmManagerHelper.cancelAlarms(getApplicationContext());
                alarmDAO = new AlarmDAO(getApplicationContext());
                alarmDAO.delete(model.getThemeName());
                alarmDAO.close();
                theme_name.setVisibility(View.INVISIBLE);

                text_theme_name.setVisibility(View.INVISIBLE);
                text_alarmTheme_to.setVisibility(View.INVISIBLE);
                text_alarmTheme_from.setVisibility(View.INVISIBLE);
                text_sleepFrom.setVisibility(View.INVISIBLE);
                text_sleepTo.setVisibility(View.INVISIBLE);
                text_alarm.setVisibility(View.INVISIBLE);
                text_sleep.setVisibility(View.INVISIBLE);

                alarmTheme_from.setVisibility(View.INVISIBLE);
                alarmTheme_to.setVisibility(View.INVISIBLE);
                alarmTheme_repeat.setVisibility(View.INVISIBLE);
                sleepFrom.setVisibility(View.INVISIBLE);
                sleepTo.setVisibility(View.INVISIBLE);

                div1.setVisibility(View.INVISIBLE);
                div2.setVisibility(View.INVISIBLE);
                div3.setVisibility(View.INVISIBLE);
                alarmUpd.setVisibility(View.INVISIBLE);
            }
        });
        ad.setNeutralButton(button3String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent i = new Intent(getApplicationContext(), AlarmDetailsActivity.class);
                i.putExtra("lets go",true);
                startActivity(i);
            }
        });

        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        AlertDialog alert = ad.create();
        alert.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void init(){
        dictionariesSpinner = (Spinner) findViewById(R.id.spinnerOfDictionaries);


        theme_name = (TextView) findViewById(R.id.tv_theme_main);
        alarmTheme_to = (TextView) findViewById(R.id.tv_AlarmThemeTo_main);
        alarmTheme_from = (TextView) findViewById(R.id.tv_AlarmThemeFrom_main);
        alarmTheme_repeat = (TextView) findViewById(R.id.tv_AlarmThemeRepeat_main);
        sleepFrom = (TextView) findViewById(R.id.tv_AlarmThemeSleepFrom_main);
        sleepTo = (TextView) findViewById(R.id.tv_AlarmThemeSleepTo_main);
        text_theme_name = (TextView) findViewById(R.id.etTheme);

        text_alarmTheme_to = (TextView) findViewById(R.id.text_AlarmThemeTo_main);
        text_alarmTheme_from = (TextView) findViewById(R.id.text_AlarmThemeFrom_main);
        text_sleepFrom = (TextView) findViewById(R.id.text_AlarmThemeSleepFrom_main);
        text_sleepTo = (TextView) findViewById(R.id.text_AlarmThemeSleepTo_main);
        text_alarm = (TextView) findViewById(R.id.etAlarm_main);
        text_sleep = (TextView) findViewById(R.id.etAlarmSleep);
        div1 = (View) findViewById(R.id.divider1);
        div2 = (View) findViewById(R.id.divider2);
        div3 = (View) findViewById(R.id.divider3);
        alarmUpd = (Button) findViewById(R.id.alarmUpdate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        getMenuInflater().inflate(R.menu.main_menu, menu2);
        return true;

    }
}

