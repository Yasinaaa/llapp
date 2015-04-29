package com.example.yasina.llapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.DAO.DictionaryDAO;
import com.example.yasina.llapp.DAO.WordsDAO;
import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.forPainting.GlobalBitmap;
import com.example.yasina.llapp.forPainting.MainPaint;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;
import com.example.yasina.llapp.forPainting.GlobalBitmap;


public class AddWordsActivity extends SherlockFragmentActivity  implements View.OnClickListener {

    public static final String TAG = "AddWordsActivity";

    private StringTokenizer tokenizer;
    private String firstL, secondL;
    private TextView tvFirstL, tvSecondL;
    private DictionaryDAO dicDAO;


    private EditText mTxtFirstLang, mTxtSecondLang, mTxtExplanation;
    private Button mBtnAdd;
    private ImageView mImage;
    private byte[] img=null;

    private WordsDAO wordsDAO;
    private static final  int forPicture =0;
    private String name = "";
   // @Override
   // public View onCreate(){
    //View(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_words);
        tvFirstL = (TextView) findViewById(R.id.tvFirstLangAddWord);
        tvSecondL = (TextView) findViewById(R.id.tvSecondLangAddWord);

        initViews();
        this.wordsDAO = new WordsDAO(this);
        dicDAO = new DictionaryDAO(this);
       Dictionary dic = dicDAO.getDicitonaryById(MainMenuActivity.clickedDictionary);

        StringTokenizer tokenizer = new StringTokenizer(dic.getName(),"-");
        //StringTokenizer tokenizer = new StringTokenizer("russian-english","-");
        while (tokenizer.hasMoreElements()) {
            firstL = tokenizer.nextToken();
            secondL = tokenizer.nextToken();
            name = firstL + "_" + secondL;
        }
        tvFirstL.setText(firstL);
        tvSecondL.setText(secondL);

       // View view = inflater.inflate(R.layout.add_words, container, false);

      /*  actionBar = getSupportActionBar();
                //getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(getString(R.string.learn_words_fragment));*/


