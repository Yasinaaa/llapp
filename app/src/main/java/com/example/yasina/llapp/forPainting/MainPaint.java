
        package com.example.yasina.llapp.forPainting;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.ScrollView;
        import android.widget.Toast;
        import com.example.yasina.llapp.AddWordsActivity;
        import com.example.yasina.llapp.R;
        import java.io.ByteArrayOutputStream;
        import java.util.ArrayList;

        /**
 * Created by yasina on 25.03.15.
 */
public class MainPaint extends Activity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {


    private DrawingView drawView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private float smallBrush, mediumBrush, largeBrush;
    private ColorDAO colorDAO;
    private ArrayList<MyColor> colors;
    private ColorAdapter colorAdapter;
    private ListView colorsListView;
            private boolean clicked = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.paint, menu);

        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawView.setBrushSize(smallBrush);
        eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        saveBtn = (ImageButton)findViewById(R.id.save_btn);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.draw_btn){
            clicked = true;
            final Dialog brushDialog = new Dialog(this);
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

            final Dialog brushDialog = new Dialog(this);
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
            if (item.getItemId() == R.id.save_btn) {

                drawView.setDrawingCacheEnabled(true);
                Bitmap bm = drawView.getDrawingCache();
                if(clicked) {

                    GlobalBitmap.img = bm;
                    MainPaint.this.finish();
                    Log.d("intent send", "senddddd");
                    Intent intent = new Intent(MainPaint.this, AddWordsActivity.class);
                    setResult(RESULT_OK, intent);
                }else {
                    AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.for_paint);
        setContentView(R.layout.for_paint2);
        colorDAO = new ColorDAO(this);
        currPaint = new ImageButton(this);
        colorsListView = (ListView)findViewById(R.id.paint_colors);

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
            colorAdapter =  new ColorAdapter(this, R.layout.colors, colors);
            colorsListView.setAdapter(colorAdapter);
            colorsListView.setOnItemClickListener(this);
          /*  colorsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return false;
                }
            });*/
        }
        drawView = (DrawingView)findViewById(R.id.drawing);
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);


    }
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currPaint = (ImageButton) adapterView.getItemAtPosition(i);
                currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            }

    public void paintClicked(View view){

        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
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
            Toast unsavedToast = Toast.makeText(getApplicationContext(),
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
                startActivityForResult(new Intent(this, ChooseColor.class), 1);
                break;

        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int color = data.getExtras().getInt("color");
        drawView.setColor(color);
        colorDAO.add(color);

        try{
            colorDAO = new ColorDAO(this);
            colors = colorDAO.getAll();
        }catch (RuntimeException e){
            colors = new ArrayList<MyColor>();
        }

        colorAdapter =  new ColorAdapter(this, R.layout.colors, colors);
        colorsListView.setAdapter(colorAdapter);
        colorsListView.setOnItemClickListener(this);
           //startActivity(new Intent(getApplicationContext(),MainPaint.class));
    }

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        }


