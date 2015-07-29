package com.example.yasina.llapp.forPainting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuInflater;
import com.example.yasina.llapp.AddWordsActivity;
import com.example.yasina.llapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainPaint_Fragment extends SherlockFragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {


    private DrawingView drawView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private float smallBrush, mediumBrush, largeBrush;
    private ColorDAO colorDAO;
    private ArrayList<MyColor> colors;
    private ColorAdapter colorAdapter;
    private ListView colorsListView;
    private boolean clicked = false;

    private View view;

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.paint, menu);
        getActionBar().setTitle("Paint");

        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawView.setBrushSize(smallBrush);
        eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        saveBtn = (ImageButton)findViewById(R.id.save_btn);

        return true;
    }*/

    @Override
    public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, MenuInflater inflater) {
        getSherlockActivity().getSupportMenuInflater().inflate(R.menu.paint, menu);
        getSherlockActivity().getActionBar().setTitle("Paint");

        drawBtn = (ImageButton) view.findViewById(R.id.draw_btn);
        drawView.setBrushSize(smallBrush);
        eraseBtn = (ImageButton) view.findViewById(R.id.erase_btn);
        newBtn = (ImageButton) view.findViewById(R.id.new_btn);
        saveBtn = (ImageButton) view.findViewById(R.id.save_btn);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {

        if(item.getItemId() == R.id.draw_btn){
            clicked = true;
            final Dialog brushDialog = new Dialog(getSherlockActivity().getApplicationContext());
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_chooser);
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
            brushDialog.show();
        }
        else if(item.getItemId()==R.id.erase_btn){

            final Dialog brushDialog = new Dialog(getSherlockActivity().getApplicationContext());
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_chooser);
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
        else if(item.getItemId()==R.id.new_btn){

            AlertDialog.Builder newDialog = new AlertDialog.Builder(getSherlockActivity().getApplicationContext());
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            clicked = false;
            newDialog.show();
        }
        else {
            if (item.getItemId() == R.id.save_btn) {

                drawView.setDrawingCacheEnabled(true);
                Bitmap bm = drawView.getDrawingCache();
                if(clicked) {

                    GlobalBitmap.img = bm;
                   // MainPaint.this.finish();
                    Log.d("intent send", "senddddd");
                    Intent intent = new Intent(getSherlockActivity().getBaseContext(), AddWordsActivity.class);
                    //intent.setResult(RESULT_OK, intent);
                }else {
                    AlertDialog.Builder warningDialog = new AlertDialog.Builder(getSherlockActivity().getApplicationContext());
                    warningDialog.setTitle("Warning!");
                    warningDialog.setMessage("You are not painting picture! Need to create something.");
                    warningDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    warningDialog.show();
                }

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.for_paint2, container, false);
        setHasOptionsMenu(true);
        colorDAO = new ColorDAO(getSherlockActivity().getApplicationContext());
        currPaint = new ImageButton(getSherlockActivity().getApplicationContext());
        colorsListView = (ListView) view.findViewById(R.id.paint_colors);

        try{
            colors = colorDAO.getAll();
        }catch (RuntimeException e){
            colors = new ArrayList<MyColor>();
        }

       /* try {
            int color = data.getExtras().getInt("color");
            drawView.setColor(color);
            colorDAO.add(color);
        }catch (RuntimeException e ){

        }*/

        if(colors.size() > 0){
            colorAdapter =  new ColorAdapter(getSherlockActivity().getApplicationContext(), R.layout.colors, colors);
            colorsListView.setAdapter(colorAdapter);
            colorsListView.setOnItemClickListener(this);
          /*  colorsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return false;
                }
            });*/
        }
        drawView = (DrawingView) view.findViewById(R.id.drawing);
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        currPaint = (ImageButton) adapterView.getItemAtPosition(i);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    public void paintClicked(View view){

        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if(view != currPaint){
            ImageButton imgView = (ImageButton)view;
            clicked = true;
            int color = Integer.parseInt(view.getTag().toString());
            // Log.d("coloooor",color);
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint222));
            currPaint=(ImageButton)view;
        }
    }

   /* public void paintClicked(){

        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint222));
            currPaint=(ImageButton)view;
        }
    }*/

    public byte[] getPicture()
    {
        drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache(true);

        Bitmap bitmap = Bitmap.createBitmap(drawView.getWidth(),drawView.getHeight(), Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream stream = null;

        if(bitmap!=null && clicked){
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        }
        else{
            Toast unsavedToast = Toast.makeText(getSherlockActivity().getApplicationContext(),
                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
            unsavedToast.show();
        }
        drawView.destroyDrawingCache();

        return stream.toByteArray();

    }

    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.add_color_btn:
                startActivityForResult(new Intent(getSherlockActivity().getApplicationContext(), ChooseColor.class), 1);
                break;

        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int color = data.getExtras().getInt("color");
        drawView.setColor(color);
        colorDAO.add(color);

        try{
            colorDAO = new ColorDAO(getSherlockActivity().getApplicationContext());
            colors = colorDAO.getAll();
        }catch (RuntimeException e){
            colors = new ArrayList<MyColor>();
        }

        colorAdapter =  new ColorAdapter(getSherlockActivity().getApplicationContext(), R.layout.colors, colors);
        colorsListView.setAdapter(colorAdapter);
        colorsListView.setOnItemClickListener(this);
        //startActivity(new Intent(getApplicationContext(),MainPaint.class));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}



