package com.example.yasina.llapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasina.llapp.Adapter.ListWordsAdapter;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 11.03.15.
 */
public class ListWordsPairActivity {
        /*extends Activity
        implements View.OnClickListener {
    //AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

    public static final String TAG = "ListWordsPairActivity";

    public static final int REQUEST_CODE_ADD_WORDS_PAIR = 40;
    public static final String EXTRA_ADDED_WORDS_PAIR = "extra_key_added_words_pair";

    private ListView mListviewWP;
    private TextView mTxtEmptyWP;
    private ImageButton mBtnAddWP;

    private ListWordsAdapter mAdapter;
    private List<Words> mListWP;
    private WordsDAO mWordsPairDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_words_pair);

        initViews();

        mWordsPairDao = new WordsDAO(this);
        mListWP = mWordsPairDao.getAllDictionaries();
        if(mListWP != null && !mListWP.isEmpty()) {
            mAdapter = new ListWordsAdapter(this, mListWP);
            mListviewWP.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyWP.setVisibility(View.VISIBLE);
            mListviewWP.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        this.mListviewWP = (ListView) findViewById(R.id.list_wps);
        this.mTxtEmptyWP = (TextView) findViewById(R.id.txt_empty_list_wp);
        this.mBtnAddWP = (ImageButton) findViewById(R.id.btn_addNewDWP_activity_list_wp);
        this.mBtnAddWP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNewDWP_activity_list_wp:
                Intent intent = new Intent(this, AddWordsPairActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_WORDS_PAIR);
                break;

            default:
                break;
        }
    }

    @Override
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWordsPairDao.close();
    }


    private void showDeleteDialogConfirmation(final Words clickedDicitonary) {
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
    }
    */
}

