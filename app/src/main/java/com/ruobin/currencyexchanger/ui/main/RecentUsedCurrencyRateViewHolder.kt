package com.ruobin.currencyexchanger.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruobin.currencyexchanger.ICurrencyConverter
import com.ruobin.currencyexchanger.R

class RecentUsedCurrencyRateViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val currencyConverter: ICurrencyConverter) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recent_used_currency_rate_list_item, parent, false)) {

    private var currencyTextView: TextView = itemView.findViewById(R.id.currency_textview)
    private var rateTextView: TextView = itemView.findViewById(R.id.rate_textview)

    fun bind(currency: String) {
        currencyTextView.text = currency
        rateTextView.text = currencyConverter.convertCurrency(currency).toString()
    }

}

