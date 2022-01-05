package com.nearlabs.nftmarketplace.ui.create.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.databinding.DialogCreateNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment


class CreateNFTDialog: BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: DialogCreateNftBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogCreateNftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.tvCreateNFTDialog.setOnClickListener {

        }

        binding.tvSendNFTDialog.setOnClickListener {

        }
    }

}