package com.example.yasina.llapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.R;

/**
 * Created by yasina on 11.03.15.
 */
public class AddDictionaryActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG = "AddDictionaryActivity";

    private EditText mTxtFirstLang, mTxtSecondLang;
    private Button mBtnAdd;
    private String[] types = {"translate","synonyms","homonyms","explanatory"};
    private Spinner spinner;
    private DictionaryDAO mDictionaryDao;
    private  ArrayAdapter<String> adapter;
    private  String clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_dictionary);

        initViews();

        this.mDictionaryDao = new DictionaryDAO(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner_TypeOfDictionaries_add_new_d);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);

    }

    private void initViews() {
        this.mTxtFirstLang = (EditText) findViewById(R.id.txt_firstLanguage_add_new_dic);
        this.mTxtSecondLang = (EditText) findViewById(R.id.txt_secondLanguage_add_new_dic);
        this.mBtnAdd = (Button) findViewById(R.id.btn_addNewDictionaryTable_add_new_d);

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNewDictionaryTable_add_new_d:
                Editable firstLang = mTxtFirstLang.getText();
                Editable secondLang = mTxtSecondLang.getText();
                clicked = spinner.getSelectedItem().toString();
                if (!TextUtils.isEmpty(firstLang) && !TextUtils.isEmpty(secondLang)) {
                    Dictionary createdDictionary = mDictionaryDao.createDictionary(
                            firstLang.toString() + "-" + secondLang.toString(),clicked);

                    Log.d(TAG, "added dictionary : " + createdDictionary.getName());
                    Intent intent = new Intent();
                    intent.putExtra(ListDictionariesActivity.EXTRA_ADDED_DICTIONARY, createdDictionary);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(this,"dictionary_created_successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(this,"empty_fields_message", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clicked = spinner.getSelectedItem().toString();
        Log.d(TAG, "clickedItem : "+clicked);

       // Intent intent = new Intent(this, ListEmployeesActivity.class);
       // intent.putExtra(ListEmployeesActivity.EXTRA_SELECTED_COMPANY_ID, clickedCompany.getId());
       // startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDictionaryDao.close();
    }
}
