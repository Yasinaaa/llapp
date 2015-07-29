package com.example.yasina.llapp.Train;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.ListWordsAdapter;
import com.example.yasina.llapp.Adapter.WordsPairsSpinner;
import com.example.yasina.llapp.AddWordsActivity;
import com.example.yasina.llapp.Alarm.AlarmDetailsActivity;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class MenuTrain_Fragment extends SherlockFragment implements  View.OnClickListener{

    private View view;
    private ActionBar actionBar;

    private Spinner themesSpinner;
    private WordsDAO wordsDAO;
    private WordsPairsSpinner mAdapter;
    private Words words;
    private String tableName = "";
    public static final String TAG = "MenuTrain_Fragment";
    private ArrayList<String> listOfTableThemeNames;
    private SlidingMenu menu;
    private Button next, alarmSet,testTrain,trainDelete,themeADD;
    private ImageButton add_newTheme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_menu_train, container, false);
        setHasOptionsMenu(true);
        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setTitle("Menu Train");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));

        add_newTheme = (ImageButton) view.findViewById(R.id.ibAdd_newTheme);
        next = (Button) view.findViewById(R.id.ibNext);
        alarmSet = (Button) view.findViewById(R.id.alarmSet);
        testTrain = (Button) view.findViewById(R.id.ibTestTrain);
        trainDelete = (Button) view.findViewById(R.id.ibTrainDelete);
        themeADD = (Button) view.findViewById(R.id.themeADD);

        add_newTheme.setOnClickListener(this);
        next.setOnClickListener(this);
        alarmSet.setOnClickListener(this);
        testTrain.setOnClickListener(this);
        trainDelete.setOnClickListener(this);
        themeADD.setOnClickListener(this);

        wordsDAO = new WordsDAO(getSherlockActivity().getApplicationContext());
        Log.d(TAG, " wordsDAO = new WordsDAO(this)");
        final ArrayList<String> listOfTableThemeNames = wordsDAO.allThemesTablesNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getSherlockActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listOfTableThemeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        themesSpinner = (Spinner) view.findViewById(R.id.spinnerOfThemes);
        Log.d(TAG, "size" + listOfTableThemeNames.size());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themesSpinner.setAdapter(adapter);
        themesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                long name = themesSpinner.getSelectedItemId();
                tableName = listOfTableThemeNames.get((int)name) + "_theme";
                Toast.makeText(getSherlockActivity().getApplicationContext(), "Position = " + tableName, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

       return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ibAdd_newTheme:
                startActivity(new Intent(getSherlockActivity().getBaseContext(), ListWordsPairActivity.class));
               // finish();
                break;
            case R.id.ibNext:
                intent = new Intent(getSherlockActivity().getBaseContext(), TrainWordsActivity.class);
                intent.putExtra("table name", tableName);
                startActivity(intent);
                //finish();
                break;
            case R.id.alarmSet:
                intent = new Intent(getSherlockActivity().getBaseContext(), AlarmDetailsActivity.class);
                intent.putExtra("table name", tableName);
                startActivity(intent);
                //finish();
                break;

            case R.id.ibTestTrain:
                intent = new Intent(getSherlockActivity().getBaseContext(), TestTrain.class);
                intent.putExtra("table name", tableName);
                startActivity(intent);
                break;

            case R.id.ibTrainDelete:
                wordsDAO.deletebyTableName(tableName);
                intent = new Intent(getSherlockActivity().getBaseContext(), MenuTrainActivity.class);
                intent.putExtra("table name", tableName);
                startActivity(intent);
               // finish();
                break;
            case R.id.themeADD:
                intent = new Intent(getSherlockActivity().getBaseContext(),AddWordsActivity.class);
                intent.putExtra("table name", tableName);
                startActivity(intent);
              //  finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }
}
