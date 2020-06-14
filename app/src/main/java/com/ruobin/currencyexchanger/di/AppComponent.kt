package com.ruobin.currencyexchanger.di

import com.ruobin.currencyexchanger.ui.main.CurrencySelectionListFragment
import com.ruobin.currencyexchanger.ui.main.CurrencySelectionListViewModel
import com.ruobin.currencyexchanger.ui.main.MainFragment
import com.ruobin.currencyexchanger.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {

    fun inject(viewModel: MainViewModel)

    fun inject(viewModel: CurrencySelectionListViewModel)

    fun inject(mainFragment: MainFragment)

    fun inject(currencySelectionListFragment: CurrencySelectionListFragment)

}