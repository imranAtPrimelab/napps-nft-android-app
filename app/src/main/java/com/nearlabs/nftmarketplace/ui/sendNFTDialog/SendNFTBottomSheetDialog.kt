package com.nearlabs.nftmarketplace.ui.sendNFTDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogSendNftBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.base.adapter.MULTI
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter.SendNFTAdapter
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.SEND_NFT_DIALOG_NEXT_EVENT_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendNFTBottomSheetDialog : BaseBottomSheetDialogFragment() {
    private lateinit var binding: DialogSendNftBinding
    private val viewModel by activityViewModels<SendNFTViewModel>()

    private val nftAdapter by lazy {
        SendNFTAdapter { ntf, position ->
            selectNTF(ntf, position)
        }
            .setMode(MULTI)
    }

    private fun selectNTF(ntf: NFT, position: Int) {
        nftAdapter.toggleSelection(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogSendNftBinding.inflate(inflater, container, false)
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
            adapter = nftAdapter
        }
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnNext.setOnClickListener {
            AppConstants.logAppsFlyerEvent(SEND_NFT_DIALOG_NEXT_EVENT_NAME, requireContext())
            val items = nftAdapter.selectedPosition.map { nftAdapter.getItemAtPosition(it) }
            findNavController().navigate(R.id.toSelectPeople)
        }
    }

    private fun initObserve() {
        observeResultFlow(viewModel.getNFT(),
            successHandler = {
                nftAdapter.setData(it)
            })
    }

}