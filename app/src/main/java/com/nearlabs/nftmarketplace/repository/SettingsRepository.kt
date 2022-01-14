package com.nearlabs.nftmarketplace.repository

import android.app.Activity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.safeCall
import com.nearlabs.nftmarketplace.data.networks.Api
import com.nearlabs.nftmarketplace.data.networks.request.DtoChangeWalletRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoLoginRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoUserCreateRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.User
import com.nearlabs.nftmarketplace.domain.model.toDomain
import com.nearlabs.nftmarketplace.domain.model.toDomainModel
import com.nearlabs.nftmarketplace.ui.auth.OTPFragment
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment

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

    suspend fun changeEmail(email: String, currentPhone: String, frag: BaseBottomSheetDialogFragment) = safeCall {

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
            val bundle = Bundle()
            bundle.putString(OTPFragment.LOGIN_TYPE, sharePrefs.loginType)
            bundle.putBoolean(OTPFragment.FROM_SETTINGS, true)
            bundle.putString(OTPFragment.EMAIL, email)
            bundle.putString(OTPFragment.PHONE, currentPhone)
            bundle.putString(OTPFragment.ID, sharePrefs.userId)
            findNavController(frag).navigate(R.id.toOtp, bundle)
        }
    }

    suspend fun changePhone(phone: String, currentEmail: String, frag: BaseBottomSheetDialogFragment) = safeCall {
        //if uses phone, have to do 2 factor
        val dToUser = DtoUserCreateRequest(sharePrefs.userName, sharePrefs.walletName, phone, currentEmail)
        if (sharePrefs.loginType == "phone")
        {
            //send OTP Request then change
            val request = DtoLoginRequest(sharePrefs.walletName)
            api.login(request)
            val bundle = Bundle()
            bundle.putString(OTPFragment.LOGIN_TYPE, sharePrefs.loginType)
            bundle.putBoolean(OTPFragment.FROM_SETTINGS, true)
            bundle.putString(OTPFragment.EMAIL, currentEmail)
            bundle.putString(OTPFragment.PHONE, phone)
            bundle.putString(OTPFragment.ID, sharePrefs.userId)
            findNavController(frag).navigate(R.id.toOtp, bundle)
        }
        else
        {
            api.modifyUser(sharePrefs.userId, dToUser)
        }
    }
}