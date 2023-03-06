package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.databinding.LayoutItemAttributeSimpleBinding
import me.gilo.woodroid.models.ProductAttribute
import javax.inject.Inject

class ProductAttributeAdapter @Inject constructor() :
    RecyclerView.Adapter<ProductAttributeAdapter.AttributeViewHolder>() {
    var onItemClickListener: ((view: View, position: Int, data: ProductAttribute?) -> Unit)? =
        null
    private var list: MutableList<ProductAttribute> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeViewHolder {
        return AttributeViewHolder(
            LayoutItemAttributeSimpleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ProductAttribute>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class AttributeViewHolder(private val itemBinding: LayoutItemAttributeSimpleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: ProductAttribute) {
            itemView.setOnClickListener {
                val size: Int = item.options?.size ?: 0
                if (size > 1)
                    onItemClickListener?.invoke(it, adapterPosition, item)
            }

            itemBinding.productAttribute = item
        }
    }
}