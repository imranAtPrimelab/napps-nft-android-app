package com.nearlabs.nftmarketplace.ui.sendNFTDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogSendNftResultBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment


class SentNFTResultBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: DialogSendNftResultBinding

    private val viewModel by activityViewModels<SendNFTViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogSendNftResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnOpenHistory.setOnClickListener { }
    }

}