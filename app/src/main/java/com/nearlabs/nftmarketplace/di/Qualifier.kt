package com.nearlabs.nftmarketplace.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TransactionUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NFTUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PreferenceName