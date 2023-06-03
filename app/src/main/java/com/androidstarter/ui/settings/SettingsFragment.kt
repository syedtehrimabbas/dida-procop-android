package com.androidstarter.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.androidstarter.base.clickevents.setOnClick
import com.androidstarter.base.navgraph.BaseNavViewModelFragment
import com.dida.procop.BR
import com.dida.procop.R
import com.dida.procop.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SettingsFragment :
    BaseNavViewModelFragment<FragmentSettingsBinding, ISettings.State, SettingsVM>(),
    AdapterView.OnItemSelectedListener {
    override val bindingVariableId = BR.viewModel
    override val bindingViewStateVariableId = BR.viewState
    override val viewModel: SettingsVM by viewModels()
    override val layoutResId: Int = R.layout.fragment_settings
    override fun toolBarVisibility(): Boolean = true
    override fun getToolBarTitle() = getString(R.string.layout_drawer_menu_item_settings)
    override fun onClick(id: Int) {}
    override fun hasOptionMenu(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val languages = arrayOf(
            getString(R.string.language_english), getString(R.string.language_french)
        )
        mViewDataBinding.languageSpinner.onItemSelectedListener = this

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )
        mViewDataBinding.languageSpinner.adapter = adapter

        when (viewModel.sharedPreferenceManager.getValueString("language") ?: "fra") {
            "eng" -> mViewDataBinding.languageSpinner.setSelection(0)
            "fra" -> mViewDataBinding.languageSpinner.setSelection(1)
            else -> mViewDataBinding.languageSpinner.setSelection(0)
        }
    }

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

    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context?.resources?.updateConfiguration(
            config,
            context?.resources?.displayMetrics
        )

        viewModel.sharedPreferenceManager.save("language", locale.isO3Language)
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val languages = arrayOf(
            getString(R.string.language_english), getString(R.string.language_french)
        )
        val local = when (position) {
            0 -> Locale.ENGLISH
            1 -> Locale.FRENCH
            else -> Locale.ENGLISH
        }

        setLocale(local)

        showToast("${getString(R.string.common_language_changed)} ${languages[position]}")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}