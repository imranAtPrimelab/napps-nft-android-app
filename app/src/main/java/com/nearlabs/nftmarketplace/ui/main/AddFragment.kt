package com.nearlabs.nftmarketplace.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogClaimNftConnectWalletBinding
import com.nearlabs.nftmarketplace.databinding.FragmentAddBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.base.BaseFragment

class AddFragment : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }


    private fun initListeners() {
        binding.btnCreateNft.setOnClickListener {
            // TODO AppsFlyer 13
            Toast.makeText(it.context, "13", Toast.LENGTH_SHORT).show()
        }

        binding.btnSendNft.setOnClickListener {
            // TODO AppsFlyer 14
            Toast.makeText(it.context, "14", Toast.LENGTH_SHORT).show()

        }
    }
}