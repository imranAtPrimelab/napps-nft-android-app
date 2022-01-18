package com.nft.maker.repository

import com.nft.maker.extensions.safeCall
import com.nft.maker.data.networks.Api
import com.nft.maker.data.networks.request.DtoChangeWalletRequest
import com.nft.maker.data.networks.request.DtoLoginRequest
import com.nft.maker.data.networks.request.DtoUserCreateRequest
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.model.toDomain
import com.nft.maker.model.toDomainModel

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
    suspend fun getUserProfile(userId: String) = safeCall {
        val dtoResponse = api.getUserProfile(userId)
        dtoResponse.dtoUserInfo.toDomain()
    }

    suspend fun changeName(name: String, phone: String, email:String) = safeCall {
        val dToUser = DtoUserCreateRequest(name, sharePrefs.walletName, phone, email)
        sharePrefs.userName = name
        api.modifyUser(sharePrefs.userId, dToUser)
    }

    suspend fun changeEmail(email: String, currentPhone: String) = safeCall {

        val dToUser = DtoUserCreateRequest(sharePrefs.userName, sharePrefs.walletName, currentPhone, email)
        //if uses phone, just update it
        if (sharePrefs.loginType == "phone")
        {
            api.modifyUser(sharePrefs.userId, dToUser)
        }
        else
        {
            //send OTP Request then change
            val request = DtoLoginRequest(sharePrefs.walletName)
            api.login(request)
        }
    }

    suspend fun changePhone(phone: String, currentEmail: String) = safeCall {
        //if uses phone, have to do 2 factor
        val dToUser = DtoUserCreateRequest(sharePrefs.userName, sharePrefs.walletName, phone, currentEmail)
        if (sharePrefs.loginType == "phone")
        {
            //send OTP Request then change
            val request = DtoLoginRequest(sharePrefs.walletName)
            api.login(request)
        }
        else
        {
            api.modifyUser(sharePrefs.userId, dToUser)
        }
    }
}