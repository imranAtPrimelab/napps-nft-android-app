package com.nearlabs.nftmarketplace.ui.sendNFTDialog

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogSendSelectPeopleNtfBinding
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.base.adapter.MULTI
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter.PeopleAdapter

class SelectPeopleBottomSheetDialog : BaseBottomSheetDialogFragment() {
    private lateinit var binding: DialogSendSelectPeopleNtfBinding
    private val viewModel by activityViewModels<SendNFTViewModel>()

    private val requestPermissionCallback = mutableMapOf<String, () -> Unit>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // TODO AppsFlyer 7
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
        binding = DialogSendSelectPeopleNtfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        initObserve()
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

        binding.btnNext.setOnClickListener {
            val items = peopleAdapter.selectedPosition.map { peopleAdapter.getItemAtPosition(it) }
            findNavController().navigate(R.id.toConsent)
        }
    }

    private fun initObserve() {
        observeResultFlow(viewModel.getPeople(),
            successHandler = {
                peopleAdapter.setData(it)
            })
    }

}