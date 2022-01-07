package com.nearlabs.nftmarketplace.data.localcontact

import com.nearlabs.nftmarketplace.domain.model.Contact

interface ContactSource {
    suspend fun getAllContact() : List<Contact>
}