package com.example.yasina.llapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yasina.llapp.DAO.WordsPairDAO;
import com.example.yasina.llapp.Model.WordsPair;
import com.example.yasina.llapp.R;

/**
 * Created by yasina on 11.03.15.
 */
public class AddWordsPairActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "AddWordsPairctivity";

    private EditText mTxtFirstLang, mTxtSecondLang;
    private Button mBtnAdd;
    private  Editable firstLang, secondLang;

    private WordsPairDAO mWordsPairDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_words_pair);

        initViews();
        String sql = mTxtFirstLang.getText().toString() + mTxtSecondLang.getText().toString();
        this.mWordsPairDAO = new WordsPairDAO(this, sql);
    }

    private void initViews() {
        this.mTxtFirstLang = (EditText) findViewById(R.id.txt_firstLanguage_add_new_wp);
        this.mTxtSecondLang = (EditText) findViewById(R.id.txt_secondLanguage_add_new_wp);
        this.mBtnAdd = (Button) findViewById(R.id.btn_addNewWordsPairTable_add_new_wp);
        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNewWordsPairTable_add_new_wp:

                if (!TextUtils.isEmpty(firstLang) && !TextUtils.isEmpty(secondLang)) {
                    WordsPair createdWordsPair = mWordsPairDAO.createWordsPair(
                            firstLang.toString(), secondLang.toString());

                    Log.d(TAG, "added word pair : " + createdWordsPair.getmId());
                    Intent intent = new Intent();
                    intent.putExtra(ListWordsPairActivity.EXTRA_ADDED_WORDS_PAIR, createdWordsPair);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(this, "word pair_created_successfully", Toast.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        mWordsPairDAO.close();
    }
}
