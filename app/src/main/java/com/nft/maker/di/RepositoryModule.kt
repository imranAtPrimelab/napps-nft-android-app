package com.nft.maker.di

import android.content.Context
import com.nft.maker.data.localcontact.ContactSource
import com.nft.maker.data.localcontact.LocalContact
import com.nft.maker.data.networks.*
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.repository.Repository
import com.nft.maker.repository.SettingsRepository
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