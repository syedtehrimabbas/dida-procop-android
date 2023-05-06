package com.androidstarter.ui.filter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.procop.databinding.LayoutStringListItemBinding
import javax.inject.Inject

class ListAdapter @Inject constructor() :
    RecyclerView.Adapter<ListAdapter.BrandsViewHolder>() {
    var selectedPosition = -1
    var onItemClickListener: ((view: View, position: Int, data: String) -> Unit)? =
        null
    private var list: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsViewHolder {
        return BrandsViewHolder(
            LayoutStringListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BrandsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class BrandsViewHolder(private val itemBinding: LayoutStringListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: String) {

            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                onItemClickListener?.invoke(it, adapterPosition, item)
                notifyDataSetChanged()
            }
            itemBinding.nameTV.text = item
        }
    }
}