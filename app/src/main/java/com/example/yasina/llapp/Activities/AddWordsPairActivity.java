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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

/**
 * Created by yasina on 11.03.15.
 */
public class AddWordsPairActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "AddWordsPairctivity";

    private EditText mTxtFirstLang, mTxtSecondLang, mTxtExplanation;
    private Button mBtnAdd;
    private Editable firstLang, secondLang, explantion;
    private ImageView mImage;

    private WordsDAO mWordsPairDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_words);

        initViews();
        String sql = mTxtFirstLang.getText().toString() + mTxtSecondLang.getText().toString();
        this.mWordsPairDAO = new WordsDAO(this, sql);
    }

    private void initViews() {
        this.mTxtFirstLang = (EditText) findViewById(R.id.etNewWord);
        this.mTxtSecondLang = (EditText) findViewById(R.id.etNewWordTranslate);
        this.mTxtExplanation =  (EditText) findViewById(R.id.etNewWordAssociation);
        this.mImage = (ImageView) findViewById(R.id.ivPictureOfWord);
        this.mBtnAdd = (Button) findViewById(R.id.btn_addNewWordsPairTable_add_new_wp);
        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.btnADD_addWordActivity:

                if (!TextUtils.isEmpty(firstLang) && !TextUtils.isEmpty(secondLang)
                        && !TextUtils.isEmpty(firstLang)){
                        //mImage.isDrawingCacheEnabled()) {
                    Words createdWordsPair = mWordsPairDAO.createWordsPair(
                            firstLang.toString(), secondLang.toString(),);

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
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWordsPairDAO.close();
    }
}
