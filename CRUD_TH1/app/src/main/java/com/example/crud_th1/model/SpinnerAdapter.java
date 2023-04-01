package com.example.crud_th1.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.crud_th1.R;

public class SpinnerAdapter extends BaseAdapter {
    private int[] imgs = {R.drawable.cat_1, R.drawable.cat2, R.drawable.cat3};

    private Context context;

    public SpinnerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Moi mot view tuong ung voi mot item hien thi tren spinner
        View item = LayoutInflater.from(context).inflate(R.layout.item_spinner, viewGroup, false);
        ImageView img = item.findViewById(R.id.img);
        img.setImageResource(imgs[position]);
        return item;
    }
}
