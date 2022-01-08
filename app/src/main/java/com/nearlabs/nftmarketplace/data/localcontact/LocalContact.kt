package com.nearlabs.nftmarketplace.data.localcontact

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.text.TextUtils
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.domain.model.ContactEmail
import com.nearlabs.nftmarketplace.domain.model.ContactPhone
import timber.log.Timber
import java.lang.Exception

class LocalContact(val context: Context) : ContactSource {

    companion object {
        private val CONTACT_URI = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI
        private const val COL_ID = ContactsContract.RawContacts.CONTACT_ID
        private const val GIVEN_NAME = ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
        private const val FAMILY_NAME = ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
        private const val COL_NUMBER = ContactsContract.CommonDataKinds.Contactables.DATA
        private const val EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS
        private const val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME

        private val projection = arrayOf(
            COL_ID,
            GIVEN_NAME,
            FAMILY_NAME,
            COL_NUMBER,
            EMAIL,
            DISPLAY_NAME
        )
    }

    @SuppressLint("Range")
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
            val emailIndex =
                cursor.getColumnIndex(EMAIL)
            do {
                val contact = Contact()
                val firstName = cursor.getString(firstNameIndex)
                val lastName = cursor.getString(lastNameIndex)
                var email = cursor.getString(emailIndex)
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
                contact.email = listOf(
                    ContactEmail(email,"personal")
                )
                contact.user_id = cursor.getString(dataIndex)

                if(contact.firstName.equals("null") || contact.lastName.equals("null") ||contact.lastName.isNullOrBlank() ||contact.firstName.isNullOrBlank()){
                    try {
                        contact.firstName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[0]
                        contact.lastName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[1]
                    }catch (noLastName : Exception){
                        contact.firstName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        contact.lastName = " "

                    }
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