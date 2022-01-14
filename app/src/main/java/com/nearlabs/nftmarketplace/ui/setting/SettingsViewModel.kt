package com.nearlabs.nftmarketplace.ui.setting

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.data.networks.request.toRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.User
import com.nearlabs.nftmarketplace.domain.model.Wallet
import com.nearlabs.nftmarketplace.repository.SettingsRepository
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository, private val sharePrefs: SharePrefs) :
    ViewModel() {

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

    fun changeEmail(email: String, currentPhone: String, frag: BaseBottomSheetDialogFragment) = resultFlow {
        settingsRepository.changeEmail(email, currentPhone, frag)
    }

    fun changePhone(phone: String, currentEmail: String, frag: BaseBottomSheetDialogFragment) = resultFlow {
        settingsRepository.changePhone(phone, currentEmail, frag)
    }
}