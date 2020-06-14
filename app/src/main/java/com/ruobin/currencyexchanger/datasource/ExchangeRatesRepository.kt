package com.ruobin.currencyexchanger.datasource

import io.reactivex.Observable

interface ExchangeRatesRepository {

    fun getFusedCurrencyRates(): Observable<CurrencyRatesModel>

    fun getRemoteCurrencyRates(): Observable<CurrencyRatesModel>

    fun getCachedCurrencyList(): List<String>?

    fun getCachedCurrencyRates(): CurrencyRatesModel?

    fun saveAllCurrencyRatesToLocal(data: CurrencyRatesModel): Boolean

    fun addToRecentCurrencyList(currency: String)

    fun getRecentCurrencyList(): List<String>

    fun saveRecentCurrencyList(): Boolean
}