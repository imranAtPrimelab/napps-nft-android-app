package com.nft.maker.data.localcontact

import com.nft.maker.model.Contact

interface ContactSource {
    suspend fun getAllContact(userId: String): List<Contact>
    suspend fun getAllContactWithEmail(userId: String): List<Contact>
}