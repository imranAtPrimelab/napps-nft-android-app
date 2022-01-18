package com.nft.maker.data.networks.request

import com.google.gson.annotations.SerializedName

data class DtoSendTransactionRequest(
    @SerializedName("sender_id")
    val senderId: String,
    @SerializedName("recipient_id")
    val recipientId: List<String>,
    @SerializedName("transaction_item_id")
    val transactionItemId: String,
    @SerializedName("transaction_value")
    val transactionValue: String,
    @SerializedName("type")
    val type: String = "gift",
)