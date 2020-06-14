package com.ruobin.currencyexchanger

import android.app.Application
import com.ruobin.currencyexchanger.di.AppComponent
import com.ruobin.currencyexchanger.di.AppModule
import com.ruobin.currencyexchanger.di.DaggerAppComponent
import com.ruobin.currencyexchanger.di.NetModule

class CurrencyExchangerApplication: Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule("http://api.currencylayer.com/"))
            .build()
    }

    fun getComponent(): AppComponent? {
        return component
    }
}