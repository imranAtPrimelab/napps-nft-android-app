package com.nearlabs.nftmarketplace.domain.model

import com.nearlabs.nftmarketplace.data.networks.response.DtoContact

data class Contact(
    val name: String,
    val nick: String
)

fun DtoContact.toDomainModel() = Contact(
    name = firstName.toString(),
    nick = firstName.toString(),
)