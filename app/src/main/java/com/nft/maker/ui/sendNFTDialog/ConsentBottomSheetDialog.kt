package com.nft.maker.ui.sendNFTDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.databinding.DialogSendConsentNtfBinding
import com.nft.maker.model.Wallet
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.ui.sendNFTDialog.adapter.WalletAdapter

class ConsentBottomSheetDialog : BaseBottomSheetDialogFragment() {
    private lateinit var binding: DialogSendConsentNtfBinding
    private val viewModel by activityViewModels<SendNFTViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogSendConsentNtfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserve()
    }


    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnDeny.setOnClickListener { popBack() }

        binding.btnAllow.setOnClickListener {
            val selectedWallet = binding.spinner.selectedItem as? Wallet ?: return@setOnClickListener
            viewModel.wallet = selectedWallet
            sendTransaction()
        }
    }

    private fun initObserve() {
        observeResultFlow(viewModel.getWallets(),
            successHandler = {
                binding.spinner.adapter = WalletAdapter(requireContext(), it)
            })
    }

    private fun sendTransaction() {
        observeResultFlow(viewModel.sendTransaction(),
            successHandler = {
                dismiss()
                findNavController().navigate(R.id.toResultSendNft)
            })
    }
}