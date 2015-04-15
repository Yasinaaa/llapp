package com.example.yasina.llapp.Train;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.WordsPairsSpinner;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;

public class MenuTrainActivity extends Activity implements View.OnClickListener {

    private Spinner themesSpinner;
    private WordsDAO wordsDAO;
    private WordsPairsSpinner mAdapter;
    private Words words;
    private String tableName = "";
    public static final String TAG = "MenuTrainActivity";
    private  ArrayList<String> listOfTableThemeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_train);

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
        tableName = listOfTableThemeNames.get((int)name);
        Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
      }
      @Override
              public void onNothingSelected(AdapterView<?> arg0) {
              }
            });

     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAdd_newTheme:
                startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                break;
            case R.id.ibNext:

                Toast.makeText(getBaseContext(), "TableName = " + tableName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TrainWordsActivity.class);
                intent.putExtra("table name",tableName);
                startActivity(intent);
                break;
        }
    }


}
