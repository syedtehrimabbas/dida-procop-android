package com.dida.procop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class BrandsGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> data;

    public BrandsGridAdapter(Context _context, ArrayList<String> _data) {
        context = _context;
        data = _data;
    }

    @Override
    public int getCount() {
        return data.size();
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
            gridView = inflater.inflate(R.layout.categories_list, viewGroup, false);

            TextView textView = gridView.findViewById(R.id.cat_text);
            textView.setText(data.get(position));

        } else {
            gridView = (View) view;
        }

        return gridView;
    }
}
