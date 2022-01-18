package com.nft.maker.ui.setting

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.nft.maker.extensions.resultFlow
import com.nft.maker.data.networks.request.toRequest
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.model.Wallet
import com.nft.maker.repository.SettingsRepository
import com.nft.maker.ui.auth.OTPFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository, private val sharePrefs: SharePrefs) :
    ViewModel() {

    var settingFragment: SettingFragment? = null

    fun getWallets() = resultFlow {
        settingsRepository.getWallets()
    }

    fun selectWallet(wallet: Wallet) = resultFlow {
        settingsRepository.changeWallet(wallet.toRequest())
    }

    fun addWallet(name: String) = resultFlow {
        settingsRepository.addWallet(name)
    }

    fun getUserProfile() = resultFlow {
        settingsRepository.getUserProfile(sharePrefs.userId)
    }

    fun clearPref() {
        sharePrefs.clear()
    }

    fun changeName(name: String, phone: String, email: String) = resultFlow {
        settingsRepository.changeName(name, phone, email)
    }

    fun checkShouldChangeEmail(email: String, currentPhone: String): Bundle? {
        if (sharePrefs.loginType == "phone")
        {
            changeEmail(email, currentPhone)
            return null
        }
        else
        {
            changeEmail(email, currentPhone)
            val bundle = Bundle()
            bundle.putString(OTPFragment.LOGIN_TYPE, sharePrefs.loginType)
            bundle.putBoolean(OTPFragment.FROM_SETTINGS, true)
            bundle.putString(OTPFragment.EMAIL, email)
            bundle.putString(OTPFragment.PHONE, currentPhone)
            bundle.putString(OTPFragment.ID, sharePrefs.userId)
            return bundle
        }
    }

    fun checkShouldChangePhone(phone: String, currentEmail: String): Bundle? {
        if (sharePrefs.loginType == "phone")
        {
            changePhone(phone, currentEmail)
            val bundle = Bundle()
            bundle.putString(OTPFragment.LOGIN_TYPE, sharePrefs.loginType)
            bundle.putBoolean(OTPFragment.FROM_SETTINGS, true)
            bundle.putString(OTPFragment.EMAIL, currentEmail)
            bundle.putString(OTPFragment.PHONE, phone)
            bundle.putString(OTPFragment.ID, sharePrefs.userId)
            return bundle
        }
        else
        {
            changePhone(phone, currentEmail)
            return null
        }
    }

    fun changeEmail(email: String, currentPhone: String) = resultFlow {
        settingsRepository.changeEmail(email, currentPhone)
    }

    fun changePhone(phone: String, currentEmail: String) = resultFlow {
        settingsRepository.changePhone(phone, currentEmail)
    }
}