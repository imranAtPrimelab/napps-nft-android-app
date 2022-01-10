package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NFTViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getNFTs() = resultFlow {
        repository.getDummyNFTs()
    }

    fun getAllNFTCollection() = resultFlow {
        repository.getAllNFTCollection()
    }

    fun getNftDetails(nftId: String) = resultFlow {
        repository.getNFTDetails(nftId)
    }

    fun claimNft(nftId: String) = resultFlow {
        repository.claimNFT(nftId)
    }

    fun getNftCreator(userId: String) = resultFlow {
        repository.getUserProfile(userId)
    }
}