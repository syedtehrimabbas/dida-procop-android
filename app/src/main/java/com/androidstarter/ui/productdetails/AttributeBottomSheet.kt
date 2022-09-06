package com.androidstarter.ui.productdetails

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.androidstarter.R
import com.androidstarter.databinding.FragmentBottomSheetAttributeBinding
import com.androidstarter.ui.home.adapter.ListAdapter
import com.androidstarter.ui.interfaces.ProductUpdateListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.gilo.woodroid.models.ProductAttribute

class AttributeBottomSheet constructor(
    private val productAttribute: ProductAttribute?,
    private val productUpdateListener: ProductUpdateListener
) :
    BottomSheetDialogFragment() {

    lateinit var listAdapter: ListAdapter

    lateinit var mViewDataBinding: FragmentBottomSheetAttributeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        mViewDataBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_bottom_sheet_attribute,
                container,
                false
            )

        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter()
        listAdapter.onItemClickListener = clickListener
        mViewDataBinding.attributeTitle.text = productAttribute?.name
        mViewDataBinding.listRecyclerView.adapter = listAdapter
        productAttribute?.options?.let { listAdapter.setList(it) }
    }

    private val clickListener =
        { view: View, position: Int, data: String? ->
            if (productAttribute != null) {
                productUpdateListener.onAttributeUpdate(productAttribute, data ?: "")
            }
            dismiss()
        }
}