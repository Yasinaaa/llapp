package com.example.yasina.llapp.Train;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.WordsPairsSpinner;
import com.example.yasina.llapp.AddWordsActivity;
import com.example.yasina.llapp.Alarm.AlarmDetailsActivity;
import com.example.yasina.llapp.Alarm.AlarmListActivity;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.Notification.NotificationWordActivity;
import com.example.yasina.llapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class MenuTrainActivity extends SherlockFragmentActivity implements View.OnClickListener {

    private Spinner themesSpinner;
    private WordsDAO wordsDAO;
    private WordsPairsSpinner mAdapter;
    private Words words;
    private String tableName = "";
    public static final String TAG = "MenuTrainActivity";
    private  ArrayList<String> listOfTableThemeNames;
    private int currentMenuPosition = -1;
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_train);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(235,170,91)));

        wordsDAO = new WordsDAO(this);
        Log.d(TAG, " wordsDAO = new WordsDAO(this)");
        final ArrayList<String> listOfTableThemeNames = wordsDAO.allThemesTablesNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfTableThemeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        themesSpinner = (Spinner) findViewById(R.id.spinnerOfThemes);
        Log.d(TAG, "size" + listOfTableThemeNames.size());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themesSpinner.setAdapter(adapter);
        themesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
        long name = themesSpinner.getSelectedItemId();
        tableName = listOfTableThemeNames.get((int)name) + "_theme";
        Toast.makeText(getBaseContext(), "Position = " + tableName, Toast.LENGTH_SHORT).show();
      }
      @Override
              public void onNothingSelected(AdapterView<?> arg0) {
              }
            });

        SlidingMenu menu = new SlidingMenu(this);
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

      /*  if (currentMenuPosition != -1) {
            ((ListView) findViewById(R.id.sidemenu)).setItemChecked(currentMenuPosition, true);
        }*/

        String[] items = {"Main","All Words",getString(R.string.add_words_fragment),"Train Words Theme"

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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ibAdd_newTheme:
                startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                break;
            case R.id.ibNext:
                intent = new Intent(getApplicationContext(), TrainWordsActivity.class);
                intent.putExtra("table name",tableName);
                startActivity(intent);
                break;
            case R.id.alarmSet:

                intent = new Intent(getApplicationContext(), AlarmDetailsActivity.class);
                intent.putExtra("table name",tableName);
                startActivity(intent);
                break;

            case R.id.ibTestTrain:
                intent = new Intent(getApplicationContext(), TestTrain.class);
                intent.putExtra("table name",tableName);
                startActivity(intent);
                break;

            case R.id.ibTrainDelete:
                wordsDAO.deletebyTableName(tableName);
                break;
        }
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

                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), MenuTrainActivity.class));
                break;
        }
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


}
