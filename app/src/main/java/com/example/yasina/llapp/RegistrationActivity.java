package com.example.yasina.llapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.ArrayList;


public class RegistrationActivity extends SherlockFragmentActivity {

    private final int add_langs_conection = 1;
   /* private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

         text = (TextView) findViewById(R.id.tvYoursLangsChoose);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
           case R.id.ibAddLanguages:
                showDialog(add_langs_conection);
                break;
           case R.id.ibShowPassword:

                break;
            case R.id.btnSingIn:
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {

            case add_langs_conection:
                final String[] checkLangsName = {"English", "Русский", "Francais","Espanol","Japanese","Arabian"};
                final boolean[] mCheckedItems = new boolean[checkLangsName.length];
                final boolean[] mCheckedItem2 = {true,true};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Languages")
                        .setCancelable(false)
                        .setMultiChoiceItems(checkLangsName, mCheckedItems,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which, boolean isChecked) {
                                        mCheckedItems[which] = isChecked;
                                    }
                                })
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        int y = 0;
                                       // StringBuilder state = new StringBuilder();
                                        String[] newLangs = new String[2];
                                        for (int i = 0; i < checkLangsName.length; i++) {
                                            if (mCheckedItems[i] && y !=2 && y < 2) {
                                                newLangs[y] = checkLangsName[i];
                                              //  state.append(checkLangsName[i] + " choosed\n");
                                                y++;
                                            }
                                        }
                                       //Toast.makeText(getApplicationContext(),
                                         //       state.toString(), Toast.LENGTH_LONG)
                                           //     .show();

                                       text.setText(newLangs[0] + " - " + newLangs[1]);
                                    }
                                })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();

                                    }
                                });
                return builder.create();

            default:
                return null;
        }
    }
*/
}
