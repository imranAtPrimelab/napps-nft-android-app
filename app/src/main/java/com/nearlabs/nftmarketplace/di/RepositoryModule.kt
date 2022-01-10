package com.nearlabs.nftmarketplace.di

import android.content.Context
import com.nearlabs.nftmarketplace.data.localcontact.ContactSource
import com.nearlabs.nftmarketplace.data.localcontact.LocalContact
import com.nearlabs.nftmarketplace.data.networks.*
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.repository.Repository
import com.nearlabs.nftmarketplace.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        api: Api,
        transactionApi: TransactionApi,
        nftApi: NFTApi,
        contactApi: ContactApi,
        userApi: UserApi,
        loginApi: LoginApi,
        sharePrefs: SharePrefs,
        localContact: ContactSource
    ) =
        Repository(
            api,
            transactionApi,
            contactApi,
            nftApi,
            userApi,
            loginApi,
            sharePrefs,
            localContact
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(api: Api, sharePrefs: SharePrefs) =
        SettingsRepository(api, sharePrefs)

    @Provides
    @Singleton
    fun providerContactSource(@ApplicationContext context: Context): ContactSource =
        LocalContact(context)
}