package com.nearlabs.nftmarketplace.data.localcontact

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Patterns
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.domain.model.ContactEmail
import com.nearlabs.nftmarketplace.domain.model.ContactPhone
import timber.log.Timber
import java.util.*

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

        private val CONTACTS_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
        )
        private val CONTACTS_KINDS_PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
    }

    @SuppressLint("Range")
    override suspend fun getAllContact(userId: String): List<Contact> {
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
            val idIndex =
                cursor.getColumnIndex(COL_ID)
            do {
                val contact = Contact()
                val firstName = cursor.getString(firstNameIndex)
                val lastName = cursor.getString(lastNameIndex)
                val email = ""
                var phoneNumber = cursor.getString(dataIndex)
                if (phoneNumber.isNullOrEmpty()) {
                    continue
                }
                phoneNumber = phoneNumber.replace("[^0-9]".toRegex(), "")
                phoneNumber = formatNumber(phoneNumber)
                contact.first_name = firstName
                contact.last_name = lastName
                contact.phone = listOf(ContactPhone(phoneNumber, "local"))
                //contact.email = listOf(ContactEmail(email, "personal"))
                contact.owner_id = userId

                if (contact.first_name.equals("null") || contact.last_name.equals("null") || contact.last_name.isNullOrBlank() || contact.first_name.isNullOrBlank()) {
                    try {
                        contact.first_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[0]
                        contact.last_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[1]
                    } catch (noLastName: Exception) {
                        contact.first_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        contact.last_name = " "

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

    @SuppressLint("Range")
    override suspend fun getAllContactWithEmail(userId: String): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val cursor: Cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            CONTACTS_PROJECTION,
            null,
            null,
            null
        ) ?: return contactList
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val phones: MutableList<String> = ArrayList()
                val emails: MutableList<String> = ArrayList()
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val contactCursor: Cursor? = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        CONTACTS_KINDS_PROJECTION,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    while (contactCursor != null && contactCursor.moveToNext()) {
                        val phoneNo = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        if (!phoneNo.isNullOrEmpty()) {
                            var phoneNumber = phoneNo.replace("[^0-9]".toRegex(), "")
                            phoneNumber = formatNumber(phoneNumber)
                            phones.add(phoneNumber)
                        }
                    }
                    contactCursor?.close()
                }
                val crEmails: Cursor? = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
                            + " = ?", arrayOf(id), null
                )
                while (crEmails != null && crEmails.moveToNext()) {
                    val email = crEmails.getString(
                        crEmails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                    )
                    // Checking validation of email
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emails.add(email)
                    }
                }
                val contact = Contact()
                contact.first_name = name?.split(" ")?.firstOrNull() ?: " "
                contact.last_name = name?.split(" ")?.getOrNull(1) ?: " "
                contact.owner_id = userId
                if (emails.isNotEmpty()) {
                    contact.email = listOf(ContactEmail(emails.first(), "personal"))
                }
                if (phones.isNotEmpty()) {
                    contact.phone = listOf(ContactPhone(phones.first(), "local"))
                }
                contactList.add(contact)
                crEmails?.close()
            }
        }
        cursor.close()
        return contactList
    }
}