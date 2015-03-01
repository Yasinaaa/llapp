package com.example.yasina.llapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainMenuActivity extends SherlockFragmentActivity {

    private static int currentMenuPosition = -1;
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            SlidingMenu menu = new SlidingMenu(this);
            menu.setMode(SlidingMenu.LEFT);
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            menu.setFadeDegree(0.35f);
            menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
            menu.setMenu(R.layout.sidemenu);
            menu.setBackgroundColor(0xFF333333);
            //menu.setBackgroundColor(EBAA5B);
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

            if (currentMenuPosition != -1) {
                ((ListView) findViewById(R.id.sidemenu)).setItemChecked(currentMenuPosition, true);
            }

            String[] items = {getString(R.string.add_words_fragment),getString(R.string.learn_words_fragment), getString(R.string.add_languages_fragmnet),
                    getString(R.string.new_words_theme_fragment), getString(R.string.create_new_lang_connection_fragment), getString(R.string.add_teacher_fragment),
                    getString(R.string.all_words_fragment), getString(R.string.create_test_fragment), getString(R.string.settings_fragment)};

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
                showFragment(new FirstFragment());
                break;
            case 1:
                showFragment(new SecondFragment());
                break;
        }
    }

    private void showFragment(Fragment currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, currentFragment)
                .commit();
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

