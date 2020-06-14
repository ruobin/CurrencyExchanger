package com.ruobin.currencyexchanger

interface ICurrencyConverter {
    fun convertCurrency(destinationCurrency: String): Float
}