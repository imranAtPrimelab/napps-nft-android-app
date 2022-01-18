package com.nft.maker.model

import com.nft.maker.data.networks.response.DtoWallet
import com.nft.maker.data.networks.response.DtoWalletResponse

data class Wallet(
    val id: Int,
    val address: String,
    val name: String,
    val selected: Boolean
)

fun DtoWallet.toDomainModel() = kotlin.run {
    Wallet(
        id = id,
        name = name.toString(),
        address = address.toString(),
        selected = selected ?: false
    )
}

fun DtoWalletResponse.toDomainModel() = kotlin.run {
    wallets?.map { it.toDomainModel() } ?: emptyList()
}