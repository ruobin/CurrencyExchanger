package com.ruobin.currencyexchanger

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ruobin.currencyexchanger.datasource.CurrencyRatesModel
import com.ruobin.currencyexchanger.datasource.local.ExchangeRatesLocalDataSource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExchangeRatesLocalDataSourceUnitTest {

    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences
    @Mock
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    private val CACHE_KEY_EXCHANGE_RATES = "cache_key_exchangerates"

    @Before
    fun setup() {
        `when`(
            mockContext.getSharedPreferences(
                anyString(),
                anyInt()
            )
        ).thenReturn(mockSharedPreferences)
        `when`(
            mockSharedPreferences.edit()
        ).thenReturn(mockSharedPreferencesEditor)
    }

    @Test
    fun saveAllCurrencyRatesToLocal_isCorrect() {
        var quotesMap: HashMap<String, Float> = HashMap()
        quotesMap.put("USDAED", 3.67295F)
        val model = CurrencyRatesModel(true, "USD", quotesMap, "terms", "privacy", System.currentTimeMillis())
        val jsonString = Gson().toJson(model)
        ExchangeRatesLocalDataSource(mockContext).saveAllCurrencyRatesToLocal(model)
        Mockito.verify(mockSharedPreferencesEditor).putString(CACHE_KEY_EXCHANGE_RATES, jsonString)
        Mockito.verify(mockSharedPreferencesEditor).commit()
    }

    @Test
    fun cacheOlderThanReturnTrue_isCorrect() {
        val now = System.currentTimeMillis()
        `when`(
            mockSharedPreferences.getLong(
                anyString(),
                anyLong())
        ).thenReturn(now - 31 * 60 * 1000)
        val result: Boolean = ExchangeRatesLocalDataSource(mockContext).cacheOlderThan(30)
        Mockito.verify(mockSharedPreferencesEditor).putLong(anyString(), anyLong())
        Mockito.verify(mockSharedPreferencesEditor).commit()
        Assert.assertTrue(result)
    }

    @Test
    fun cacheOlderThanReturnFalse_isCorrect() {
        val now = System.currentTimeMillis()
        `when`(
            mockSharedPreferences.getLong(
                anyString(),
                anyLong())
        ).thenReturn(now - 29 * 60 * 1000)
        val result: Boolean = ExchangeRatesLocalDataSource(mockContext).cacheOlderThan(30)
        Mockito.verify(mockSharedPreferencesEditor).putLong(anyString(), anyLong())
        Mockito.verify(mockSharedPreferencesEditor).commit()
        Assert.assertFalse(result)
    }
}