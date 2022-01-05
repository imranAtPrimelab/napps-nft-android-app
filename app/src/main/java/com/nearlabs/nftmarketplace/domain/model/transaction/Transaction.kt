package com.nearlabs.nftmarketplace.domain.model.transaction

import com.nearlabs.nftmarketplace.data.networks.response.DtoTransaction


data class Transaction(
    val id: Long,
    val sender: TransactionAddress,
    val receiver: TransactionAddress,
    val direction: TransactionDirection,
    val timestamp: Long
) {
    fun identifier() = "#$id"
}

fun DtoTransaction.toDomainModel() = Transaction(
    id = 0L,
    sender = TransactionAddress(
        address = "",
        name = "",
    ),
    receiver = TransactionAddress(
        address = "",
        name = ""
    ),
    direction = TransactionDirection.Incoming,
    timestamp = System.currentTimeMillis()
)