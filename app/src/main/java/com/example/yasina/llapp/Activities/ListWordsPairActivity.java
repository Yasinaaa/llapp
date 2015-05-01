package com.example.yasina.llapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.Adapter.ListWordsAdapter;
import com.example.yasina.llapp.AddWordsActivity;
import com.example.yasina.llapp.Alarm.AlarmListActivity;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.Notification.NotificationWordActivity;
import com.example.yasina.llapp.R;
import com.example.yasina.llapp.Train.MenuTrainActivity;
import com.example.yasina.llapp.Train.TrainWordsActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.StringTokenizer;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * Created by yasina on 11.03.15.
 */
public class ListWordsPairActivity
        extends SherlockFragmentActivity
        implements OnItemLongClickListener, OnItemClickListener, OnClickListener{

    public static final String TAG = "ListWordsPairActivity";

    private ListView mListviewWP;
    private TextView mTxtEmptyWP;
    private ListWordsAdapter mAdapter;
    private ArrayList<Words> mListWP;
    private WordsDAO mWordsPairDao;
    private ArrayList<Words> forTest;
    private String name2 = "";

    private int currentMenuPosition = -1;
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_words_pair);

        getActionBar().setTitle("All Words");

        try {
            DictionaryDAO dicDAO = new DictionaryDAO(this);
            Dictionary dic = dicDAO.getDicitonaryById(MainMenuActivity.clickedDictionary);

            StringTokenizer tokenizer = new StringTokenizer(dic.getName(), "-");
            while (tokenizer.hasMoreElements()) {
                name2 = tokenizer.nextToken() + "_" + tokenizer.nextToken();
            }

            initViews();

            mWordsPairDao = new WordsDAO(this, name2);

            mListWP = mWordsPairDao.getAllDictionaries();
            if (mListWP != null && !mListWP.isEmpty()) {

                mAdapter = new ListWordsAdapter(this, R.layout.list_item_words_pair, mListWP);
                mListviewWP.setAdapter(mAdapter);

                mListviewWP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Words clickedWordsPair = ((Words) adapterView.getItemAtPosition(i));
                        forTest.add(clickedWordsPair);
                        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());

                    }
                });
            } else {
                mTxtEmptyWP.setVisibility(View.VISIBLE);
                mListviewWP.setVisibility(View.GONE);
            }
        }catch (RuntimeException e){
            setContentView(R.layout.empty);

            String button1String = "Create dictionary";
            String button2String = "Cancel";

            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Mistake");
            ad.setMessage("You don't have any dictionaries.Please create new one for this.");
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(getApplicationContext(),ListDictionariesActivity.class));
                }
            });
            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    finish();
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

    private void initViews() {
        this.mListviewWP = (ListView) findViewById(R.id.list_wps);
        this.mTxtEmptyWP = (TextView) findViewById(R.id.txt_empty_list_wp);
        this.mListviewWP.setOnItemClickListener(this);
        this.mListviewWP.setOnItemLongClickListener(this);
        forTest = new ArrayList<Words>();
    }

   private String name;
   private WordsDAO wordsDAO_new;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_words_list_to_theme:

                final EditText input = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListWordsPairActivity.this);
	               builder.setTitle("Write here your words theme name")
                          .setView(input)
		                  .setNegativeButton("OK",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          name = input.getText().toString() + "_theme";
                                          wordsDAO_new = new WordsDAO(getApplicationContext(),name);
                                          Log.d(TAG,"" + wordsDAO_new.getAllDictionaries().size());
                                          wordsDAO_new.addListOfWords(forTest);
                                          dialog.cancel();
                                      }
                                  });
	              AlertDialog alert = builder.create();
	              alert.show();

                break;
            case R.id.btn_train_words_list_to_theme:

                final EditText input2 = new EditText(this);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ListWordsPairActivity.this);
                builder2.setTitle("Write here your words theme name")
                        .setView(input2)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        name = input2.getText().toString() + "_theme";
                                        wordsDAO_new = new WordsDAO(getApplicationContext(),name);

                                        wordsDAO_new.addListOfWords(forTest);
                                        Log.d(TAG,"" + wordsDAO_new.getAllDictionaries().size());
                                        Intent intent = new Intent(getApplicationContext(), TrainWordsActivity.class);
                                        intent.putExtra("table name",name);
                                        startActivity(intent);
                                        dialog.cancel();
                                        finish();
                                    }
                                });
                AlertDialog alert2 = builder2.create();
                alert2.show();

                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Words clickedWordsPair = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Words clickedWordsPair = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
        return true;
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

