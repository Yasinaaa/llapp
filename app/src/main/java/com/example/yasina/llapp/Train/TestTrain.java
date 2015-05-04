package com.example.yasina.llapp.Train;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class TestTrain extends Activity {

    private LinearLayout layout;
    private String name;
    private WordsDAO themeWordsDAO;
    private ArrayList<Words> words;
    private TextView[] textView;
    private LinearLayout[] ll;
    private EditText[] editText;
    private int[] randoms;
    private Button checkButton, againButton, backButton;
    private int allWordsCount;
    private int size, n;
    private ArrayList<Integer> mas;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_train);

        try {
            name = getIntent().getExtras().getString("table name");
            Log.d(" Name", name + "");
            themeWordsDAO = new WordsDAO(getApplicationContext(), name);
            words = new ArrayList<Words>();
            words = themeWordsDAO.getAllDictionaries();
            size = words.size();
            Log.d("Train", words.size() + "");


        }catch (RuntimeException e){
            setContentView(R.layout.empty);

            String button1String = "Create dictionary";
            String button2String = "Cancel";

            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Mistake");
            ad.setMessage("You don't have any themes of words. Please create new one for this.");
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
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

                }
            });
            AlertDialog alert = ad.create();
            alert.show();

        }

        layout = (LinearLayout) findViewById(R.id.linearLayout2);
        layout.setOrientation(LinearLayout.VERTICAL);

        textView = new TextView[size];
        ll = new LinearLayout[size];
        editText = new EditText[size];

        randoms = new int[size];
        //int num = 0;

        mas = new ArrayList<Integer>();
        for(int j = 0; j< size; j++){
            mas.add(j);
        }

        n = size;
        int num;
        random = new Random();

        for (int i = 0; i < size; i++) {

            num = random.nextInt(n - i);
            randoms[i] = mas.remove(num);

            textView[i] = new TextView(this);
            textView[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            textView[i].setText(words.get(randoms[i]).getFirstLang());
            textView[i].setId(i);
            textView[i].setEms(10);

            editText[i] = new EditText(this);
            editText[i].setId(i);
            editText[i].setEms(10);

            ll[i] = new LinearLayout(this);
            ll[i].setOrientation(LinearLayout.HORIZONTAL);
            ll[i].addView(textView[i]);
            ll[i].addView(editText[i]);

            layout.addView(ll[i]);
        }


        checkButton = new Button(this);
        checkButton.setText("Check");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < size; i++) {
                    String answer = editText[i].getText().toString();
                    int temp = randoms[i];
                    if (!answer.equals(words.get(temp).getSecondLang())) {
                        editText[i].setBackgroundColor(Color.RED);
                    }
                }
                try {
                    layout.removeView(againButton);
                }catch (RuntimeException e){

                }

                    againButton = new Button(getApplicationContext());
                    againButton.setText("Again");
                    againButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), TestTrain.class);
                            i.putExtra("table name", name);
                            startActivity(i);
                            finish();
                        }
                    });
                    layout.addView(againButton);

            }
        });
        layout.addView(checkButton);


        backButton = new Button(getApplicationContext());
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layout.addView(backButton);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_train, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
