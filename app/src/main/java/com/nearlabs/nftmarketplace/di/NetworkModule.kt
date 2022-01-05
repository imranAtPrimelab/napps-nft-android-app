package com.nearlabs.nftmarketplace.di

import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.networks.TransactionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @BaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl() = "https://bib4ivjb9i.execute-api.us-west-1.amazonaws.com"

    @TransactionUrl
    @Singleton
    @Provides
    fun provideTransactionUrl() = "https://oj472d2cb3.execute-api.us-west-1.amazonaws.com"


    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
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
    fun provideTransactionService(@BaseUrl transactionUrl: String, httpClient: OkHttpClient) : TransactionApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(transactionUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionApi::class.java)
    }
}