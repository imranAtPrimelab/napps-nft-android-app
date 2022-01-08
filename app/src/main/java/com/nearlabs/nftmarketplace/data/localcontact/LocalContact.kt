package com.nearlabs.nftmarketplace.data.localcontact

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.text.TextUtils
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.domain.model.ContactEmail
import com.nearlabs.nftmarketplace.domain.model.ContactPhone
import timber.log.Timber

class LocalContact(val context: Context) : ContactSource {

    companion object {
        private val CONTACT_URI = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI
        private const val COL_ID = ContactsContract.RawContacts.CONTACT_ID
        private const val GIVEN_NAME = ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
        private const val FAMILY_NAME = ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
        private const val COL_NUMBER = ContactsContract.CommonDataKinds.Contactables.DATA
        private const val COLUMN_EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS

        private val projection = arrayOf(
            COL_ID,
            GIVEN_NAME,
            FAMILY_NAME,
            COL_NUMBER,
            COLUMN_EMAIL
        )
    }

    override suspend fun getAllContact(userId: String): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val selection = ContactsContract.Data.MIMETYPE + " in (?, ?)";
        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        val sortOrder = "UPPER($GIVEN_NAME) ASC"

        val cursor: Cursor? = context.contentResolver.query(
            CONTACT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        if (cursor == null) {
            Timber.e("The cursor is null")
            return emptyList()
        }

        if (cursor.moveToNext()) {
            val firstNameIndex = cursor.getColumnIndex(GIVEN_NAME)
            val lastNameIndex = cursor.getColumnIndex(FAMILY_NAME)
            val dataIndex = cursor.getColumnIndex(COL_NUMBER)
            val dataIndexEmail = cursor.getColumnIndex(COLUMN_EMAIL)
            do {
                val contact = Contact()
                val firstName = cursor.getString(firstNameIndex)
                val lastName = cursor.getString(lastNameIndex)
                var phoneNumber = cursor.getString(dataIndex)
                var email = "manmohan@primelab.io"

                if (phoneNumber.isNullOrEmpty()) {
                    phoneNumber =  ""
                }
                if (email.isNullOrEmpty()) {
                    email = ""
                }
                phoneNumber = phoneNumber.replace("[^0-9]".toRegex(), "")
                phoneNumber = formatNumber(phoneNumber)
                contact.firstName = firstName
                contact.lastName = lastName
                contact.owner_id = userId
                if (!phoneNumber.isNullOrEmpty()) {
                    contact.phone = listOf(ContactPhone(phoneNumber, "local"))
                }
                if (!email.isNullOrEmpty()) {
                    contact.email = listOf(ContactEmail(email, "local"))
                }
                if (contact.phone.isNullOrEmpty() && contact.email.isNullOrEmpty()) {
                    continue
                }
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return contactList
    }

    private fun formatNumber(input: String): String {
        return input
    }
}