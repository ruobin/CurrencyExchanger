package com.ruobin.currencyexchanger.di

import android.content.Context
import com.ruobin.currencyexchanger.CurrencyExchangerApplication
import com.ruobin.currencyexchanger.datasource.DefaultExchangeRatesRepository
import com.ruobin.currencyexchanger.datasource.ExchangeRatesRepository
import com.ruobin.currencyexchanger.datasource.local.ExchangeRatesLocalDataSource
import com.ruobin.currencyexchanger.datasource.remote.ExchangeRatesRemoteDataSource
import com.ruobin.currencyexchanger.ui.main.CurrencySelectionListViewModel
import com.ruobin.currencyexchanger.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(application: CurrencyExchangerApplication) {

    private var application: CurrencyExchangerApplication = application

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideExchangeRatesRepository(localDataSource: ExchangeRatesLocalDataSource, exchangeRatesApi: ExchangeRatesRemoteDataSource): ExchangeRatesRepository {
        return DefaultExchangeRatesRepository(localDataSource, exchangeRatesApi)
    }

    @Provides
    @Singleton
    fun providesExchangeRatesLocalDataSource(context: Context): ExchangeRatesLocalDataSource {
        return ExchangeRatesLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideMainViewModel(repository: ExchangeRatesRepository): MainViewModel {
        return MainViewModel(repository)
    }

    @Provides
    @Singleton
    fun provideCurrencySelectionListViewModel(repository: ExchangeRatesRepository): CurrencySelectionListViewModel {
        return CurrencySelectionListViewModel(repository)
    }

}