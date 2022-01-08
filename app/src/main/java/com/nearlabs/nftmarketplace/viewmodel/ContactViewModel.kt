package com.nearlabs.nftmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getContacts() = resultFlow {
        repository.getContacts()
    }


}*/