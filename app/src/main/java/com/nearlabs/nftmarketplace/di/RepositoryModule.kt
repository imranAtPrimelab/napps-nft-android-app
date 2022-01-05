package com.nearlabs.nftmarketplace.di

import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.networks.ContactApi
import com.nearlabs.nftmarketplace.data.networks.NFTApi
import com.nearlabs.nftmarketplace.data.networks.TransactionApi
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.repository.Repository
import com.nearlabs.nftmarketplace.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        sharePrefs: SharePrefs
    ) =
        Repository(
            api,
            transactionApi,
            contactApi,
            nftApi,
            sharePrefs
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(api: Api, sharePrefs: SharePrefs) =
        SettingsRepository(api, sharePrefs)
}