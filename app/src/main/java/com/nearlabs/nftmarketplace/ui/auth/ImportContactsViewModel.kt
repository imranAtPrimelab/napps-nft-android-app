package com.nearlabs.nftmarketplace.ui.auth

import androidx.lifecycle.ViewModel
import com.nearlabs.nftmarketplace.common.extensions.resultFlow
import com.nearlabs.nftmarketplace.data.networks.request.DtoSendTransactionRequest
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.domain.model.Wallet
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImportContactsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var recipientId: List<Contact>? = null

    fun postContacts() = resultFlow {
        repository.postLocalContact(recipientId!!)
    }

}