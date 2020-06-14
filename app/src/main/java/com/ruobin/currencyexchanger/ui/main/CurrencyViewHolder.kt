package com.ruobin.currencyexchanger.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruobin.currencyexchanger.R

class CurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup, clickListener: CurrencySelectionListener) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.currency_selection_list_item, parent, false)) {

    interface CurrencySelectionListener {
        fun onItemClick(currencySelection: String)
    }

    private var textView: TextView = itemView.findViewById(R.id.textView)

    init {
        itemView.setOnClickListener {
            clickListener.onItemClick(textView.text.toString())
        }
    }

    fun bind(rates: String) {
        textView.text = rates
    }

}

