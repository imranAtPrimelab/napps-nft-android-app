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
import android.graphics.Color
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.activity.BaseActivity
import com.nearlabs.nftmarketplace.ui.base.adapter.MULTI


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
        binding.searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
            .setBackgroundColor(Color.TRANSPARENT)
    }

    private fun initListeners() {

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

        binding.cvSearch.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                binding.searchView.onActionViewExpanded();
            }
        })
    }


    private fun getContactList() {
        (this.activity as BaseActivity).showProgressDialog()
        observeResultFlow(
            viewModel.getLocalContacts(
            ), successHandler = {
                contactListAdapter.setData(it)
                (this.activity as BaseActivity).dismissProgressDialog()
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
                (this.activity as BaseActivity).dismissProgressDialog()
            }
        )
    }


}