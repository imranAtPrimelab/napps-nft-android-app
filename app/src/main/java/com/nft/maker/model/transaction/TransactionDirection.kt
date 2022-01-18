package com.nft.maker.model.transaction

sealed class TransactionDirection {
    object Incoming: TransactionDirection()
    object Outgoing: TransactionDirection()
}