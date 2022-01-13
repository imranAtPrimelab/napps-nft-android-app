package com.nearlabs.nftmarketplace.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    fun getContacts() = resultFlow {
        repository.getContacts()
    }

    fun getLocalContacts() = resultFlow {
        repository.getLocalContact()
    }

    fun postLocalContact(contacts : List<Contact>) = resultFlow {
        repository.postLocalContact(contacts)
    }

    var itemsCopy : List<Contact>? = null

    var selectedHashSet: HashMap<Contact,Int>? = HashMap()


}