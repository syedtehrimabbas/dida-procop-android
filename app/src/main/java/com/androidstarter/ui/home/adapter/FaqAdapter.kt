package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.R
import com.androidstarter.databinding.LayoutFaqItemBinding
import com.androidstarter.ui.fq.FaqData
import javax.inject.Inject

class FaqAdapter @Inject constructor() :
    RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    var onItemClickListener: ((view: View, position: Int, data: FaqData?) -> Unit)? =
        null
    private var list: MutableList<FaqData> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        return FaqViewHolder(
            LayoutFaqItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<FaqData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class FaqViewHolder(private val itemBinding: LayoutFaqItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: FaqData) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }
            itemBinding.faqTitle.text = item.title
            itemBinding.faqDescription.text = item.description

            itemBinding.expandedLayoutFaq.visibility =
                if (item.expanded) View.VISIBLE else View.GONE
            itemBinding.plusMinusImg.setImageResource(if (item.expanded) R.drawable.ic_minus else R.drawable.ic_add)

            itemBinding.faqTitle.setOnClickListener {
                item.expanded = !item.expanded
                notifyItemChanged(adapterPosition)
            }
        }
    }
}