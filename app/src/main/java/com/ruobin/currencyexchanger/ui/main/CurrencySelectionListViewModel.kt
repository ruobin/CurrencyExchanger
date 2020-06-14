package com.ruobin.currencyexchanger.ui.main

import androidx.lifecycle.ViewModel
import com.ruobin.currencyexchanger.datasource.ExchangeRatesRepository
import javax.inject.Singleton

@Singleton
class CurrencySelectionListViewModel(var repository: ExchangeRatesRepository) : ViewModel() {

    fun getCurrencyList(): List<String>? {
        return repository.getCachedCurrencyList()
    }

    fun addToRecentCurrencyList(currency: String) {
        repository.addToRecentCurrencyList(currency)
    }
}