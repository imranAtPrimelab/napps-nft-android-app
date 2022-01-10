package com.nearlabs.nftmarketplace.ui.sendNFTDialog

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
}