package com.example.yasina.llapp;

import android.content.Intent;
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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.Activities.ListDictionariesActivity;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.DictionariesSpinner;
import com.example.yasina.llapp.Alarm.AlarmListActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        dictionariesSpinner = (Spinner) findViewById(R.id.spinnerOfDictionaries);

        dictionaryDAO = new DictionaryDAO(this);
		List<Dictionary> listDictionaries = dictionaryDAO.getAllDictionaries();
		if(listDictionaries != null) {
			mAdapter = new DictionariesSpinner(this, listDictionaries);
			dictionariesSpinner.setAdapter(mAdapter);
			dictionariesSpinner.setOnItemSelectedListener(this);
		}

            SlidingMenu menu = new SlidingMenu(this);
            menu.setMode(SlidingMenu.LEFT);
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            menu.setFadeDegree(0.35f);
            menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
            menu.setMenu(R.layout.sidemenu);
            menu.setBackgroundColor(0xFF333333);
            //menu.setBackgroundColor(EBAA5B);
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

            if (currentMenuPosition != -1) {
                ((ListView) findViewById(R.id.sidemenu)).setItemChecked(currentMenuPosition, true);
            }

            String[] items = {"Main","All Words",getString(R.string.add_words_fragment),"Train Words Theme","Notification","Alarm"
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
                //showFragment(new LearnWordsFragment());
               // startActivity(new Intent(getApplicationContext(),LearnWordsActivity.class));
                startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                break;
            case 2:
//                showFragment(new AddWordsActivity());
                startActivity(new Intent(getApplicationContext(),AddWordsActivity.class));

                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), MenuTrainActivity.class));
                break;
            case 4:
                Intent intent = new Intent(getApplicationContext(), NotificationWordActivity.class);
                intent.putExtra("table name","first_theme");
                startActivity(intent);
                break;
            case 5:
               Intent i = new Intent(getApplicationContext(), AlarmListActivity.class);
                i.putExtra("table name","first_theme");
                startActivity(i);
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
                //startActivity(new Intent(getApplicationContext(),ListDictionariesActivity.class));
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

         //Intent intent = new Intent(this, AddWordsFragment.class);
         //intent.putExtra(ListEmployeesActivity.EXTRA_SELECTED_COMPANY_ID, clickedCompany.getId());
         //startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

