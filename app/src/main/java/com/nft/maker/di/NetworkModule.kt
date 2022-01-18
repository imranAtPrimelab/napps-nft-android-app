package com.nft.maker.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nft.maker.BuildConfig
import com.nft.maker.data.networks.interceptor.TokenInterceptor
import com.nft.maker.data.networks.*
import com.nft.maker.data.preference.SharePrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        val builder =  OkHttpClient.Builder()
            .addNetworkInterceptor(tokenInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
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

    @Provides
    @Singleton
    fun provideUserService(
        @BaseUrl url: String,
        httpClient: OkHttpClient
    ): UserApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(
        @BaseUrl url: String,
        httpClient: OkHttpClient
    ): LoginApi {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}