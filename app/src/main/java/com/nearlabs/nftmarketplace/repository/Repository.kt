package com.nearlabs.nftmarketplace.repository

import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.networks.NFTApi
import com.nearlabs.nftmarketplace.data.networks.TransactionApi
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.nft.toDomainModel
import com.nearlabs.nftmarketplace.domain.model.toDomainModel
import com.nearlabs.nftmarketplace.domain.model.transaction.TransactionDirection
import com.nearlabs.nftmarketplace.domain.model.transaction.toDomainModel

class Repository(
    private val api: Api,
    private val transactionApi: TransactionApi,
    private val nftApi: NFTApi,
    private val sharePrefs: SharePrefs
) {

    fun isLoggedIn() = sharePrefs.isLoggedIn

    suspend fun getUsers() = safeCall {
        val dtoUsers = api.getUsers()
        dtoUsers.map { it.toDomainModel() }
    }

    suspend fun getTransactions() = safeCall {
        val dtoTransactions = transactionApi.getTransaction()
        dtoTransactions.map { it.toDomainModel() }
    }

    suspend fun getDummyTransactions() = safeCall {
        DummyDataGenerator.transactions()
    }

    suspend fun getDummySentTransactions() = safeCall {
        DummyDataGenerator.transactions().filter { it.direction == TransactionDirection.Outgoing }
    }

    suspend fun getDummyRecvTransactions() = safeCall {
        DummyDataGenerator.transactions().filter { it.direction == TransactionDirection.Incoming }
    }

    suspend fun getDummyNFTs() = safeCall {
        DummyDataGenerator.NFTs()
    }

    suspend fun getAllNFTCollection() = safeCall {
        val userId = sharePrefs.getUserId()
        val dtoNft = nftApi.getAllNFTCollections(userId)
        dtoNft.data.map { it.toDomainModel() }
    }
}