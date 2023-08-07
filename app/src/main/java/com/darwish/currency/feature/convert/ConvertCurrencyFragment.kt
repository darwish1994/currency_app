package com.darwish.currency.feature.convert

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.darwish.currency.R
import com.darwish.currency.core.dialog.DialogUtils
import com.darwish.currency.core.extention.manageVisibility
import com.darwish.currency.core.extention.observe
import com.darwish.currency.core.extention.showToast
import com.darwish.currency.core.extention.toVisible
import com.darwish.currency.core.extention.viewBinding
import com.darwish.currency.databinding.FragmentConvertCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment(R.layout.fragment_convert_currency), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private val binding by viewBinding(FragmentConvertCurrencyBinding::bind)
    private val viewModel by viewModels<ConvertViewModel>()

    private val adapter by lazy {
        ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_item
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getStatueObserver(), ::viewStatueObserver)
        if (adapter.isEmpty)
            viewModel.getSymbols()
        else
            binding.groupAllView.toVisible()
        initTextDebounce()
        binding.fromSpinner.adapter = adapter
        binding.toSpinner.adapter = adapter


    }


    /**
     * convert amount from select currency to target currency
     * amount should be bigger than 0
     * from and to currency can not be the same
     * */

    private fun convertAmount(
        amount: Double = binding.amountFrom.editableText.toString().toDoubleOrNull() ?: 0.0
    ) {
        val from = binding.fromSpinner.selectedItem as String?
        val to = binding.toSpinner.selectedItem as String?

        if (from == null) {
            showToast(R.string.error_select_from)
            return
        }
        if (to == null) {
            showToast(R.string.error_select_to)
            return
        }

        if (from == to) {
            showToast(R.string.different_currency_error)
            return
        }

        if (amount <= 0.0) {
            showToast(R.string.amount_error)
            return
        }


        viewModel.convert(from, to, amount) // call api

    }


    /**
     * get currency history for last 30 days
     *
     * */
    private fun getCurrencySeries() {
        val from = binding.fromSpinner.selectedItem as String?
        val to = binding.toSpinner.selectedItem as String?

        if (from == null) {
            showToast(R.string.error_select_from)
            return
        }
        if (to == null) {
            showToast(R.string.error_select_to)
            return
        }

        if (from == to) {
            showToast(R.string.different_currency_error)
            return
        }

        findNavController().navigate(
            ConvertCurrencyFragmentDirections.actionConvertCurrencyFragmentToHistoricalDataFragment(
                from = from,
                to = to
            )
        )
    }


    override fun onResume() {
        super.onResume()
        binding.convertBtn.setOnClickListener(this)
        binding.btnSwap.setOnClickListener(this)
        binding.fromSpinner.onItemSelectedListener = this
        binding.toSpinner.onItemSelectedListener = this

    }

    override fun onPause() {
        super.onPause()
        binding.convertBtn.setOnClickListener(null)
        binding.btnSwap.setOnClickListener(null)
        binding.fromSpinner.onItemSelectedListener = null
        binding.toSpinner.onItemSelectedListener = null

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.convertBtn -> getCurrencySeries()
            binding.btnSwap -> swapCurrency()

        }
    }


    private fun swapCurrency() {
        val firstIndex = binding.fromSpinner.selectedItemPosition
        val secondIndex = binding.toSpinner.selectedItemPosition
        binding.fromSpinner.setSelection(secondIndex, true)
        binding.toSpinner.setSelection(firstIndex, true)
        convertAmount()

    }


    private var dataChangeJob: Job? = null
    private fun initTextDebounce() {
        binding.amountFrom.doAfterTextChanged { text ->
            dataChangeJob?.cancel()
            dataChangeJob = lifecycleScope.launch {
                delay(300)
                text?.toString()?.toDoubleOrNull()?.let { amount ->
                    if (amount > 0.0)
                        convertAmount(amount)

                }

            }

        }

    }


    private fun viewStatueObserver(it: ConvertViewState) {
        Timber.v("there 1")
        when (it) {
            is ConvertViewState.IsLoading -> binding.progress.manageVisibility(it.data)

            is ConvertViewState.UpdateSpinnerWithData -> {
                binding.groupAllView.toVisible()
                Timber.v("there")
                adapter.clear()
                adapter.addAll(it.data.map { it.key })
                adapter.notifyDataSetChanged()
            }

            is ConvertViewState.ShowErrorSymbol -> {
                DialogUtils.showErrorPopup(
                    context = requireActivity(),
                    retryListener = {
                        viewModel.getSymbols()
                    },
                    cancelListener = {},
                    error = it.data
                )
            }

            is ConvertViewState.ShowErrorConvert -> {
                DialogUtils.showErrorPopup(
                    context = requireActivity(),
                    retryListener = {
                        convertAmount()
                    },
                    cancelListener = {},
                    error = it.data
                )
            }

            is ConvertViewState.ViewConvertResult -> {
                binding.amountTo.apply {
                    text.clear()
                    text.append(it.data.toString())
                }
            }

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        convertAmount()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit


}