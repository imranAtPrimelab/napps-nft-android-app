package com.nearlabs.nftmarketplace.ui.setting

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.data.networks.request.toRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.Wallet
import com.nearlabs.nftmarketplace.repository.SettingsRepository
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
}