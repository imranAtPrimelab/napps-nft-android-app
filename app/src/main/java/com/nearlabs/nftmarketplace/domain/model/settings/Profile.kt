package com.nearlabs.nftmarketplace.domain.model.settings

import com.nearlabs.nftmarketplace.domain.model.Wallet

data class Profile(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val wallet: Wallet,
    val security: Security,
)