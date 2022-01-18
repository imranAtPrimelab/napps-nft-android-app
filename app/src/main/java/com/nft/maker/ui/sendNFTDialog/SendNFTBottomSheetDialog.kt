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
import com.nft.maker.databinding.DialogSendNftBinding
import com.nft.maker.model.nft.NFT
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.ui.sendNFTDialog.adapter.SendNFTAdapter
import com.nft.maker.util.AppConstants
import com.nft.maker.util.AppConstants.SEND_NFT_DIALOG_NEXT_EVENT_NAME
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SendNFTBottomSheetDialog : BaseBottomSheetDialogFragment() {
    private lateinit var binding: DialogSendNftBinding
    private val viewModel by activityViewModels<SendNFTViewModel>()

    private val nftAdapter by lazy {
        SendNFTAdapter { ntf, position ->
            selectNTF(ntf, position)
        }
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
            AppConstants.logAppsFlyerEvent(SEND_NFT_DIALOG_NEXT_EVENT_NAME, it.context)

            val selectedNFt = nftAdapter.selectedPosition.mapNotNull { position ->
                nftAdapter.getItemAtPosition(
                    position
                )
            }.firstOrNull() ?: return@setOnClickListener

            viewModel.transactionItem = selectedNFt

            observeResultFlow(
                viewModel.getContacts(),
                successHandler = { it ->
                    if(it.isEmpty()){
                        Toast.makeText(requireContext(), "no contacts imported, please import contacts!", Toast.LENGTH_SHORT).show()
                    }else{
                        findNavController().navigate(R.id.toSelectPeople)
                    }
                }, errorHandler = {
                    Timber.e(it)
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            )

        }
    }

    private fun initObserve() {
        observeResultFlow(viewModel.getNFT(),
            successHandler = {
                nftAdapter.setData(it)
                nftAdapter.context = this.context
            })
    }

}