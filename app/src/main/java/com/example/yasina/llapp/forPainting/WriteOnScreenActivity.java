package com.example.yasina.llapp.forPainting;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.yasina.llapp.R;

/**
 * Created by yasina on 17.03.15.
 */
public class WriteOnScreenActivity extends SherlockFragmentActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(new TouchEventView(this, null));
        }


    }

