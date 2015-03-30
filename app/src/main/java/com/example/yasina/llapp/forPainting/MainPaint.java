package com.example.yasina.llapp.forPainting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yasina.llapp.AddWordsActivity;
import com.example.yasina.llapp.MainMenuActivity;
import com.example.yasina.llapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by yasina on 25.03.15.
 */
public class MainPaint extends Activity implements View.OnClickListener {

        //custom drawing view
        private DrawingView drawView;
        //buttons
        private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
        //sizes
        private float smallBrush, mediumBrush, largeBrush;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.picture_painting);

            //get drawing view
            drawView = (DrawingView)findViewById(R.id.drawing);

            //get the palette and first color button
            LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
            currPaint = (ImageButton)paintLayout.getChildAt(0);
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

            //sizes from dimensions
            smallBrush = getResources().getInteger(R.integer.small_size);
            mediumBrush = getResources().getInteger(R.integer.medium_size);
            largeBrush = getResources().getInteger(R.integer.large_size);

            //draw button
            drawBtn = (ImageButton)findViewById(R.id.draw_btn);
            drawBtn.setOnClickListener(this);

            //set initial size
            drawView.setBrushSize(mediumBrush);

            //erase button
            eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
            eraseBtn.setOnClickListener(this);

            //new button
            newBtn = (ImageButton)findViewById(R.id.new_btn);
            newBtn.setOnClickListener(this);

            //save button
            saveBtn = (ImageButton)findViewById(R.id.save_btn);
            saveBtn.setOnClickListener(this);
        }

        //user clicked paint
        public void paintClicked(View view){
            //use chosen color

            //set erase false
            drawView.setErase(false);
            drawView.setBrushSize(drawView.getLastBrushSize());

            if(view!=currPaint){
                ImageButton imgView = (ImageButton)view;
                String color = view.getTag().toString();
                drawView.setColor(color);
                //update ui
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint222));
                currPaint=(ImageButton)view;
            }
        }

    public byte[] getPicture()
    {
        drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache(true);

        Bitmap bitmap = Bitmap.createBitmap(drawView.getWidth(),drawView.getHeight(), Bitmap.Config.ARGB_8888);
       /* drawView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        drawView.layout(0, 0, drawView.getMeasuredWidth(), drawView.getMeasuredHeight());

        drawView.buildDrawingCache(true);
        Bitmap bitmap = drawView.getDrawingCache();*/

        ByteArrayOutputStream stream = null;

        if(bitmap!=null){
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        }
        else{
            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
            unsavedToast.show();
        }
        drawView.destroyDrawingCache();

        return stream.toByteArray();

    }

        @Override
        public void onClick(View view){

            if(view.getId()==R.id.draw_btn){
                //draw button clicked
                final Dialog brushDialog = new Dialog(this);
                brushDialog.setTitle("Brush size:");
                brushDialog.setContentView(R.layout.brush_chooser);
                //listen for clicks on size buttons
                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(false);
                        drawView.setBrushSize(smallBrush);
                        drawView.setLastBrushSize(smallBrush);
                        brushDialog.dismiss();
                    }
                });
                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(false);
                        drawView.setBrushSize(mediumBrush);
                        drawView.setLastBrushSize(mediumBrush);
                        brushDialog.dismiss();
                    }
                });
                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(false);
                        drawView.setBrushSize(largeBrush);
                        drawView.setLastBrushSize(largeBrush);
                        brushDialog.dismiss();
                    }
                });
                //show and wait for user interaction
                brushDialog.show();
            }
            else if(view.getId()==R.id.erase_btn){
                //switch to erase - choose size
                final Dialog brushDialog = new Dialog(this);
                brushDialog.setTitle("Eraser size:");
                brushDialog.setContentView(R.layout.brush_chooser);
                //size buttons
                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(true);
                        drawView.setBrushSize(smallBrush);
                        brushDialog.dismiss();
                    }
                });
                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(true);
                        drawView.setBrushSize(mediumBrush);
                        brushDialog.dismiss();
                    }
                });
                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(true);
                        drawView.setBrushSize(largeBrush);
                        brushDialog.dismiss();
                    }
                });
                brushDialog.show();
            }
            else if(view.getId()==R.id.new_btn){
                //new button
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("New drawing");
                newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
                newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        drawView.startNew();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                newDialog.show();
            }
            else {
                if (view.getId() == R.id.save_btn) {

                    /*AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                    saveDialog.setTitle("Save drawing");
                    saveDialog.setMessage("Save drawing to device Gallery?");
                    AlertDialog.Builder builder = saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {*/

                            //Intent intent = new Intent(getApplicationContext(), AddWordsActivity.class);
                          //  Paint p = drawView.getPicture();
                        /*intent.putExtra("picture",getPicture());
                        setResult(RESULT_OK, intent);*/


                            Log.d("bitmap", "strm");


                               drawView.setDrawingCacheEnabled(true);
                               Bitmap bm = drawView.getDrawingCache();
                    Log.d("bm", "used");

                           /* File fPath = Environment.getExternalStorageDirectory();
                            File f = null;
                            f = new File(fPath, "drawPic1.png");

                            try {
                                FileOutputStream strm = new FileOutputStream(f);
                                bm.compress(Bitmap.CompressFormat.PNG, 80, strm);
                                strm.close();
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }*/


                          /* Bitmap mBitmap = null;

                            drawView.setDrawingCacheEnabled(true);
                            bit = drawView.getDrawingCache();*/


                   // AddWordsActivity.mImage.setImageBitmap(bm);

                           // Intent intent = new Intent();
                            //intent.putExtra("BitmapImage", bm);
                            //setResult(RESULT_OK, intent);



                    GlobalBitmap.img = bm;
                            MainPaint.this.finish();
                    Log.d("intent send", "senddddd");
                    Intent intent = new Intent(MainPaint.this, AddWordsActivity.class);
                    setResult(RESULT_OK,intent);

                      /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();



                        Intent intent2 = new Intent(MainPaint.this, AddWordsActivity.class);

                        intent2.putExtra("lala", byteArray);
                        startActivityForResult(intent2,1);*/


                            // finish();

                       /* drawView.setDrawingCacheEnabled(true);


                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), drawView.getDrawingCache(),
                                UUID.randomUUID().toString()+".png", "drawing");

                        if(imgSaved!=null){
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                            savedToast.show();
                        }
                        else{
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        drawView.destroyDrawingCache();*/
                        }
                   // });
                    /*saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    saveDialog.show();*/
                }
            }
        //}

    }
