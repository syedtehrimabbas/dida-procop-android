package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dida.procop.R
import com.dida.procop.databinding.LayoutCategoryBoxBinding
import com.dida.procop.databinding.LayoutCategoryItemBinding
import me.gilo.woodroid.models.Category

class ProductCategoriesAdapter constructor(val VIEW_TYPE: Int = 0) :
    RecyclerView.Adapter<ProductCategoriesAdapter.CategoriesViewHolder>() {
    var selectedPosition = -1
    var onItemClickListener: ((view: View, position: Int, data: Category?) -> Unit)? =
        null
    private var list: MutableList<Category> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layout = when (viewType) {
            TYPE_SIMPLE -> R.layout.layout_category_item
            TYPE_CATEGORY_BOX -> R.layout.layout_category_box
            else -> R.layout.layout_category_item
        }

        val viewDataBinding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater
                    .from(parent.context), layout, parent, false
            )
        return CategoriesViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
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

    override fun getItemViewType(position: Int): Int {
        return if (VIEW_TYPE == 0) TYPE_SIMPLE else TYPE_CATEGORY_BOX
    }

    inner class CategoriesViewHolder(private val itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Category) {
            when (itemViewType) {
                TYPE_SIMPLE -> bindSimpleCategories(item)
                TYPE_CATEGORY_BOX -> bindBoxCategories(item)
            }
        }

        private fun bindSimpleCategories(item: Category) {
            itemBinding as LayoutCategoryItemBinding
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
            itemBinding.catText.text = item.name
        }

        private fun bindBoxCategories(item: Category) {
            itemBinding as LayoutCategoryBoxBinding

            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }

            itemBinding.category = item
        }
    }

    companion object {
        const val TYPE_SIMPLE = 0
        const val TYPE_CATEGORY_BOX = 1
    }
}