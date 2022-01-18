package com.nft.maker.data.networks.request

import com.nft.maker.model.Wallet

data class DtoChangeWalletRequest(
    val id: Int
)

fun Wallet.toRequest() = kotlin.run {
    DtoChangeWalletRequest(id)
}