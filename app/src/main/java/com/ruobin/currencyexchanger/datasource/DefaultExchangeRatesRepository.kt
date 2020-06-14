package com.ruobin.currencyexchanger.datasource

import com.ruobin.currencyexchanger.datasource.local.ExchangeRatesLocalDataSource
import com.ruobin.currencyexchanger.datasource.remote.ExchangeRatesRemoteDataSource
import io.reactivex.Observable

class DefaultExchangeRatesRepository(private var localDataSource: ExchangeRatesLocalDataSource, private var exchangeRatesApi: ExchangeRatesRemoteDataSource) : ExchangeRatesRepository {

    private var cacheMaxDurationInMinutes: Long = 30

    override fun getFusedCurrencyRates(): Observable<CurrencyRatesModel> {
        return if (localDataSource.cacheOlderThan(cacheMaxDurationInMinutes) || getCachedCurrencyRates() == null) {
            getRemoteCurrencyRates()
        } else {
            Observable.just(getCachedCurrencyRates())
        }
    }

    override fun getRemoteCurrencyRates(): Observable<CurrencyRatesModel> {
        return exchangeRatesApi.getAllLiveCurrencyRates()
    }

    override fun saveAllCurrencyRatesToLocal(data: CurrencyRatesModel): Boolean {
        return localDataSource.saveAllCurrencyRatesToLocal(data)
    }

    override fun getCachedCurrencyList(): List<String>? {
        return localDataSource.getCachedCurrencyList()
    }

    override fun getCachedCurrencyRates(): CurrencyRatesModel? {
        return localDataSource.getCachedCurrencyRates()
    }

    override fun addToRecentCurrencyList(currency: String) {
        localDataSource.addToRecentCurrencyList(currency)
    }

    override fun getRecentCurrencyList(): List<String> {
        return localDataSource.getRecentCurrencyList()
    }

    override fun saveRecentCurrencyList(): Boolean {
        return localDataSource.saveRecentCurrencyList()
    }

}
