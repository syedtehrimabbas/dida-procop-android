package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.databinding.LayoutItemListBinding
import me.gilo.woodroid.models.countries.CountriesResponseItem
import me.gilo.woodroid.models.countries.CountryState
import javax.inject.Inject

class CitiesAdapter @Inject constructor() :
    RecyclerView.Adapter<CitiesAdapter.ListViewHolder>() {
    var onItemClickListener: ((view: View, position: Int, data: CountryState) -> Unit)? =
        null
    private var list: MutableList<CountryState> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<CountryState>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val itemBinding: LayoutItemListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: CountryState) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }

            itemBinding.attributeValue = item.name
        }
    }
}