package com.androidstarter.ui.home.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.ui.filter.ProductFilterVM
import com.dida.procop.R
import com.dida.procop.databinding.LayoutColorItemBinding
import javax.inject.Inject

class FilterColorsAdapter @Inject constructor() :
    RecyclerView.Adapter<FilterColorsAdapter.FiltersColorViewHolder>() {
    var selectedPosition = -1
    var onItemClickListener: ((view: View, position: Int, data: ProductFilterVM.FilterColor?) -> Unit)? =
        null
    private var list: MutableList<ProductFilterVM.FilterColor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersColorViewHolder {
        return FiltersColorViewHolder(
            LayoutColorItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FiltersColorViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ProductFilterVM.FilterColor>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class FiltersColorViewHolder(private val itemBinding: LayoutColorItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: ProductFilterVM.FilterColor) {
            itemView.setOnClickListener {
                selectedPosition = absoluteAdapterPosition
                onItemClickListener?.invoke(it, absoluteAdapterPosition, item)
                notifyDataSetChanged()
            }
            itemBinding.mainLayout.background = if (absoluteAdapterPosition == selectedPosition)
                itemBinding.mainLayout.resources.getDrawable(R.drawable.selected_rectangle_stroke)
            else
                itemBinding.mainLayout.resources.getDrawable(R.drawable.transparent_rectangle_stroke)

            itemBinding.colorView.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(item.colorCode))
            itemBinding.colorName.text = item.name
        }
    }
}