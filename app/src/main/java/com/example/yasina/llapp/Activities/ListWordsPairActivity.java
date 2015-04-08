package com.example.yasina.llapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yasina.llapp.Adapter.ListWordsAdapter;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;
import com.example.yasina.llapp.train.TrainWordsActivity;

import java.util.ArrayList;
import java.util.StringTokenizer;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * Created by yasina on 11.03.15.
 */
public class ListWordsPairActivity
        extends Activity
        implements OnItemLongClickListener, OnItemClickListener, OnClickListener{

    public static final String TAG = "ListWordsPairActivity";

    public static final int REQUEST_CODE_ADD_WORDS_PAIR = 40;
    public static final String EXTRA_ADDED_WORDS_PAIR = "extra_key_added_words_pair";

    private ListView mListviewWP;
    private TextView mTxtEmptyWP;
    private ImageButton mBtnAddWP;

    private ListWordsAdapter mAdapter;
    private ArrayList<Words> mListWP;
    private WordsDAO mWordsPairDao;
    private ArrayList<Words> forTest;
    private String name2 = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_words_pair);

        DictionaryDAO dicDAO = new DictionaryDAO(this);
        Dictionary dic = dicDAO.getDicitonaryById(MainMenuActivity.clickedDictionary);

        StringTokenizer tokenizer = new StringTokenizer(dic.getName(),"-");
        while (tokenizer.hasMoreElements()) {
            name2 = tokenizer.nextToken() + "_" + tokenizer.nextToken();
        }

        initViews();

        mWordsPairDao = new WordsDAO(this,name2);
        mListWP = mWordsPairDao.getAllDictionaries();
        if(mListWP != null && !mListWP.isEmpty()) {

            mAdapter = new ListWordsAdapter(this,R.layout.list_item_words_pair,mListWP);
            mListviewWP.setAdapter(mAdapter);

            mListviewWP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Words clickedWordsPair = ((Words) adapterView.getItemAtPosition(i));
                    forTest.add(clickedWordsPair);
                    Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
                  /*  Toast.makeText(getApplicationContext(), ((Words)
                            adapterView.getItemAtPosition(i)).getFirstLang(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "clickedItem : " + i);//clickedWordsPair.getFirstLang());*/


                }
            });
        }
        else {
            mTxtEmptyWP.setVisibility(View.VISIBLE);
            mListviewWP.setVisibility(View.GONE);
        }
       /* mListviewWP.setOnItemClickListener(
                new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Words clickedWordsPair = mAdapter.getItem(position);
                        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
                        forTest.add(clickedWordsPair);


                    }
                }
        );*/
    }

    private void initViews() {
        this.mListviewWP = (ListView) findViewById(R.id.list_wps);
        this.mTxtEmptyWP = (TextView) findViewById(R.id.txt_empty_list_wp);
     //   this.mBtnAddWP = (ImageButton) findViewById(R.id.btn_addNewDWP_activity_list_wp);
        this.mListviewWP.setOnItemClickListener(this);
        this.mListviewWP.setOnItemLongClickListener(this);
//        this.mBtnAddWP.setOnClickListener(this);
       forTest = new ArrayList<Words>();
    }

   private String name;
   private WordsDAO wordsDAO_new;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_words_list_to_theme:
               /* Intent intent = new Intent(this, AddWordsPairActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_WORDS_PAIR);*/
          //      break;


                final EditText input = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListWordsPairActivity.this);
	               builder.setTitle("Write here your words theme name")
                          .setView(input)
		                  .setNegativeButton("OK",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          name = input.getText().toString();
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
                                        name = input2.getText().toString();
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

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_WORDS_PAIR) {
            if(resultCode == RESULT_OK) {
                if(data != null) {
                    Words createdDictionary = (Words) data.getSerializableExtra(EXTRA_ADDED_WORDS_PAIR);
                    if(createdDictionary != null) {
                        if(mListWP == null)
                            mListWP = new ArrayList<Words>();
                        mListWP.add(createdDictionary);

                        if(mListviewWP.getVisibility() != View.VISIBLE) {
                            mListviewWP.setVisibility(View.VISIBLE);
                            mTxtEmptyWP.setVisibility(View.GONE);
                        }

                        if(mAdapter == null) {
                            mAdapter = new ListWordsAdapter(this, mListWP);
                            mListviewWP.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListWP);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWordsPairDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Words clickedWordsPair = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
        //forTest.add(clickedWordsPair);
        // Intent intent = new Intent(this, ListEmployeesActivity.class);
        // intent.putExtra(ListEmployeesActivity.EXTRA_SELECTED_COMPANY_ID, clickedWordsPair.getImage());
        // startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Words clickedWordsPair = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedWordsPair.getFirstLang());
       // forTest.add(clickedWordsPair);
        return true;
    }


 /*   private void showDeleteDialogConfirmation(final Words clickedDicitonary) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the \""+clickedDicitonary.getmId()+"\" dictionary ?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mWordsPairDao != null) {
                    mWordsPairDao.deleteDictionary(clickedDicitonary);
                    mListWP.remove(clickedDicitonary);

                    if(mListWP.isEmpty()) {
                        mListWP = null;
                        mListviewWP.setVisibility(View.GONE);
                        mTxtEmptyWP.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListWP);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListWordsPairActivity.this, "dictionary_deleted_successfully", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNeutralButton("no", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/


 }

