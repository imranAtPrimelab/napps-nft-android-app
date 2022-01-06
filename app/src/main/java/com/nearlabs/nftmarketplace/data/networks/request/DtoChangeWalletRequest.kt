package com.nearlabs.nftmarketplace.data.networks.request

import com.nearlabs.nftmarketplace.domain.model.Wallet

data class DtoChangeWalletRequest(
    val id: Int
)

fun Wallet.toRequest() = kotlin.run {
    DtoChangeWalletRequest(id)
}