package com.darwish.currency.feature.historiccal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.darwish.currency.R
import com.darwish.currency.core.dialog.DialogUtils
import com.darwish.currency.core.extention.manageVisibility
import com.darwish.currency.core.extention.observe
import com.darwish.currency.core.extention.viewBinding
import com.darwish.currency.databinding.FragmentHistoricalDataBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoricalDataFragment : Fragment(R.layout.fragment_historical_data) {

    private val arg by navArgs<HistoricalDataFragmentArgs>()
    private val binding by viewBinding(FragmentHistoricalDataBinding::bind)
    private val viewModel by viewModels<HistoryViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // observer view changes
        observe(viewModel.getViewState(), ::viewStateObserver)
        // bind adapter
        binding.currencyRateRec.adapter = viewModel.currencyRateAdapter
        // get history
        loadData()

        // swipe refresh listener
        binding.swipeView.setOnRefreshListener {
            binding.swipeView.isRefreshing = false
            loadData()
        }

    }


    private fun loadData() = viewModel.getHistory(arg.from, arg.to)
    private fun viewStateObserver(it: HistoryViewStatue) {
        when (it) {
            is HistoryViewStatue.IsLoading -> binding.progressLoad.manageVisibility(it.data)
            is HistoryViewStatue.ShowError -> DialogUtils.showErrorPopup(
                context = requireActivity(),
                retryListener = {
                    loadData()
                },
                cancelListener = {},
                error = it.data
            )


        }
    }

}