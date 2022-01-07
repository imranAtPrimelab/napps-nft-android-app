package com.nearlabs.nftmarketplace.domain.model

import com.nearlabs.nftmarketplace.data.networks.response.DtoContact
import com.nearlabs.nftmarketplace.data.networks.response.DtoContactAddress
import com.nearlabs.nftmarketplace.data.networks.response.DtoContactEmail
import com.nearlabs.nftmarketplace.data.networks.response.DtoContactPhone

data class Contact(
    val birthday: String? = null,
    val address: List<ContactAddress>? = null,
    val archivedDate: Long? = null,
    val created: Long? = null,
    var lastName: String? = null,
    val groups: List<String>? = null,
    val companies: List<String>? = null,
    var phone: List<ContactPhone>? = null,
    val dob: String? = null,
    val isImported: Boolean? = null,
    val contactUserId: String? = null,
    val importSource: String? = null,
    var firstName: String? = null,
    val jobTitle: String? = null,
    val appId: String? = null,
    val updated: Long? = null,
    val email: List<ContactEmail>? = null,
    val status: String? = null
)

data class ContactEmail(
    val address: String? = null,
    val type: String? = null
)

data class ContactAddress(
    val country: String? = null,
    val city: String? = null,
    val formatted: String? = null,
    val street: String? = null,
    val region: String? = null,
    val postalCode: String? = null,
    val type: String? = null
)

data class ContactPhone(
    val number: String? = null,
    val type: String? = null
)

fun DtoContact?.toDomainModel() = this?.run {
    Contact(
        birthday = birthday,
        address = address?.mapNotNull { it.toDomainModel() },
        archivedDate = archivedDate?.toLongOrNull(),
        created = created?.toLongOrNull(),
        lastName = lastName,
        groups = groups,
        companies = companies,
        phone = phone?.mapNotNull { it.toDomainModel() },
        dob = dob,
        isImported = isImported,
        contactUserId = contactUserId,
        importSource = importSource,
        firstName = firstName,
        jobTitle = jobTitle,
        appId = appId,
        updated = updated?.toLongOrNull(),
        email = email?.mapNotNull { it.toDomainModel() },
        status = status
    )
}

fun DtoContactEmail?.toDomainModel() = this?.run {
    ContactEmail(
        address = address,
        type = type
    )
}

fun DtoContactAddress?.toDomainModel() = this?.run {
    ContactAddress(
        country = country,
        city = country,
        formatted = formatted,
        street = street,
        region = region,
        postalCode = postalCode,
        type = type
    )
}

fun DtoContactPhone?.toDomainModel() = this?.run {
    ContactPhone(
        number = number,
        type = type
    )
}