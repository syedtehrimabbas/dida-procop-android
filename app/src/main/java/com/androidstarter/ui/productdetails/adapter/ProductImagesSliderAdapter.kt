package com.androidstarter.ui.productdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.androidstarter.databinding.ItemProductImagesSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import me.gilo.woodroid.models.Image
import javax.inject.Inject

class ProductImagesSliderAdapter @Inject constructor() :
    SliderViewAdapter<ProductImagesSliderAdapter.ListViewHolder>() {

    private var list: MutableList<Image> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup): ListViewHolder {
        return ListViewHolder(
            ItemProductImagesSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getCount(): Int {
        return list.size
    }

    fun setList(list: List<Image>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val itemBinding: ItemProductImagesSliderBinding) :
        SliderViewAdapter.ViewHolder(itemBinding.root) {

        fun bind(item: Image) {
            itemView.setOnClickListener {
            }

            itemBinding.imageUrl = item.src
        }
    }
}