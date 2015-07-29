package com.example.yasina.llapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuInflater;
import com.example.yasina.llapp.Adapter.ListWordsAdapter;
import com.example.yasina.llapp.AddWordsActivity;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;
import com.example.yasina.llapp.Train.TrainWordsActivity;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class AllWords_Fragment extends SherlockFragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private View view;
    private ActionBar actionBar;
    public static final String TAG = "AllWords_Fragment";
    private ListView mListviewWP;
    private TextView mTxtEmptyWP;
    private ListWordsAdapter mAdapter;
    private ArrayList<Words> mListWP;
    private WordsDAO mWordsPairDao;
    private ArrayList<Words> forTest;
    private String name2 = "";
    private  Words clickedWordsPair;
    private Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list_words_pair, container, false);
        setHasOptionsMenu(true);
        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setTitle("All Words");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(237, 211, 140)));

        try {
            DictionaryDAO dicDAO = new DictionaryDAO(getSherlockActivity().getApplicationContext());
            Dictionary dic = dicDAO.getDicitonaryById(MainMenuActivity.clickedDictionary);

            StringTokenizer tokenizer = new StringTokenizer(dic.getName(), "-");
            while (tokenizer.hasMoreElements()) {
                name2 = tokenizer.nextToken() + "_" + tokenizer.nextToken();
            }

            initViews();

            mWordsPairDao = new WordsDAO(getSherlockActivity().getApplicationContext(), name2);

            mListWP = mWordsPairDao.getAllDictionaries();
            if (mListWP != null && !mListWP.isEmpty()) {

                mAdapter = new ListWordsAdapter(getSherlockActivity().getApplicationContext(), R.layout.list_item_words_pair, mListWP);
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
            view = inflater.inflate(R.layout.empty, container, false);


            String button1String = "Create dictionary";
            String button2String = "Cancel";

            AlertDialog.Builder ad = new AlertDialog.Builder(getSherlockActivity().getApplicationContext());
            ad.setTitle("Mistake");
            ad.setMessage("You don't have any dictionaries.Please create new one for this.");
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(getSherlockActivity().getApplicationContext(),ListDictionariesActivity.class));
                }
            });
            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    getSherlockActivity().finish();
                }
            });
            ad.setCancelable(true);
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    getSherlockActivity().finish();
                }
            });
            AlertDialog alert = ad.create();
            alert.show();
        }

        return view;
    }

    private void initViews() {
        this.mListviewWP = (ListView) view.findViewById(R.id.list_wps);
        //   this.mTxtEmptyWP = (TextView) findViewById(R.id.txt_empty_list_wp);
        this.mListviewWP.setOnItemClickListener(this);
        this.mListviewWP.setOnItemLongClickListener(this);
        forTest = new ArrayList<Words>();
    }

    private String name;
    private WordsDAO wordsDAO_new;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

           /* case R.id.btn_save_words_list_to_theme:

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

                break;*/

            default:
                break;
        }
    }
    private void showDialog() {
        String button1String = "Nothing";
        String button2String = "Delete";
        String button3String = "Change";

        AlertDialog.Builder ad = new AlertDialog.Builder(getSherlockActivity().getApplicationContext());
        ad.setTitle("Change");
        ad.setMessage("What do you wanna do with this word?");
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                getSherlockActivity().finish();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                WordsDAO wordDAO = new WordsDAO(getSherlockActivity().getApplicationContext());
                wordDAO.deleteDictionary(clickedWordsPair, name2);
                wordDAO.deleteFromThemes(clickedWordsPair);
                startActivity(new Intent(getSherlockActivity().getApplicationContext(),ListWordsPairActivity.class));
            }
        });
        ad.setNeutralButton(button3String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent i = new Intent(getSherlockActivity().getApplicationContext(),AddWordsActivity.class);
                i.putExtra("id",clickedWordsPair.getmId());
                startActivity(i);
            }
        });

        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                getSherlockActivity().finish();
            }
        });
        AlertDialog alert = ad.create();
        alert.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Words clickedWordsPair = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        clickedWordsPair = mAdapter.getItem(position);

        showDialog();
        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {

        switch (item.getItemId()) {

            case R.id.btn_train_words_list_to_theme:

                final EditText input2 = new EditText(getSherlockActivity().getApplicationContext());
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getSherlockActivity());
                builder2.setTitle("Write here your words theme name")
                        .setView(input2)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        name = input2.getText().toString() + "_theme";
                                        wordsDAO_new = new WordsDAO(getSherlockActivity().getApplicationContext(),name);

                                        wordsDAO_new.addListOfWords(forTest);
                                        Log.d(TAG, "" + wordsDAO_new.getAllDictionaries().size());
                                        Intent intent = new Intent(getSherlockActivity().getApplicationContext(), TrainWordsActivity.class);
                                        intent.putExtra("table name", name);
                                        startActivity(intent);
                                        dialog.cancel();
                                       // getSherlockActivity().finish();
                                    }
                                });
                AlertDialog alert2 = builder2.create();
                alert2.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, MenuInflater inflater) {

        getSherlockActivity().getSupportMenuInflater().inflate(R.menu.menu_list_words, menu);

      /*  saveBtn = (Button) view.findViewById(R.id.btn_train_words_list_to_theme);
        saveBtn.setOnClickListener(this);*/
        super.onCreateOptionsMenu(menu, inflater);
    }

}
