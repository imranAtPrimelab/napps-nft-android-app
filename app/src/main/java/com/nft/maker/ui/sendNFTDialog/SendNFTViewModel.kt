package com.nft.maker.ui.sendNFTDialog

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nft.maker.extensions.resultFlow
import com.nft.maker.data.networks.request.DtoSendTransactionRequest
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.model.Contact
import com.nft.maker.model.Wallet
import com.nft.maker.model.nft.NFT
import com.nft.maker.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendNFTViewModel @Inject constructor(
    private val sharePrefs: SharePrefs,
    private val repository: Repository
) : ViewModel() {

    var transactionItem: NFT? = null
    var recipientId: List<Contact>? = null
    var wallet: Wallet? = null


    fun getNFT() = resultFlow {
        repository.getAllNFTCollection()
    }

    fun getContacts() = resultFlow {
        repository.getContacts()
    }

    fun getWallets() = resultFlow {
        repository.getDummyWallets()
    }


    fun sendTransaction() = resultFlow {
        val request = DtoSendTransactionRequest(
            sharePrefs.userId,
            recipientId?.mapNotNull { it.contactUserId } ?: emptyList(),
            transactionItem?.id ?: "",
            ""
        )

        repository.sendTransaction(request)
    }

    val bottomVisibility : MutableLiveData<Int> =  MutableLiveData<Int>().apply {
        postValue(View.VISIBLE)
    }

}