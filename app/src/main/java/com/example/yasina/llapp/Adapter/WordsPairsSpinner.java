package com.example.yasina.llapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.util.List;

/**
 * Created by yasina on 11.03.15.
 */
public class WordsPairsSpinner extends BaseAdapter {

        public static final String TAG = "WordsPairsSpinner";

        private List<Words> mItems;
        private LayoutInflater mInflater;

        public WordsPairsSpinner(Context context, List<Words> listWordsPairs) {
            this.setItems(listWordsPairs);
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
        }

        @Override
        public Words getItem(int position) {
            return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
        }

        @Override
        public long getItemId(int position) {
            return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getmId() : position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if(v == null) {
                v = mInflater.inflate(R.layout.spinner_words_pair, parent, false);
                holder = new ViewHolder();
                holder.txtFirstLang = (TextView) v.findViewById(R.id.txt_firstLanguage_of_spinner_wp);
                holder.txtSecondLang = (TextView) v.findViewById(R.id.txt_secondLanguage_of_spinner_wp);
                v.setTag(holder);
            }
            else {
                holder = (ViewHolder) v.getTag();
            }

            Words currentItem = getItem(position);
            if(currentItem != null) {
                holder.txtFirstLang.setText(currentItem.getFirstLang());
                holder.txtSecondLang.setText(currentItem.getSecondLang());
            }

            return v;
        }

        public List<Words> getItems() {
            return mItems;
        }

        public void setItems(List<Words> mItems) {
            this.mItems = mItems;
        }

        class ViewHolder {
            TextView txtFirstLang;
            TextView txtSecondLang;
        }
    }

