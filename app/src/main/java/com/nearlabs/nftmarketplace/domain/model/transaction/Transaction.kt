package com.nearlabs.nftmarketplace.domain.model.transaction

import org.ocpsoft.prettytime.PrettyTime
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

import java.util.*
import com.nearlabs.nftmarketplace.data.networks.response.DtoTransactionResponse
import org.threeten.bp.Instant


data class Transaction(
    val id: String,
    val sender: TransactionAddress,
    val receiver: TransactionAddress,
    val direction: TransactionDirection,
    val timestamp: LocalDateTime
) {
    fun identifier() = "#$id"
    fun getPrettyTime(): String {
        return PrettyTime().format(
            Date(
                timestamp.atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )
        )
    }
}

fun DtoTransactionResponse.toDomainModel() = Transaction(
    id = transactionId,
    sender = TransactionAddress(
        address = "",
        name = "",
    ),
    receiver = TransactionAddress(
        address = "",
        name = ""
    ),
    direction = if (sender == true) TransactionDirection.Outgoing else TransactionDirection.Incoming,
    timestamp = Instant.ofEpochMilli(created ?: System.currentTimeMillis())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
)