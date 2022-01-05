package com.nearlabs.nftmarketplace.di

import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.networks.ContactApi
import com.nearlabs.nftmarketplace.data.networks.NFTApi
import com.nearlabs.nftmarketplace.data.networks.TransactionApi
import com.nearlabs.nftmarketplace.data.networks.interceptor.TokenInterceptor
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @BaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl() = "https://api.nearlogin.io"

    @TransactionUrl
    @Singleton
    @Provides
    fun provideTransactionUrl() = "https://api.nearlogin.io"

    @ContactUrl
    @Singleton
    @Provides
    fun provideContactUrl() = "https://api.nearlogin.io"

    @NFTUrl
    @Singleton
    @Provides
    fun provideNFTUrl() = "https://api.nearlogin.io"

    @Singleton
    @Provides
    fun provideTokenInterceptor(sharedPreferences: SharePrefs): Interceptor {
        return TokenInterceptor(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideHttpClient(tokenInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(tokenInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseService(
        @BaseUrl baseUrl: String,
        httpClient: OkHttpClient
    ): Api {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionService(
        @BaseUrl transactionUrl: String,
        httpClient: OkHttpClient
    ): TransactionApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(transactionUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNftService(
        @NFTUrl url: String,
        httpClient: OkHttpClient
    ): NFTApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NFTApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContactService(
        @ContactUrl url: String,
        httpClient: OkHttpClient
    ): ContactApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContactApi::class.java)
    }
}