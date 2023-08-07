package com.darwish.currency.feature.historiccal.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darwish.currency.feature.historiccal.domain.model.CurrencyRate
import com.darwish.currency.databinding.CurrencySeriesItemLayoutBinding

class CurrencySeriesAdapter : RecyclerView.Adapter<CurrencySeriesAdapter.RateViewHolder>() {
    private val dataList = arrayListOf<CurrencyRate>()
    fun updateData(data: List<CurrencyRate>) {
        dataList.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
            // in this should i use diff util

        }

    }


    inner class RateViewHolder(private val layout: CurrencySeriesItemLayoutBinding) :
        RecyclerView.ViewHolder(layout.root) {

        fun bind(item: CurrencyRate) {
            layout.dateTv.text = item.date
            layout.rateTv.text = item.rate.toString()
            layout.currencyTv.text=item.currency
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder =
        RateViewHolder(
            layout = CurrencySeriesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}