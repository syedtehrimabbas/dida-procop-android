package com.dida.procop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.MyHolder> {

    ArrayList<String> data;
    Context context;

    public CatListAdapter(ArrayList<String> _data, Context _context) {
        this.data = _data;
        this.context = _context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.cat_text.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView cat_text;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cat_text = itemView.findViewById(R.id.cat_text);
        }
    }

}
