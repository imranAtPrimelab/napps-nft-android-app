package com.nearlabs.nftmarketplace.ui.sendNFTDialog

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogImportPeopleBinding
import com.nearlabs.nftmarketplace.databinding.DialogSendSelectPeopleNtfBinding
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.base.adapter.MULTI
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter.PeopleAdapter
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.CONTACTS_PERMISSION_GRANTED_EVENT_NAME
import android.provider.ContactsContract

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.database.Cursor
import com.nearlabs.nftmarketplace.domain.model.ContactPhone
import java.lang.Exception


class ImportPeopleDialog : BaseBottomSheetDialogFragment() {
    private lateinit var binding: DialogImportPeopleBinding
    private val viewModel by activityViewModels<SendNFTViewModel>()

    private val requestPermissionCallback = mutableMapOf<String, () -> Unit>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            AppConstants.logAppsFlyerEvent(CONTACTS_PERMISSION_GRANTED_EVENT_NAME,requireContext())
            Log.e("ERORR88888", " GRANTED")
            getContactList()
        }else{
            Log.e("ERORR88888", " DENIED")
        }
    }

    private val peopleAdapter by lazy {
        PeopleAdapter { contact, position ->
            selectContact(contact, position)
        }
            .setMode(MULTI)
    }

    private fun selectContact(contact: Contact, position: Int) {
        peopleAdapter.toggleSelection(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogImportPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        fillAdapter()
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun initAdapter() {
        with(binding.recyclerView) {
            adapter = peopleAdapter
        }
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnBack.setOnClickListener {
            popBack()
        }

        binding.btnImport.setOnClickListener {


            val selectedRecipientId = peopleAdapter.selectedPosition.mapNotNull { peopleAdapter.getItemAtPosition(it) }
            if (selectedRecipientId.isEmpty()) {
                // error select empty people
                return@setOnClickListener
            }
            else{
                /* observeResultFlow(viewModel.getContacts(),
                    successHandler = {
                        peopleAdapter.setData(it)
                    }
                )  */

                viewModel.recipientId = selectedRecipientId
                viewModel.postContacts()

                popBack()
            }


        }
    }

    private fun fillAdapter() {
        peopleAdapter.setData(getContactList())

    }

    @SuppressLint("Range")
    private fun getContactList() : List<Contact>? {
        val contacts = mutableListOf<Contact>()
        val cr: ContentResolver = requireActivity().contentResolver
        val cur: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cur?.count ?: 0) > 0) {
            val contacts_phones = mutableListOf<ContactPhone>()

            while (cur != null && cur.moveToNext()) {
                val id: String = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name: String = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0
                ) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    while (pCur!!.moveToNext()) {
                        val phoneNo: String = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        contacts_phones.add(ContactPhone(number = phoneNo))
                        Log.i(TAG, "Name: $name")
                        Log.i(TAG, "Phone Number: $phoneNo")
                        ContactsContract.CommonDataKinds.Email.ADDRESS
                    }
                    pCur.close()
                }

                try{
                    contacts.add(Contact(firstName = name.split(" ")[0],
                        lastName = name.split(" ")[1],
                        phone = (contacts_phones as List<ContactPhone>?),
                        user_id = id)
                    )
                }catch (noLastName : Exception){
                    contacts.add(Contact(firstName = name,
                        lastName = name,
                        phone = (contacts_phones as List<ContactPhone>?),
                        user_id = id)
                    )
                }
            }
        }
        cur?.close()

        return contacts
    }

}