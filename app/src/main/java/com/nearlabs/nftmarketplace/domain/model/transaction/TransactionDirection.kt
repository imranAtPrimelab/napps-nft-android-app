package com.nearlabs.nftmarketplace.domain.model.transaction

sealed class TransactionDirection {
    object Incoming: TransactionDirection()
    object Outgoing: TransactionDirection()
}