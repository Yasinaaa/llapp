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
import android.widget.TextView;

import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;
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
    private Random random;
    private int allWordsCount;
    private ArrayList<Integer> input;

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
            Log.d("Train", words.size() + "");

            layout = (LinearLayout) findViewById(R.id.linearLayout2);
            layout.setOrientation(LinearLayout.VERTICAL);

            textView = new TextView[3];
            ll = new LinearLayout[3];
            editText = new EditText[3];

            random = new Random();
            randoms = new int[3];
            int num = 0;

            input = new ArrayList<Integer>();

            for (int i = 0; i < 3; i++) {
                num = random.nextInt(3);
                //randoms[i] = num;
                RandomFour randomFour = new RandomFour(num, input);
                num = randomFour.generate();

                textView[num] = new TextView(this);
                textView[num].setText(words.get(num).getFirstLang());
                textView[num].setId(i);
                textView[num].setEms(10);

                editText[num] = new EditText(this);
                editText[num].setId(num);
                editText[num].setEms(10);

                ll[num] = new LinearLayout(this);
                ll[num].setOrientation(LinearLayout.HORIZONTAL);
                ll[num].addView(textView[num]);
                ll[num].addView(editText[num]);

                layout.addView(ll[num]);
            }


            checkButton = new Button(this);
            checkButton.setText("Check");
            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < 3; i++) {
                        String answer = editText[i].getText().toString();
                        int temp = input.get(i);
                        if (!answer.equals(words.get(temp).getSecondLang())) {
                            textView[i].setBackgroundColor(Color.RED);
                            editText[i].setBackgroundColor(Color.RED);


                        }
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
        }catch (Exception e){
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

    class RandomFour
    {
        private final ArrayList<Integer> input;
        private final Random rnd;
        private final int Count;
        private int genCount=0;
        public RandomFour(int in, ArrayList<Integer> input)
        {
            this.input = input;
            Count=in;
            rnd=new Random(in);
        }

        public int generate()
        {
            if (genCount>=Count)
                return -1;
            int next;
            do
            {
                next = rnd.nextInt(Count);
            }
            while (!input.add(next));
            genCount++;
            return next;
        }
    }
}
