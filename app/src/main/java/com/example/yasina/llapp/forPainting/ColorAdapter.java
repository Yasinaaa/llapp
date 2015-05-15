package com.example.yasina.llapp.forPainting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yasina.llapp.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by yasina on 04.05.15.
 */
public class ColorAdapter extends ArrayAdapter {

        public static final String TAG = "ColorAdapter";
        private Context context;
        private ArrayList<MyColor> objects = new ArrayList<MyColor>();

        public ColorAdapter(Context context,int list_item_layout, ArrayList<MyColor> listOfColors) {
            super(context,list_item_layout,listOfColors);
            this.context = context;
            if(listOfColors !=null) this.objects = listOfColors;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public MyColor getItem(int position) {
            return objects.get(position);
        }

        private ViewHolder vh = new ViewHolder();
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.colors, null);

                vh.color_btn = (ImageView) rowView.findViewById(R.id.color_btn);

                rowView.setTag(vh);
            }
            ViewHolder vh = (ViewHolder) rowView.getTag();

            vh.color_btn.setImageResource(R.drawable.paint222);
            vh.color_btn.setBackgroundResource(R.drawable.paint222);
            vh.color_btn.setBackgroundColor(objects.get(position).getColor());
            vh.color_btn.setTag(objects.get(position).getColor());

           /* Resources resources = getResources();
            image.setImageDrawable(resources.getDrawable(R.drawable.myfirstimage));*/

            return rowView;
        }

        class ViewHolder {
            ImageView color_btn;
        }
}
