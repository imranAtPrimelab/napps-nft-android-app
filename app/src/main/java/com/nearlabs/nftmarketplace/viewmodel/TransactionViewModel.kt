package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getRecentTransactions() = resultFlow {
        repository.getRecentTransactions()
    }

    fun getTransactions() = resultFlow {
        repository.getTransactions()
    }

    fun getSentTransactions() = resultFlow {
        repository.getSentTransactions()
    }

    fun getRecvTransactions() = resultFlow {
        repository.getRecvTransactions()
    }
}