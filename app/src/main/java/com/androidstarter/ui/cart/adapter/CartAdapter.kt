package com.androidstarter.ui.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.base.interfaces.CoroutineViewModel
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.databinding.LayoutCartItemBinding
import kotlinx.coroutines.*
import javax.inject.Inject

class CartAdapter @Inject constructor(
    val cartProductDao: CartProductDao
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>(), CoroutineViewModel {
    private var list: MutableList<CartProduct> = mutableListOf()
    var onItemClickListener: ((view: View, position: Int, data: CartProduct) -> Unit)? =
        null
    var onChildItemClickListener: ((view: View, position: Int, data: CartProduct) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutCartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<CartProduct>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val itemBinding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: CartProduct) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }

            itemBinding.minusCart.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity -= 1
                    onChildItemClickListener?.invoke(it, adapterPosition, item)
                }
                notifyDataSetChanged()
            }

            itemBinding.plusCart.setOnClickListener {
                item.quantity += 1
                onChildItemClickListener?.invoke(it, adapterPosition, item)
                notifyDataSetChanged()
            }

            itemBinding.item = item
        }
    }

    override val viewModelScope: CoroutineScope
        get() = CoroutineScope(viewModelJob + Dispatchers.IO)

    override fun cancelAllJobs() {
        viewModelScope.cancel()
        viewModelJob.cancel()
    }

    override val viewModelJob: Job
        get() = Job()

    override fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    override fun async(block: suspend () -> Unit) = viewModelScope.async { block }

    fun launch(dispatcher: Dispatcher = Dispatcher.Main, block: suspend () -> Unit) {
        viewModelScope.launch(
            when (dispatcher) {
                Dispatcher.Main -> Dispatchers.Main
                Dispatcher.Background -> Dispatchers.IO
                Dispatcher.LongOperation -> Dispatchers.Default
            }
        ) { block() }
    }
}