package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getRecentTransactions() = resultFlow {
        repository.getDummyTransactions()
    }

    fun getTransactions() = resultFlow {
        repository.getDummyTransactions()
    }

    fun getSentTransactions() = resultFlow {
        repository.getDummySentTransactions()
    }

    fun getRecvTransactions() = resultFlow {
        repository.getDummyRecvTransactions()
    }
}