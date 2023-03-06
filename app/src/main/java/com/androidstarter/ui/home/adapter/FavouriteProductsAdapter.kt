package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.dida.procop.R
import com.androidstarter.base.interfaces.CoroutineViewModel
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.cart.models.FavouriteProduct
import com.dida.procop.databinding.LayoutProductFavouriteBinding
import kotlinx.coroutines.*
import javax.inject.Inject

class FavouriteProductsAdapter @Inject constructor(
    val cartProductDao: CartProductDao
) :
    RecyclerView.Adapter<FavouriteProductsAdapter.FavouriteProductsViewHolder>(),
    CoroutineViewModel {
    private var _list: MutableList<FavouriteProduct> = mutableListOf()
    var openList: List<FavouriteProduct> = _list
    var onItemClickListener: ((view: View, position: Int, data: FavouriteProduct?) -> Unit)? =
        null
    var onChildItemClickListener: ((view: View, position: Int, data: FavouriteProduct) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteProductsViewHolder {
        return FavouriteProductsViewHolder(
            LayoutProductFavouriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteProductsViewHolder, position: Int) {
        holder.bind(_list[position])
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    fun setList(list: List<FavouriteProduct>) {
        this._list.clear()
        this._list.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavouriteProductsViewHolder(private val itemBinding: LayoutProductFavouriteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: FavouriteProduct) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }

            launch {
                cartProductDao.getProductById(item.productId)?.let { _ ->
                    launch(Dispatcher.Main) {
                        setRemoveCartText(itemBinding.addToCartBtn)
                    }
                } ?: setAddCartText(itemBinding.addToCartBtn)
            }

            launch {
                cartProductDao.getFavProductById(item.productId)?.let { _ ->
                    launch(Dispatcher.Main) {
                        favourite(itemBinding.addToFavBtn)
                    }
                } ?: unFavourite(itemBinding.addToFavBtn)
            }

            itemBinding.addToCartBtn.setOnClickListener {
                onChildItemClickListener?.invoke(it, adapterPosition, item)
                notifyDataSetChanged()
            }

            itemBinding.addToFavBtn.setOnClickListener {
                _list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                onChildItemClickListener?.invoke(it, adapterPosition, item)
            }

            itemBinding.product = item
        }
    }

    fun setRemoveCartText(view: AppCompatButton) {
        view.text = view.context.getString(R.string.common_remove_to_cart)
    }

    fun setAddCartText(view: AppCompatButton) {
        view.text = view.context.getString(R.string.common_add_to_cart)
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

    private fun unFavourite(addToFavBtn: AppCompatImageView) {
        launch(Dispatcher.Main) {
            addToFavBtn.setImageDrawable(addToFavBtn.context?.let {
                AppCompatResources.getDrawable(
                    it, R.drawable.circle_white_heart
                )
            })
        }
    }

    private fun favourite(addToFavBtn: AppCompatImageView) {
        launch(Dispatcher.Main) {
            addToFavBtn.setImageDrawable(addToFavBtn.context?.let {
                AppCompatResources.getDrawable(
                    it, R.drawable.circle_red_heart
                )
            })
        }
    }
}