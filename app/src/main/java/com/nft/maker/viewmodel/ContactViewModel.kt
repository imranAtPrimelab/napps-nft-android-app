package com.nft.maker.viewmodel

import androidx.lifecycle.ViewModel
import com.nft.maker.extensions.resultFlow
import com.nft.maker.model.Contact
import com.nft.maker.repository.Repository
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

    fun postAddLocalContact(contacts : Contact) = resultFlow {
        repository.postAddLocalContact(contacts)
    }

    var itemsCopy : List<Contact>? = null

    var selectedHashSet: HashMap<Contact,Int>? = HashMap()


}