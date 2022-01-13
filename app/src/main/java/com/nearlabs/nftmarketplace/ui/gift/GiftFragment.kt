package com.nearlabs.nftmarketplace.ui.gift

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentGiftNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.adapters.ContactListAdapter
import com.nearlabs.nftmarketplace.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.activity.BaseActivity
import com.nearlabs.nftmarketplace.ui.base.adapter.MULTI
import java.lang.Exception


@AndroidEntryPoint
class GiftFragment : BaseFragment(R.layout.fragment_gift_nft) {

    private val binding by viewBinding(FragmentGiftNftBinding::bind)
    private val viewModel by activityViewModels<ContactViewModel>()

    private val contactListAdapter by lazy {
        return@lazy ContactListAdapter(activity as Context) { contact, position ->
            selectContact(contact, position)
        }.setMode(MULTI)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                getContactList()
            }
        }


    private fun selectContact(contact: Contact, position: Int) {
        contactListAdapter.toggleSelection(position)
        viewModel.selectedHashSet!!.add(position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isFocusableInTouchMode = true
        view.requestFocus()

        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        val i = Intent()
                        i.action = Intent.ACTION_MAIN
                        i.addCategory(Intent.CATEGORY_HOME)
                        view.context.startActivity(i)
                        return true
                    }
                }
                return false
            }
        })
        initListeners()
        initViews()
    }
    private fun initViews() {
        binding.contactList.adapter = contactListAdapter
        binding.contactList.callOnClick()
    }


    private fun initListeners() {

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val currentData = (binding.contactList.adapter as ContactListAdapter).getData()!!
                for(i in viewModel.selectedHashSet!!.indices){
                    if(currentData.contains(viewModel.itemsCopy!![viewModel.selectedHashSet!![i]])){
                        for (y in currentData.indices){
                            selectContact(currentData[currentData.indexOf(viewModel.itemsCopy!![viewModel.selectedHashSet!![i]])],
                                currentData.indexOf(viewModel.itemsCopy!![viewModel.selectedHashSet!![i]]))
                        }
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                (binding.contactList.adapter as ContactListAdapter).filter(s.toString(),
                    viewModel.itemsCopy!!
                )

            }
        })

        /*binding.searchView.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                (binding.contactList.adapter as ContactListAdapter).filter(binding.searchView.text.toString())
            }
            false
        }*/

        binding.sendGift.setOnClickListener {
            observeResultFlow(
                viewModel.getContacts()
                , successHandler = {
                   if(it.isNotEmpty()){
                       findNavController().navigate(R.id.toCreateNft)
                   }else{
                       Toast.makeText(requireContext(), "please import contacts first", Toast.LENGTH_SHORT).show()
                   }
                }, errorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            )
        }

        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.toMain)
        }

        binding.importContact.setOnClickListener {
            val selectedContacts = contactListAdapter.selectedPosition.mapNotNull { contactListAdapter.getItemAtPosition(it) }

            if (selectedContacts.isEmpty()) {
                // error select empty people
                Toast.makeText(requireContext(),"no contacts selected!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                (this.activity as BaseActivity).showProgressDialog()
                observeResultFlow(
                    viewModel.postLocalContact(
                        selectedContacts
                    ), successHandler = {
                        Toast.makeText(requireContext(), "contacts imported successfully", Toast.LENGTH_SHORT).show()
                        (this.activity as BaseActivity).dismissProgressDialog()

                    }, errorHandler = {
                        Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                        (this.activity as BaseActivity).dismissProgressDialog()
                    }
                )
            }

        }
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)

    }


    private fun getContactList() {
        (this.activity as BaseActivity).showProgressDialog()
        observeResultFlow(
            viewModel.getLocalContacts(
            ), successHandler = {
                contactListAdapter.setData(it)
                viewModel.itemsCopy = it
                (this.activity as BaseActivity).dismissProgressDialog()
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
                (this.activity as BaseActivity).dismissProgressDialog()
            }
        )
    }




}