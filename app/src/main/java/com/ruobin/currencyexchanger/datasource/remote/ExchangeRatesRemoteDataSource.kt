package com.ruobin.currencyexchanger.datasource.remote

import com.ruobin.currencyexchanger.datasource.CurrencyRatesModel
import io.reactivex.Observable
import retrofit2.http.GET

interface ExchangeRatesRemoteDataSource {

    @GET(API_REQUEST_URL)
    fun getAllLiveCurrencyRates(): Observable<CurrencyRatesModel>

    companion object {
        private const val ENDPOINT = "live"
        private const val ACCESS_KEY = "6aa8825e9fa62ad82094a0fc4a5c7045"
        const val API_REQUEST_URL = "$ENDPOINT?access_key=$ACCESS_KEY"
    }
}