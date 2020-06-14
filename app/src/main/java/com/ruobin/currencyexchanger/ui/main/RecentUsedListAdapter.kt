package com.ruobin.currencyexchanger.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruobin.currencyexchanger.ICurrencyConverter

class RecentUsedListAdapter(private var recentCurrencyList : List<String>, private val currencyConverter: ICurrencyConverter) : RecyclerView.Adapter<RecentUsedCurrencyRateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentUsedCurrencyRateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecentUsedCurrencyRateViewHolder(inflater, parent, currencyConverter)
    }

    override fun getItemCount(): Int = recentCurrencyList.size

    override fun onBindViewHolder(holder: RecentUsedCurrencyRateViewHolder, position: Int) {
        val currency: String = recentCurrencyList[position]
        holder.bind(currency)
    }

}