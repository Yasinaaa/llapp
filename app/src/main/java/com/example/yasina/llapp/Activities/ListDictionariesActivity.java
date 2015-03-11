package com.example.yasina.llapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasina.llapp.Adapter.ListDictionariesAdapter;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 11.03.15.
 */
public class ListDictionariesActivity extends Activity
        implements View.OnClickListener {
        //AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

    public static final String TAG = "ListDicitonariesActivity";

    public static final int REQUEST_CODE_ADD_DICTIONARY = 40;
    public static final String EXTRA_ADDED_DICTIONARY = "extra_key_added_DICTIONARY";

    private ListView mListviewDicitonaries;
    private TextView mTxtEmptyListDicitonaries;
    private ImageButton mBtnAddDictionary;

    private ListDictionariesAdapter mAdapter;
    private List<Dictionary> mListDictionaries;
    private DictionaryDAO mDictionaryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dictionaries);

        initViews();

        mDictionaryDao = new DictionaryDAO(this);
        mListDictionaries = mDictionaryDao.getAllDictionaries();
        if(mListDictionaries != null && !mListDictionaries.isEmpty()) {
            mAdapter = new ListDictionariesAdapter(this, mListDictionaries);
            mListviewDicitonaries.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListDicitonaries.setVisibility(View.VISIBLE);
            mListviewDicitonaries.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        this.mListviewDicitonaries = (ListView) findViewById(R.id.list_dictionaries);
        this.mTxtEmptyListDicitonaries = (TextView) findViewById(R.id.txt_empty_list_dictionaries);
        this.mBtnAddDictionary = (ImageButton) findViewById(R.id.btn_addNewDictionary_activity_list_dict);
       // this.mListviewDicitonaries.setOnItemClickListener(this);
       // this.mListviewDicitonaries.setOnItemLongClickListener(this);
        this.mBtnAddDictionary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNewDictionary_activity_list_dict:
                Intent intent = new Intent(this, AddDictionaryActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_DICTIONARY);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_DICTIONARY) {
            if(resultCode == RESULT_OK) {
                if(data != null) {
                    Dictionary createdDictionary = (Dictionary) data.getSerializableExtra(EXTRA_ADDED_DICTIONARY);
                    if(createdDictionary != null) {
                        if(mListDictionaries == null)
                            mListDictionaries = new ArrayList<Dictionary>();
                        mListDictionaries.add(createdDictionary);

                        if(mListviewDicitonaries.getVisibility() != View.VISIBLE) {
                            mListviewDicitonaries.setVisibility(View.VISIBLE);
                            mTxtEmptyListDicitonaries.setVisibility(View.GONE);
                        }

                        if(mAdapter == null) {
                            mAdapter = new ListDictionariesAdapter(this, mListDictionaries);
                            mListviewDicitonaries.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListDictionaries);
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
        mDictionaryDao.close();
    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dictionary clickedDictionary = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedDictionary.getName());
        Intent intent = new Intent(this, ListEmployeesActivity.class);
        intent.putExtra(ListEmployeesActivity.EXTRA_SELECTED_COMPANY_ID, clickedDictionary.getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Company clickedCompany = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : "+clickedCompany.getName());
        showDeleteDialogConfirmation(clickedCompany);
        return true;
    }*/

    private void showDeleteDialogConfirmation(final Dictionary clickedDicitonary) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the \""+clickedDicitonary.getName()+"\" dictionary ?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mDictionaryDao != null) {
                    mDictionaryDao.deleteDictionary(clickedDicitonary);
                    mListDictionaries.remove(clickedDicitonary);

                    if(mListDictionaries.isEmpty()) {
                        mListDictionaries = null;
                        mListviewDicitonaries.setVisibility(View.GONE);
                        mTxtEmptyListDicitonaries.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListDictionaries);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListDictionariesActivity.this,"dictionary_deleted_successfully", Toast.LENGTH_SHORT).show();
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
}
