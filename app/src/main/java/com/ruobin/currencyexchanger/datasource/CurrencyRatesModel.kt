package com.ruobin.currencyexchanger.datasource

data class CurrencyRatesModel(var success: Boolean = false, var source: String, var quotes: Map<String, Float>, var terms: String, var privacy: String, var timestamp: Long) {

}