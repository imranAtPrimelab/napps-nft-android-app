package com.nearlabs.nftmarketplace.domain.model.transaction

import com.nearlabs.nftmarketplace.data.networks.response.DtoTransaction
import org.ocpsoft.prettytime.PrettyTime
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

import java.util.*


data class Transaction(
    val id: Long,
    val sender: TransactionAddress,
    val receiver: TransactionAddress,
    val direction: TransactionDirection,
    val timestamp: LocalDateTime
) {
    fun identifier() = "#$id"
    fun getPrettyTime() : String {
        return PrettyTime().format(
            Date(timestamp.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli())
        )
    }
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
    timestamp = LocalDateTime.now()
)