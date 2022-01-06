package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository,private val sharePrefsRepository: SharePrefs) : ViewModel() {
    var currentEmail: String = ""
    var currentPhone: String = ""
    val walletName = sharePrefsRepository.walletName
    val loginType = sharePrefsRepository.loginType

    fun createUser(name: String, walletId: String) = resultFlow {
        repository.createUser(name, walletId, currentPhone, currentEmail)
    }

    fun loginUser(walletName : String) = resultFlow {
        repository.login(walletName)
    }

    fun verifyUser(walletName : String, nonce :String) = resultFlow {
        repository.verifyLogin(walletName, nonce)
    }


}