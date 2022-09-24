package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.R
import com.androidstarter.databinding.LayoutCategoryItemBinding
import me.gilo.woodroid.models.AttributeTerm
import java.util.*
import javax.inject.Inject

class BrandsAdapter @Inject constructor() :
    RecyclerView.Adapter<BrandsAdapter.BrandsViewHolder>() {
    var selectedPosition = -1
    var onItemClickListener: ((view: View, position: Int, data: AttributeTerm?) -> Unit)? =
        null
    private var list: MutableList<AttributeTerm> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsViewHolder {
        return BrandsViewHolder(
            LayoutCategoryItemBinding.inflate(
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

    fun setList(list: List<AttributeTerm>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    inner class BrandsViewHolder(private val itemBinding: LayoutCategoryItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: AttributeTerm) {

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
            itemBinding.catText.text = item.name?.lowercase(Locale.getDefault())
        }

    }
}