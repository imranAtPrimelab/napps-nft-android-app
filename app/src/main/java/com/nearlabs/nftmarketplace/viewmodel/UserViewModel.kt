package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository, sharePrefsRepository: SharePrefs) : ViewModel() {
    var currentEmail: String = ""
    var currentPhone: String = ""
    var usesPhone: Boolean = true
    var walletName = sharePrefsRepository.walletName
    val loginType = sharePrefsRepository.loginType

    fun createUser(name: String, walletId: String, claimNFTID: String? = null) = resultFlow {
        repository.createUser(name, walletId, currentPhone, currentEmail, claimNFTID)
    }

    fun loginUser(walletName: String) = resultFlow {
        repository.login(walletName)
    }

    fun verifyUser(walletName: String, nonce: String) = resultFlow {
        repository.verifyLogin(walletName, nonce)
    }

    fun updateUser(userId: String) = resultFlow {
        repository.modifyUser(userId, currentPhone, currentEmail)
    }


}