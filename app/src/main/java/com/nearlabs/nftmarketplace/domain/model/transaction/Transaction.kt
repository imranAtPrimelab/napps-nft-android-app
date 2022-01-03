package com.nearlabs.nftmarketplace.domain.model.transaction


data class Transaction(
    val id: Long,
    val sender: TransactionAddress,
    val receiver: TransactionAddress,
    val direction: TransactionDirection,
    val timestamp: Long
) {

}