package com.ruobin.currencyexchanger.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CurrencySelectionListAdapter(private val rates : List<String>, private val listener: CurrencyViewHolder.CurrencySelectionListener) : RecyclerView.Adapter<CurrencyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyViewHolder(inflater, parent, this.listener)
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val rate: String = rates[position]
        holder.bind(rate)
    }
}