        //  return view;
    }

    private void initViews() {
        this.mTxtFirstLang = (EditText) findViewById(R.id.etNewWord);
        this.mTxtSecondLang = (EditText) findViewById(R.id.etNewWordTranslate);
        this.mTxtExplanation =  (EditText) findViewById(R.id.etNewWordAssociation);
        this.mImage = (ImageView) findViewById(R.id.ivPictureOfWord);



        this.mBtnAdd = (Button) findViewById(R.id.btnADD_addWordActivity);
        //this.mBtnAdd.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnADD_addWordActivity:

                initViews();
               this.wordsDAO = new WordsDAO(this,name);
               // this.wordsDAO = new WordsDAO(this);

                if (mTxtFirstLang != null && mTxtSecondLang != null && mTxtExplanation != null
                        && mImage != null) {
                    //mImage.isDrawingCacheEnabled()) {

                    Words createdWordsPair = wordsDAO.createWordsPair(
                            mTxtFirstLang.getText().toString(), mTxtSecondLang.getText().toString(), img, mTxtExplanation.getText().toString());

                    Log.d("ADD WORD", "added word pair : " + createdWordsPair.getmId());

                    Toast.makeText(this, "word pair_created_successfully", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "empty_fields_message", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.ivPictureOfWord:
                Intent request = new Intent(AddWordsActivity.this, MainPaint.class);
                //startActivity(request);
                startActivityForResult(new Intent(getApplicationContext(),MainPaint.class),1);
                break;
            default:
                break;

        }
    }




  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //mImage.setImageBitmap(MainPaint.bit);
      // if (data == null) {return;
      Bitmap d = GlobalBitmap.img;

      Log.d("hi",d.getWidth() + " - " + d.getHeight());
     // mImage.setImageBitmap( );
      Log.d("hi","im here");
     // Bitmap b= BitmapFactory.decodeResource(getResources(), R.drawable.paint);
      ByteArrayOutputStream bos=new ByteArrayOutputStream();
      d.compress(Bitmap.CompressFormat.PNG, 100, bos);
      img = bos.toByteArray();


       mImage = (ImageView) findViewById(R.id.ivPictureOfWord);
       mImage.setImageResource(R.drawable.checkmark);

/*      switch (requestCode) {
          case forPicture:
              if (resultCode == RESULT_OK) {
                  Intent intent = getIntent();
                  Bundle extras = intent.getExtras();
                  Bitmap bitmap = (Bitmap) extras.getParcelable("BitmapImage");
                  Log.d("ok", "ok");
                 // mImage.setImageBitmap(bitmap);

                 //



              }*/
      }


     /*  Bitmap btp_img = null;
       InputStream in_stream;

       if (resultCode == MainPaint.RESULT_OK && requestCode == 1)
       {
           //Intent intent = getIntent();
           Bundle extras = getIntent().getExtras();
           //Bitmap bmp = (Bitmap) extras.getParcelable("lala");
           Bitmap bitmap = (Bitmap) extras.getParcelable("BitmapImage");

           try {

               byte[] picBytes = data.getExtras().getByteArray("BitmapImage");
               Toast.makeText(this,picBytes.length + "",Toast.LENGTH_LONG).show();
               in_stream = getContentResolver().openInputStream(
                       data.getData());
               btp_img = BitmapFactory.decodeStream(in_stream);
               in_stream.close();
           } catch (IOException e) {
               e.printStackTrace();
           }*/


          // mImage.setImageBitmap(bitmap);
      // }

        /*  try {
               if (btp_img != null) {
                   btp_img.recycle();
               }
               //Bundle extras = getIntent().getExtras();
               //Bitmap bmp = (Bitmap) extras.getParcelable("lala");

              byte[] picBytes = data.getExtras().getByteArray("lala");
               in_stream = getContentResolver().openInputStream(
                       data.getData());
               btp_img = BitmapFactory.decodeStream(in_stream);
               in_stream.close();
              Log.e("Mistake","Mistake");
               mImage.setImageBitmap(btp_img);
              Log.e("ko","MistakeNo");

           }  catch (IOException e) {
               e.printStackTrace();
           }
       super.onActivityResult(requestCode, resultCode, data);*/

       /* byte[] picture = data.getByteArrayExtra("picture");
        Toast toast2 = Toast.makeText(this,
                picture.length, Toast.LENGTH_SHORT);
        toast2.show();
        Bitmap bitmap = null;

        if(picture !=null){
            bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length+1);
            mImage.setImageBitmap(bitmap);
        }

        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "your bitmap is null!", Toast.LENGTH_SHORT);
            toast.show();
        }
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img=bos.toByteArray();*/
      /* */


   /* private ActionBar actionBar;
    private ViewPager pager;
    // private ActionBar.Tab tab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(getString(R.string.add_words_fragment));

        ViewPager.SimpleOnPageChangeListener ViewPagerListener =
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        actionBar.setSelectedNavigationItem(position);
                    }
                };

        pager = (ViewPager) view.findViewById(R.id.pager_color);
        pager.setOnPageChangeListener(ViewPagerListener);

        FragmentManager fm = getChildFragmentManager();
        ViewPagerColorAdapter viewPagerScheduleAdapter = new ViewPagerColorAdapter(fm);
        pager.setAdapter(viewPagerScheduleAdapter);

      /*  ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());

                SherlockFragmentActivity sherlockFragmentActivity = getSherlockActivity();
                if (sherlockFragmentActivity != null) {
                    SlidingMenu slidingMenu = ((MainMenuActivity) sherlockFragmentActivity).getMenu();
                    if (tab.getPosition() == 0) {
                        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    } else {
                        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                    }
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };

       // tab = actionBar.newTab().setText("Document").setTabListener(tabListener);
       // actionBar.addTab(tab);

        //tab = actionBar.newTab().setText("Write now").setTabListener(tabListener);
        //actionBar.addTab(tab);

        return view;


    }*/

}

