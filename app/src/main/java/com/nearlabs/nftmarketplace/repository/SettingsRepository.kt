package com.nearlabs.nftmarketplace.repository

import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.networks.request.DtoAddWalletRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoChangeWalletRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.toDomainModel

class SettingsRepository(private val api: Api, private val sharePrefs: SharePrefs) {

    suspend fun getWallets() = safeCall {
        val dtoWallet = /*api.getWallets()*/DummyDataGenerator.wallets()
        dtoWallet.toDomainModel()
    }

    suspend fun changeWallet(request: DtoChangeWalletRequest) = safeCall {
//        val response = api.changeWallet(request)
//        response.isSuccessful
        true
    }

    suspend fun addWallet(name: String) = safeCall {
//        val response = api.addWallet(DtoAddWalletRequest(name))
//        response.isSuccessful
        true
    }
}