package com.example.yasina.llapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.Activities.ListDictionariesActivity;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.DictionariesSpinner;
import com.example.yasina.llapp.Alarm.AlarmDAO;
import com.example.yasina.llapp.Alarm.AlarmManagerHelper;
import com.example.yasina.llapp.Alarm.AlarmModel;
import com.example.yasina.llapp.DAO.DBHelper;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Notification.NotificationWordActivity;
import com.example.yasina.llapp.Train.MenuTrainActivity;
import com.example.yasina.llapp.Train.TestTrain;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

public class MainMenuActivity extends SherlockFragmentActivity implements AdapterView.OnItemSelectedListener {

    private static int currentMenuPosition = -1;
    public static final String TAG = "MainMenuActivity";
    public static int idDictionary;
    private SlidingMenu menu;
    private Spinner dictionariesSpinner;
    private DictionaryDAO dictionaryDAO;
    private DictionariesSpinner mAdapter;
    public static long clickedDictionary;
    private TextView theme_name, alarmTheme_to, alarmTheme_from, alarmTheme_repeat, sleepFrom, sleepTo;
    private AlarmDAO alarmDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));
        getActionBar().setTitle("Main");

        dictionariesSpinner = (Spinner) findViewById(R.id.spinnerOfDictionaries);
        theme_name = (TextView) findViewById(R.id.tv_theme_main);
        alarmTheme_to = (TextView) findViewById(R.id.tv_AlarmThemeTo_main);
        alarmTheme_from = (TextView) findViewById(R.id.tv_AlarmThemeFrom_main);
        alarmTheme_repeat = (TextView) findViewById(R.id.tv_AlarmThemeRepeat_main);
        sleepFrom = (TextView) findViewById(R.id.tv_AlarmThemeSleepFrom_main);
        sleepTo = (TextView) findViewById(R.id.tv_AlarmThemeSleepTo_main);

        dictionaryDAO = new DictionaryDAO(this);
		List<Dictionary> listDictionaries = dictionaryDAO.getAllDictionaries();
		if(listDictionaries != null) {
			mAdapter = new DictionariesSpinner(this, listDictionaries);
			dictionariesSpinner.setAdapter(mAdapter);
			dictionariesSpinner.setOnItemSelectedListener(this);
		}

        alarmDAO = new AlarmDAO(this);
       try {
           AlarmModel model = alarmDAO.getByID();
           alarmDAO.close();
            String temp = model.getThemeName().replace("_theme","");
            theme_name.setText(temp);

            alarmTheme_from.setText("From " + model.getFromDay() + "." + model.getFromMonth() + "." + model.getFromYear() + " " + model.getFromHours() + ":" + model.getFromMinutes() + " " + model.getFromAM_PM());
            alarmTheme_to.setText("To " + model.getToDay() + "." + model.getToMonth() + "." + model.getToYear() + " " + model.getToHours() + ":" + model.getToMinutes() + " " + model.getToAM_PM());
            alarmTheme_repeat.setText("Every " + model.getRepeat() + " " + model.getRepeatMin_Hour());
            sleepFrom.setText("From " + model.getFromSleepHours() + ":" + model.getFromSleepMinutes() + " " + model.getFromSleep_AM_PM());
            sleepTo.setText("To " + model.getToSleepHours() + ":" + model.getToSleepMinutes() + " " + model.getToSleep_AM_PM());
        }catch(RuntimeException e){

       }

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

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void showFragment(Fragment currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()//.show(currentFragment)
               .replace(R.id.container, currentFragment)

                .commit();
    }

    public SlidingMenu getMenu() {
        return menu;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuToggle();
                return true;
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
            case R.id.ibAddDictionary:

                 startActivity(new Intent(getApplicationContext(),ListDictionariesActivity.class));
                break;



            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        clickedDictionary = dictionariesSpinner.getSelectedItemId();
        if(clickedDictionary == 0) clickedDictionary = 1;

        Log.d(TAG, "clickedItem : " + clickedDictionary);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

