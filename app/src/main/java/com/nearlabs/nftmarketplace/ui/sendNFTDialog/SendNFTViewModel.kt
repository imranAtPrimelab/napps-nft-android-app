package com.nearlabs.nftmarketplace.ui.sendNFTDialog

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendNFTViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getNFT() = resultFlow {
        repository.getDummyNFTs()
    }

    fun getPeople() = resultFlow {
        repository.getDummyPeoples()
    }
}