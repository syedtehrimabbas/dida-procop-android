package com.androidstarter.ui.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.databinding.LayoutOrderItemBinding
import kotlinx.coroutines.*
import me.gilo.woodroid.models.Order
import javax.inject.Inject

class OrdersAdapterAdapter @Inject constructor(
    val cartProductDao: CartProductDao
) :
    RecyclerView.Adapter<OrdersAdapterAdapter.OrdersViewHolder>() {
    private var _list: MutableList<Order> = mutableListOf()
    var dataList: List<Order> = _list
    var onItemClickListener: ((view: View, position: Int, data: Order) -> Unit)? =
        null
    var onChildItemClickListener: ((view: View, position: Int, data: Order) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(
            LayoutOrderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(_list[position])
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    fun setList(list: List<Order>) {
        this._list.clear()
        this._list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this._list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int) = this._list[position]

    inner class OrdersViewHolder(private val itemBinding: LayoutOrderItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Order) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }

            itemBinding.item = item
        }
    }
}