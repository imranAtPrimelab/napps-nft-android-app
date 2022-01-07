package com.nearlabs.nftmarketplace.data.localcontact

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.text.TextUtils
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.domain.model.ContactPhone
import timber.log.Timber

class LocalContact(val context: Context) : ContactSource {

    companion object {
        private val CONTACT_URI = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI
        private val COL_ID = ContactsContract.RawContacts.CONTACT_ID
        private val GIVEN_NAME = ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
        private val FAMILY_NAME = ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
        private val COL_NUMBER = ContactsContract.CommonDataKinds.Contactables.DATA

        private val projection = arrayOf(
            COL_ID,
            GIVEN_NAME,
            FAMILY_NAME,
            COL_NUMBER
        )
    }

    override suspend fun getAllContact(): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val selection = ContactsContract.Data.MIMETYPE + " in (?, ?)" + " AND " +
                ContactsContract.Data.HAS_PHONE_NUMBER + " = '" + 1 + "'"
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
            val firstNameIndex =
                cursor.getColumnIndex(GIVEN_NAME)
            val lastNameIndex =
                cursor.getColumnIndex(FAMILY_NAME)
            val dataIndex =
                cursor.getColumnIndex(COL_NUMBER)
            do {
                val contact = Contact()
                val firstName = cursor.getString(firstNameIndex)
                val lastName = cursor.getString(lastNameIndex)
                var phoneNumber = cursor.getString(dataIndex)
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue
                }
                phoneNumber = phoneNumber.replace("[^0-9]".toRegex(), "")
                phoneNumber = formatNumber(phoneNumber)
                contact.firstName = firstName
                contact.lastName = lastName
                contact.phone = listOf(
                    ContactPhone(phoneNumber, "local")
                )
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