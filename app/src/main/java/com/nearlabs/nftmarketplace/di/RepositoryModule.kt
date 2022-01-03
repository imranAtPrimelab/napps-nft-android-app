package com.nearlabs.nftmarketplace.di

import android.content.Context
import android.content.SharedPreferences
import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.repository.Repository
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
    fun provideRepository(api: Api, sharePrefs: SharePrefs) = Repository(api, sharePrefs)
}