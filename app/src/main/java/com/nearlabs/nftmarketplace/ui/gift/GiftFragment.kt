package com.nearlabs.nftmarketplace.ui.gift

import android.Manifest
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.data.localcontact.LocalContact
import com.nearlabs.nftmarketplace.databinding.FragmentGiftNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter.SendNFTAdapter
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.SEND_GIFT_NFT_EVENT_NAME
import com.nearlabs.nftmarketplace.util.adapters.ContactListAdapter
import com.nearlabs.nftmarketplace.viewmodel.ContactViewModel
import com.nearlabs.nftmarketplace.viewmodel.CreateNftViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GiftFragment : BaseFragment(R.layout.fragment_gift_nft) {

    private val binding by viewBinding(FragmentGiftNftBinding::bind)
    private val viewModel by activityViewModels<ContactViewModel>()

    private val contactListAdapter by lazy {ContactListAdapter()  }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                syncContact()
            }
        }

    private fun syncContact() {
        observeResultFlow(
            viewModel.postLocalContact(
            ), successHandler = {
                getContactList()

            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        )

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initViews()
    }
    private fun initViews() {
        binding.contactList.adapter = contactListAdapter

    }
    private fun initListeners() {

        binding.sendGift.setOnClickListener {
            findNavController().navigate(R.id.toCreateNft)
        }

        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.toMain)
        }

        binding.importContact.setOnClickListener {
            syncContact()
        }
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)

    }

    private fun getContactList() {
        observeResultFlow(
            viewModel.getContacts(
            ), successHandler = {
                contactListAdapter.setData(it)
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }
}