package com.example.yasina.llapp.forPainting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yasina.llapp.R;

/**
 * Created by yasina on 25.03.15.
 */
public class DrawingView extends View {

        private Path drawPath;
        private Paint drawPaint, canvasPaint;
        private int paintColor = 0xFF660000;
        private Canvas drawCanvas;
        private Bitmap canvasBitmap;
        private float brushSize, lastBrushSize;
        private boolean erase=false;

        public DrawingView(Context context, AttributeSet attrs){
            super(context, attrs);
            setupDrawing();
        }

        private void setupDrawing(){

            brushSize = getResources().getInteger(R.integer.small_size);
            lastBrushSize = brushSize;
            drawPath = new Path();
            drawPaint = new Paint();
            drawPaint.setColor(paintColor);
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(brushSize);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
            canvasPaint = new Paint(Paint.DITHER_FLAG);
        }

        //size assigned to view
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if(w>0 && h>0) {
                canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                drawCanvas = new Canvas(canvasBitmap);
            }else {
                Log.e("Drawing saved to Gallery!", "Mistake");
            }
        }

        //draw the view - will be called after touch event
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
            canvas.drawPath(drawPath, drawPaint);
        }

        //register user touches as drawing action
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();
            //respond to down, move and up events
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;
                default:
                    return false;
            }
            //redraw
            invalidate();
            return true;

        }

        //update color
     /*   public void setColor(String newColor){
            invalidate();
            Log.d("color", paintColor + " is " + newColor);
            paintColor = Color.parseColor(newColor);
            drawPaint.setColor(paintColor);

        }*/

    public void setColor(int newColor){
        invalidate();
       // paintColor = Color.parseColor(newColor);
         drawPaint.setColor(newColor);
    }

        //set brush size
        public void setBrushSize(float newSize){
            float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    newSize, getResources().getDisplayMetrics());
            brushSize=pixelAmount;
            drawPaint.setStrokeWidth(brushSize);
        }

        //get and set last brush size
        public void setLastBrushSize(float lastSize){
            lastBrushSize=lastSize;
        }
        public float getLastBrushSize(){
            return lastBrushSize;
        }

        //set erase true or false
        public void setErase(boolean isErase){
            erase=isErase;
            if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            else drawPaint.setXfermode(null);
        }

        //start new drawing
        public void startNew(){
            drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            invalidate();
        }

       public Paint getPicture(){
           return drawPaint;
       }
    }
