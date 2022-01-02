package com.nearlabs.nftmarketplace.domain.model

import com.nearlabs.nftmarketplace.data.networks.response.DtoUserResponse

data class User(
    val id: Int,
    val name: String,
    val nick: String,
    val image: String
)

fun DtoUserResponse.toDomainModel() = User(
    id = id,
    name = name.toString(),
    nick = nick.toString(),
    image = image.toString()
)