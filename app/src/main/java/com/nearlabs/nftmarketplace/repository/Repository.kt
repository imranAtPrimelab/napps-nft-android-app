package com.nearlabs.nftmarketplace.repository

import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.toDomainModel

class Repository(private val api: Api, private val sharePrefs: SharePrefs) {

    fun isLoggedIn() = sharePrefs.isLoggedIn

    suspend fun getUsers() = safeCall {
        val dtoUsers = api.getUsers()
        dtoUsers.map { it.toDomainModel() }
    }

    suspend fun getDummyTransactions() = safeCall {
        DummyDataGenerator.transactions()
    }

    suspend fun getDummyNFTs() = safeCall {
        DummyDataGenerator.NFTs()
    }
}