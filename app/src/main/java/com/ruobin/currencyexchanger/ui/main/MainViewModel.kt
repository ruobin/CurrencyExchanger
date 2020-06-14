package com.ruobin.currencyexchanger.ui.main

import androidx.lifecycle.ViewModel
import com.ruobin.currencyexchanger.datasource.CurrencyRatesModel
import com.ruobin.currencyexchanger.datasource.ExchangeRatesRepository
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class MainViewModel(var repository: ExchangeRatesRepository) : ViewModel() {

    fun getAllLiveCurrencyRates(): Observable<CurrencyRatesModel> {
        return repository.getFusedCurrencyRates()
    }

    fun cacheAllCurrencyRatesToLocal(data: CurrencyRatesModel): Boolean {
        return repository.saveAllCurrencyRatesToLocal(data)
    }

    fun convertCurrency(sourceCurrency: String, sourceValue: Float, destinationCurrency: String): Float {
        var ratesModel: CurrencyRatesModel = repository.getCachedCurrencyRates() ?: return Float.NaN
        var ratesBetweenSourceAndUSD: Float = ratesModel.quotes["USD$sourceCurrency"] ?: return Float.NaN
        var ratesBetweenDestinationAndUSD: Float = ratesModel.quotes["USD$destinationCurrency"] ?: return Float.NaN
        return ratesBetweenDestinationAndUSD/ratesBetweenSourceAndUSD * sourceValue
    }

    fun getRecentCurrencyList(): List<String> {
        return repository.getRecentCurrencyList()
    }

    fun saveRecentCurrencyListToPersistentCache() {
        repository.saveRecentCurrencyList()
    }

}
