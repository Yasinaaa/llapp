package com.example.yasina.llapp.Train;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 08.04.15.
 */
public class TrainSpinnerAdapter extends ArrayAdapter {

    public static final String TAG = "DictionariesSpinner";
    private Context context;
    private ArrayList<String> objects = new ArrayList<String>();

        private List<Dictionary> mItems;
        private LayoutInflater mInflater;

        public TrainSpinnerAdapter(Context context,int list_item_layout,ArrayList<String> listOfTableNames) {
            super(context, list_item_layout, listOfTableNames);
            this.context = context;
            if(objects != null ) this.objects = objects;
        }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if(v == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_item_layout, null);
                holder = new ViewHolder();
                holder.tableName = (TextView) v.findViewById(R.id.tv_name);
                v.setTag(holder);
            }

            ViewHolder vh = (ViewHolder) v.getTag();
            vh.tableName.setText(objects.get(position));

            return v;
        }


        class ViewHolder {
            TextView tableName;
        }
    }

