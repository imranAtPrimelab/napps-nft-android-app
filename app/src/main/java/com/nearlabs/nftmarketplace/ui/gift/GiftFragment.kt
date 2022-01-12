package com.nearlabs.nftmarketplace.ui.gift

import android.Manifest
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.KeyEvent
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
import android.content.Intent
import com.nearlabs.nftmarketplace.ui.base.activity.BaseActivity


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
        (this.activity as BaseActivity).showProgressDialog()
        observeResultFlow(
            viewModel.postLocalContact(
            ), successHandler = {
                getContactList()
                (this.activity as BaseActivity).dismissProgressDialog()
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                (this.activity as BaseActivity).dismissProgressDialog()
            }
        )
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