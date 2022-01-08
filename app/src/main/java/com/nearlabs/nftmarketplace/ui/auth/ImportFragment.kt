package com.nearlabs.nftmarketplace.ui.auth

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.data.localcontact.LocalContact
import com.nearlabs.nftmarketplace.databinding.FragmentImportBinding
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.base.adapter.MULTI
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter.PeopleAdapter
import com.nearlabs.nftmarketplace.util.AppConstants
import kotlinx.coroutines.launch

class ImportFragment : BaseFragment(R.layout.fragment_import) {

    private val binding by viewBinding(FragmentImportBinding::bind)
    private val viewModel by activityViewModels<ImportContactsViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            AppConstants.logAppsFlyerEvent(AppConstants.CONTACTS_PERMISSION_GRANTED_EVENT_NAME,requireContext())
            fillAdapter()
        }else{
            Toast.makeText(requireContext(),"Please accept all permissions to continue", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        initListeners()
    }


    private fun fillAdapter() {
        lifecycleScope.launch {
            peopleAdapter.setData(LocalContact(requireContext()).getAllContact())
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

    private fun initAdapter() {
        with(binding.recyclerView) {
            adapter = peopleAdapter
        }
    }

    private fun initListeners() {

        binding.importBtn.setOnClickListener {
            val selectedRecipientId =
                peopleAdapter.selectedPosition.mapNotNull { peopleAdapter.getItemAtPosition(it) }
            if (selectedRecipientId.isEmpty()) {
                // error select empty people
                return@setOnClickListener
            } else {
                // people have been selected
                viewModel.recipientId = selectedRecipientId
                viewModel.postContacts()
                findNavController().navigate(R.id.toMain)
            }

        }

    }

}