package com.androidstarter.ui.profile

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.dida.procop.BR
import com.dida.procop.R
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.dida.procop.databinding.FragmentContactUsBinding
import com.dida.procop.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseNavViewModelFragment<FragmentProfileBinding, IProfile.State, ProfileVM>() {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: ProfileVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_profile
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = "Profile"
    override fun onClick(id: Int) {}

    override fun hasOptionMenu(): Boolean = true
    override fun onPrepareOptionsMenu(menu: Menu) {

        val menuItem = menu.findItem(R.id.itemCartMenu)
        val cartLayout = menuItem.actionView as ConstraintLayout
        val cartButton = cartLayout.findViewById<ImageView>(R.id.cartImage)
        val cartCount = cartLayout.findViewById<TextView>(R.id.cartCount)

        val favImage = cartLayout.findViewById<ImageView>(R.id.favImage)
        val favCount = cartLayout.findViewById<TextView>(R.id.favCount)

        cartCount.text = viewModel.databaseHelper.cartCount.value.toString()
        favCount.text = viewModel.databaseHelper.favCount.value.toString()

        cartButton.setOnClick {
            navigateToCart(viewModel.databaseHelper)
        }

        favImage.setOnClick {
            navigateToFavourite(viewModel.databaseHelper)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.databaseHelper.cartCount()
        viewModel.databaseHelper.favouriteCount()
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.databaseHelper.cartCount.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
        viewModel.databaseHelper.favCount.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
    }
}