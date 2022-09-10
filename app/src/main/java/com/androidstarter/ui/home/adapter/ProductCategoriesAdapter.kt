package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.R
import com.androidstarter.databinding.LayoutCategoryItemBinding
import me.gilo.woodroid.models.Category
import javax.inject.Inject

class ProductCategoriesAdapter @Inject constructor() :
    RecyclerView.Adapter<ProductCategoriesAdapter.TrendingRepoViewHolder>() {
    var selectedPosition = -1
    var onItemClickListener: ((view: View, position: Int, data: Category?) -> Unit)? =
        null
    private var list: MutableList<Category> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoViewHolder {
        return TrendingRepoViewHolder(
            LayoutCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingRepoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Category>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class TrendingRepoViewHolder(private val itemBinding: LayoutCategoryItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Category) {
            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                onItemClickListener?.invoke(it, adapterPosition, item)
                notifyDataSetChanged()
            }
            val backgroundColor =
                if (adapterPosition == selectedPosition) R.color.appPink else R.color.white

            val textColor =
                if (adapterPosition == selectedPosition) R.color.white else R.color.black

            itemBinding.cardView.setCardBackgroundColor(
                itemBinding.cardView.context.getColor(
                    backgroundColor
                )
            )
            itemBinding.catText.setTextColor(itemBinding.catText.context.getColor(textColor))
            itemBinding.category = item
        }
    }
}