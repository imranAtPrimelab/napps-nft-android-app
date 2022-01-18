package com.nft.maker.data.networks.response

import com.google.gson.annotations.SerializedName

data class DtoTransactionResponse(
    @SerializedName("sender_id")
    val senderId: String?,

    @SerializedName("blockchain_status")
    val blockchainStatus: String?,

    @SerializedName("created")
    val created: Long?,

    @SerializedName("updated")
    val updated: String?,

    @SerializedName("transaction_id")
    val transactionId: String,

    @SerializedName("transaction_item_id")
    val transaction_item_id: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("sender")
    val sender: Boolean?,

    @SerializedName("formattedtime")
    val dateTime: String?,

    @SerializedName("counterparty")
    val counterParty: DtoCounterParty?,
)

data class DtoCounterParty(
    @SerializedName("wallet_id")
    val walletId: String?,

    @SerializedName("full_name")
    val fullName: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phone")
    val phone: String?
)