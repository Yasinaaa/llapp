package com.example.yasina.llapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.Activities.ListDictionariesActivity;
import com.example.yasina.llapp.Activities.ListWordsPairActivity;
import com.example.yasina.llapp.Adapter.DictionariesSpinner;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.Notification.NotificationWordActivity;
import com.example.yasina.llapp.Train.MenuTrainActivity;
import com.example.yasina.llapp.forPainting.GlobalBitmap;
import com.example.yasina.llapp.forPainting.MainPaint;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;
import com.example.yasina.llapp.forPainting.GlobalBitmap;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class AddWordsActivity extends SherlockFragmentActivity  implements View.OnClickListener {

    public static final String TAG = "AddWordsActivity";

    private String firstL, secondL;
    private TextView tvFirstL, tvSecondL;
    private DictionaryDAO dicDAO;
    private EditText mTxtFirstLang, mTxtSecondLang, mTxtExplanation;
    private Button mBtnAdd;
    private ImageView mImage;
    private byte[] img=null;
    private WordsDAO wordsDAO;
    private String name = "";
    private int currentMenuPosition = -1;
    private SlidingMenu menu;
    private Dictionary dic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_words);
        getActionBar().setTitle("Add Words");
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(235,170,91)));

        tvFirstL = (TextView) findViewById(R.id.tvFirstLangAddWord);
        tvSecondL = (TextView) findViewById(R.id.tvSecondLangAddWord);

        initViews();

        try {

           this.wordsDAO = new WordsDAO(this);
           dicDAO = new DictionaryDAO(this);

           dic = dicDAO.getDicitonaryById(MainMenuActivity.clickedDictionary);

           StringTokenizer tokenizer = new StringTokenizer(dic.getName(),"-");

           while (tokenizer.hasMoreElements()) {
               firstL = tokenizer.nextToken();
               secondL = tokenizer.nextToken();
               name = firstL + "_" + secondL;
           }
           tvFirstL.setText(firstL);
           tvSecondL.setText(secondL);

       }catch (Exception e1){

        setContentView(R.layout.empty);

        String button1String = "Create dictionary";
        String button2String = "Cancel";

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Mistake");
        ad.setMessage("You don't have any dictionaries.Please create new one for this.");
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                startActivity(new Intent(getApplicationContext(),ListDictionariesActivity.class));
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
                   finish();
            }
        });
           AlertDialog alert = ad.create();
           alert.show();
    }

        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBackgroundColor(0xFF333333);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
        menu.setSelectorDrawable(R.drawable.sidemenu_items_background);

        ((ListView) findViewById(R.id.sidemenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuToggle();

                if (currentMenuPosition != position)
                    changeFragment(position);

                currentMenuPosition = position;
            }
        });

       /* if (currentMenuPosition != -1) {
            ((ListView) findViewById(R.id.sidemenu)).setItemChecked(currentMenuPosition, true);
        }*/

        String[] items = {"Main","All Words",getString(R.string.add_words_fragment),"Train Words Theme"

        };

        ((ListView) findViewById(R.id.sidemenu)).setAdapter(
                new ArrayAdapter<Object>(
                        this,
                        R.layout.sidemenu_item,
                        R.id.text,
                        items
                )
        );

        this.menu = menu;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        this.mTxtFirstLang = (EditText) findViewById(R.id.etNewWord);
        this.mTxtSecondLang = (EditText) findViewById(R.id.etNewWordTranslate);
        this.mTxtExplanation =  (EditText) findViewById(R.id.etNewWordAssociation);
        this.mImage = (ImageView) findViewById(R.id.ivPictureOfWord);
        this.mBtnAdd = (Button) findViewById(R.id.btnADD_addWordActivity);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnADD_addWordActivity:

               initViews();
               this.wordsDAO = new WordsDAO(this,name);

                if (mTxtFirstLang != null && mTxtSecondLang != null && mTxtExplanation != null
                        && mImage != null) {

                    Words createdWordsPair = wordsDAO.createWordsPair(
                            mTxtFirstLang.getText().toString(), mTxtSecondLang.getText().toString(), img, mTxtExplanation.getText().toString());

                    Log.d("ADD WORD", "added word pair : " + createdWordsPair.getmId());

                    Toast.makeText(this, "word pair_created_successfully", Toast.LENGTH_LONG).show();
                    this.mTxtFirstLang.setText("");
                    this.mTxtSecondLang.setText("");
                    this.mTxtExplanation.setText("");
                    mImage.setImageResource(R.drawable.paint);
                } else {
                    Toast.makeText(this, "empty_fields_message", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.ivPictureOfWord:
                Intent request = new Intent(AddWordsActivity.this, MainPaint.class);
                startActivityForResult(new Intent(getApplicationContext(),MainPaint.class),1);
                break;
            default:
                break;

        }
    }




  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      Bitmap d = GlobalBitmap.img;
     // Log.d("hi",d.getWidth() + " - " + d.getHeight());
     // Log.d("hi","im here");

      ByteArrayOutputStream bos=new ByteArrayOutputStream();
      try {
          d.compress(Bitmap.CompressFormat.PNG, 100, bos);
          img = bos.toByteArray();

          mImage = (ImageView) findViewById(R.id.ivPictureOfWord);
          mImage.setImageResource(R.drawable.checkmark);
      }catch (RuntimeException e){

      }

      }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if(menu.isMenuShowing()){
                menu.toggle(true);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void changeFragment(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(),ListWordsPairActivity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(),AddWordsActivity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), MenuTrainActivity.class));
                break;
        }
    }
    public SlidingMenu getMenu() {
        return menu;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuToggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuToggle(){
        if(menu.isMenuShowing())
            menu.showContent();
        else
            menu.showMenu();
    }


}

