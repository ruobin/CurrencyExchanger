package com.ruobin.currencyexchanger.di

import com.google.gson.Gson
import com.ruobin.currencyexchanger.datasource.remote.ExchangeRatesRemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule(private var mBaseUrl: String) {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient? {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson? {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson?, okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesExchangeRatesRemoteDataSource(retrofit: Retrofit): ExchangeRatesRemoteDataSource {
        return retrofit.create(ExchangeRatesRemoteDataSource::class.java)
    }
}