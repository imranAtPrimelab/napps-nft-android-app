package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var currentEmail: String = ""
    var currentPhone: String = ""

    fun createUser(name: String, walletId: String) = resultFlow {
        repository.createUser(name, walletId, currentPhone, currentEmail)
    }
}