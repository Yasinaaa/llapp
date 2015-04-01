package com.example.yasina.llapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yasina.llapp.Model.Words;
import com.example.yasina.llapp.R;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by yasina on 11.03.15.
 */
public class ListWordsAdapter extends BaseAdapter {

    public static final String TAG = "ListWordsPairAdapter";

    private List<Words> mItems;
    private LayoutInflater mInflater;

    public ListWordsAdapter(Context context, List<Words> listDictionaries) {
        this.setItems(listDictionaries);
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
            v = mInflater.inflate(R.layout.list_item_words_pair, parent, false);
            holder = new ViewHolder();
            holder.txtFirstLang = (TextView) v.findViewById(R.id.txt_firstLanguage_list_item_wp);
            holder.txtSecondLang = (TextView) v.findViewById(R.id.txt_secondLanguage_list_item_wp);
            holder.ivPicture = (ImageView) v.findViewById(R.id.ivPicture_list_item_wp);
            holder.txtExplanation = (TextView) v.findViewById(R.id.txt_explanation_item_wp);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        Words currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtFirstLang.setText(currentItem.getFirstLang());
            holder.txtSecondLang.setText(currentItem.getSecondLang());

            byte[] outImage = currentItem.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            holder.ivPicture.setImageBitmap(Bitmap.createScaledBitmap(theImage, 200, 120, false));

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
        ImageView ivPicture;
        TextView txtExplanation;
    }

}


