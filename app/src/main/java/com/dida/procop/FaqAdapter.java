package com.dida.procop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyHolder> {

    ArrayList<FaqData> faqDataList;
    Context context;

    public FaqAdapter(ArrayList<FaqData> _faqDataList, Context _context) {
        this.faqDataList = _faqDataList;
        this.context = _context;
    }

    @NonNull
    @Override
    public FaqAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_faq_item, parent, false);
        return new FaqAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqAdapter.MyHolder holder, int position) {
        FaqData faqData = faqDataList.get(position);
        holder.faqTitle.setText(faqData.getTitle());
        holder.faqDescription.setText(faqData.getDescription());

        boolean isExpanded = faqDataList.get(position).isExpanded();
        holder.expandedLayoutFaq.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.plusMinusImg.setImageResource(isExpanded ? R.drawable.ic_minus : R.drawable.ic_add);
    }

    @Override
    public int getItemCount() {
        return faqDataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView faqTitle;
        TextView faqDescription;
        ConstraintLayout expandedLayoutFaq;
        ImageView plusMinusImg;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            faqTitle = itemView.findViewById(R.id.faqTitle);
            faqDescription = itemView.findViewById(R.id.faqDescription);
            expandedLayoutFaq = itemView.findViewById(R.id.expandedLayoutFaq);
            plusMinusImg = itemView.findViewById(R.id.plusMinusImg);

            faqTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FaqData faqData = faqDataList.get(getAdapterPosition());
                    faqData.setExpanded(!faqData.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
