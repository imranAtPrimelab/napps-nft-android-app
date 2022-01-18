package com.nft.maker.model.settings

import com.nft.maker.model.Wallet

data class Profile(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val wallet: Wallet,
    val security: Security,
)