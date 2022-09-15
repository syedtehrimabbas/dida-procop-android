package com.androidstarter.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.androidstarter.R
import com.androidstarter.base.interfaces.CoroutineViewModel
import com.androidstarter.base.viewmodel.Dispatcher
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.databinding.LayoutProductItemBinding
import kotlinx.coroutines.*
import me.gilo.woodroid.models.Product
import javax.inject.Inject

class ProductsAdapter @Inject constructor(
    val cartProductDao: CartProductDao
) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(), CoroutineViewModel {
    private var list: MutableList<Product> = mutableListOf()
    var onItemClickListener: ((view: View, position: Int, data: Product?) -> Unit)? =
        null
    var onChildItemClickListener: ((view: View, position: Int, data: Product) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Product>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ProductsViewHolder(private val itemBinding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Product) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition, item)
            }

            launch {
                cartProductDao.getProductById(item.id)?.let { _ ->
                    launch(Dispatcher.Main) {
                        setRemoveCartText(itemBinding.addToCartBtn)
                    }
                } ?: setAddCartText(itemBinding.addToCartBtn)
            }

            launch {
                cartProductDao.getFavProductById(item.id)?.let { _ ->
                    launch(Dispatcher.Main) {
                        favourite(itemBinding.addToFavBtn)
                    }
                } ?: unFavourite(itemBinding.addToFavBtn)
            }

            itemBinding.addToCartBtn.setOnClickListener {
                launch {
                    onChildItemClickListener?.invoke(it, adapterPosition, item)
                }
                notifyDataSetChanged()
            }

            itemBinding.addToFavBtn.setOnClickListener {
                launch {
                    onChildItemClickListener?.invoke(it, adapterPosition, item)
                }
                notifyDataSetChanged()
            }

            itemBinding.product = item
        }
    }

    fun setRemoveCartText(view: AppCompatButton) {
        view.text = "Remove Cart"
    }

    fun setAddCartText(view: AppCompatButton) {
        view.text = "Add to cart"
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