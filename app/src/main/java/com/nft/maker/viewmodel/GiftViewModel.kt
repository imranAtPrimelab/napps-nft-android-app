package com.nft.maker.viewmodel

import androidx.lifecycle.ViewModel
import com.nft.maker.extensions.resultFlow
import com.nft.maker.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GiftViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getContacts() = resultFlow {
        repository.getDummyNFTs()
    }
}