package com.ruobin.currencyexchanger.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ruobin.currencyexchanger.datasource.CurrencyRatesModel
import java.util.*

class ExchangeRatesLocalDataSource(context: Context) {

    private val CACHE_KEY_REMOTE_FETCH_TIMESTAMP = "cache_key_remote_fetch_timestamp"
    private val CACHE_KEY_EXCHANGE_RATES = "cache_key_exchangerates"
    private val CACHE_KEY_RECENT_CURRENCY_LIST = "cache_key_recent_currency_list"

    private var sharedPrefRemoteFetchTimestamp : SharedPreferences
    private var sharedPrefExchangeRatesModel : SharedPreferences
    private var sharedPrefRecentCurrencyList : SharedPreferences

    private var cachedCurrencyRatesModel: CurrencyRatesModel? = null

    private val RECENT_CURRENCY_LIST_SIZE: Int = 9
    private var recentCurrencyQueue: LinkedList<String> = LinkedList()

    init {
        sharedPrefRemoteFetchTimestamp = context.getSharedPreferences(CACHE_KEY_REMOTE_FETCH_TIMESTAMP, Context.MODE_PRIVATE)
        sharedPrefExchangeRatesModel = context.getSharedPreferences(CACHE_KEY_EXCHANGE_RATES, Context.MODE_PRIVATE)
        sharedPrefRecentCurrencyList = context.getSharedPreferences(CACHE_KEY_RECENT_CURRENCY_LIST, Context.MODE_PRIVATE)
    }

    fun saveAllCurrencyRatesToLocal(data: CurrencyRatesModel): Boolean {
        with (sharedPrefExchangeRatesModel.edit()) {
            putString(CACHE_KEY_EXCHANGE_RATES, Gson().toJson(data))
            return commit()
        }
    }

    fun getCachedCurrencyList(): List<String>? {
        return getCachedCurrencyRates()?.quotes?.keys?.map { it -> it.substring(3)}
    }

    fun getCachedCurrencyRates(): CurrencyRatesModel? {
        if (cachedCurrencyRatesModel == null) {
            val jsonString: String? =
                sharedPrefExchangeRatesModel.getString(CACHE_KEY_EXCHANGE_RATES, "")
            if (!jsonString.isNullOrEmpty()) {
                cachedCurrencyRatesModel =
                    Gson().fromJson(jsonString, CurrencyRatesModel::class.java)
            }
        }
        return cachedCurrencyRatesModel
    }

    fun cacheOlderThan(minutes: Long): Boolean {
        val lastTimestamp: Long = sharedPrefRemoteFetchTimestamp.getLong(CACHE_KEY_REMOTE_FETCH_TIMESTAMP, 0)
        val currentTimestamp: Long = System.currentTimeMillis()
        with (sharedPrefRemoteFetchTimestamp.edit()) {
            putLong(CACHE_KEY_REMOTE_FETCH_TIMESTAMP, currentTimestamp)
            commit()
        }
        return (currentTimestamp - lastTimestamp > minutes * 60 * 1000)
    }

    fun addToRecentCurrencyList(currency: String) {
        if (getRecentCurrencyQueue().contains(currency)) {
            getRecentCurrencyQueue().remove(currency)
            getRecentCurrencyQueue().add(currency)
        } else {
            getRecentCurrencyQueue().add(currency)
            while (getRecentCurrencyQueue().size > RECENT_CURRENCY_LIST_SIZE) {
                getRecentCurrencyQueue().pop()
            }
        }
    }

    fun getRecentCurrencyList(): List<String> {
        val list: ArrayList<String> = ArrayList()
        getRecentCurrencyQueue().listIterator().forEach {
            list.add(it)
        }
        return list
    }

    private fun getRecentCurrencyQueue(): LinkedList<String> {
        if (recentCurrencyQueue.isNullOrEmpty()) {
            val jsonString: String? =
                sharedPrefRecentCurrencyList.getString(CACHE_KEY_RECENT_CURRENCY_LIST, "")
            if (!jsonString.isNullOrEmpty()) {
                recentCurrencyQueue =
                    Gson().fromJson(jsonString, LinkedList::class.java) as LinkedList<String>
            }
        }
        return recentCurrencyQueue
    }

    fun saveRecentCurrencyList(): Boolean {
        with (sharedPrefRecentCurrencyList.edit()) {
            putString(CACHE_KEY_RECENT_CURRENCY_LIST, Gson().toJson(getRecentCurrencyQueue()))
            return commit()
        }
    }
}