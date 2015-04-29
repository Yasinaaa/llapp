package com.example.yasina.llapp.Train;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by yasina on 08.04.15.
 */
public class TrainAdapter extends ArrayAdapter {

        public static final String TAG = "ListWordsPairAdapter";
        private Context context;
        private ArrayList<Words> objects = new ArrayList<Words>();

        //private List<Words> mItems;
        //private LayoutInflater mInflater;

        public TrainAdapter(Context context,int list_item_layout, ArrayList<Words> listDictionaries) {
            super(context,list_item_layout,listDictionaries);
            this.context = context;
            if(listDictionaries !=null) this.objects = listDictionaries;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Words getItem(int position) {
            return objects.get(position);
        }

        private ViewHolder vh = new ViewHolder();
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.list_item_words_pair_2, null);

                vh.txtFirstLang = (TextView) rowView.findViewById(R.id.txt_word_list_item_wp);
                vh.txtSecondLang = (TextView) rowView.findViewById(R.id.txt_translate_list_item_wp);
                vh.ivPicture = (ImageView) rowView.findViewById(R.id.ivPicture_list_item_wp);
                vh.ivPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vh.ivPicture.setImageResource(R.drawable.checkmark);
                    }
                });
                vh.txtExplanation = (TextView) rowView.findViewById(R.id.txt_explanation_item_wp);
                rowView.setTag(vh);
            }
            ViewHolder vh = (ViewHolder) rowView.getTag();
            vh.txtFirstLang.setText(objects.get(position).getFirstLang());
            vh.txtSecondLang.setText(objects.get(position).getSecondLang());

            byte[] outImage = objects.get(position).getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            vh.ivPicture.setImageBitmap(theImage);

            vh.txtExplanation.setText(objects.get(position).getExplanation());
            return rowView;
        }

        class ViewHolder {
            TextView txtFirstLang;
            TextView txtSecondLang;
            ImageView ivPicture;
            TextView txtExplanation;
        }

    }



