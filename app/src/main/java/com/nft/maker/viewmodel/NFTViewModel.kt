package com.nft.maker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nft.maker.extensions.resultFlow
import com.nft.maker.repository.Repository
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

    val shouldRefresh : MutableLiveData<Boolean> =  MutableLiveData<Boolean>().apply {
        postValue(false)
    }

}