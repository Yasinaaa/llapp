package com.example.yasina.llapp.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yasina.llapp.Model.Dictionary;
import com.example.yasina.llapp.R;

/**
 * Created by yasina on 11.03.15.
 */
public class ListDictionariesAdapter extends BaseAdapter {

    public static final String TAG = "ListDictionariesAdapter";

    private List<Dictionary> mItems;
    private LayoutInflater mInflater;

    public ListDictionariesAdapter(Context context, List<Dictionary> listDictionaries) {
        this.setItems(listDictionaries);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Dictionary getItem(int position) {
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
            v = mInflater.inflate(R.layout.list_item_dicitonary, parent, false);
            holder = new ViewHolder();
            holder.txtDictionaryName = (TextView) v.findViewById(R.id.txt_dictionaryName);
            holder.txtDictionaryType = (TextView) v.findViewById(R.id.txt_dictionaryType);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        Dictionary currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtDictionaryName.setText(currentItem.getName());
            holder.txtDictionaryType.setText(currentItem.getType());
        }

        return v;
    }

    public List<Dictionary> getItems() {
        return mItems;
    }

    public void setItems(List<Dictionary> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtDictionaryName;
        TextView txtDictionaryType;
    }

}

