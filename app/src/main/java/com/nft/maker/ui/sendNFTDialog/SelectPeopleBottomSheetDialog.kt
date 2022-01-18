package com.nft.maker.ui.sendNFTDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.databinding.DialogSendSelectPeopleNtfBinding
import com.nft.maker.model.Contact
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.ui.base.adapter.MULTI
import com.nft.maker.ui.sendNFTDialog.adapter.PeopleAdapter
import timber.log.Timber

class SelectPeopleBottomSheetDialog : BaseBottomSheetDialogFragment() {
    private lateinit var binding: DialogSendSelectPeopleNtfBinding
    private val viewModel by activityViewModels<SendNFTViewModel>()


    private val peopleAdapter by lazy {
        PeopleAdapter { contact, position ->
            selectContact(contact, position)
        }.setMode(MULTI)
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
            val selectedRecipientId = peopleAdapter.selectedPosition.mapNotNull { peopleAdapter.getItemAtPosition(it) }

            if (selectedRecipientId.isEmpty()) {
                // error select empty people
                return@setOnClickListener
            }

            viewModel.recipientId = selectedRecipientId
            dismiss()
            findNavController().navigate(R.id.toConsent)
        }
    }

    private fun initObserve() {
        observeResultFlow(
            viewModel.getContacts(),
            successHandler = {
                peopleAdapter.setData(it)
            }, errorHandler = {
                Timber.e(it)
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        )
    }

}