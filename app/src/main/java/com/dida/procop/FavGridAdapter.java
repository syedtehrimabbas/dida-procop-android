package com.dida.procop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> prodNames;
    ArrayList<Integer> imgNames;

    public FavGridAdapter(Context _context, ArrayList<String> _prodName, ArrayList<Integer> _imgNames) {
        context = _context;
        prodNames = _prodName;
        imgNames = _imgNames;
    }

    @Override
    public int getCount() {
        return prodNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.fav_item_layout, viewGroup, false);

            TextView itemName = gridView.findViewById(R.id.itemName);
            ImageView itemImage = gridView.findViewById(R.id.itemImage);
            itemName.setText(prodNames.get(position));
            itemImage.setImageResource(imgNames.get(position));

        } else {
            gridView = (View) view;
        }

        return gridView;
    }

}
