package com.nft.maker.ui.create

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nft.maker.R
import com.nft.maker.databinding.FragmentNftMintedBinding
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NftMintedSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: FragmentNftMintedBinding
    private val nftViewModel by activityViewModels<NFTViewModel>()
    companion object {
        const val NFT_NAME = "nft_name"
        const val NFT_PATH = "nft_path"
    }
    private var nftName = ""
    private var nftPath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nftName = arguments?.getString(NFT_NAME) ?: ""
        nftPath = arguments?.getString(NFT_PATH) ?: ""
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNftMintedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.fragmentMintedText.text = getString(R.string.nft_mint_success, nftName)
        Glide.with(requireContext())
            .load(nftPath)
            .into(binding.nftImage)
    }

    private fun initListeners() {
        binding.btnOpen.setOnClickListener {
            nftViewModel.shouldRefresh.value = true
            findNavController().navigate(R.id.toMyNFTs)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        nftViewModel.shouldRefresh.value = true
        super.onDismiss(dialog)
    }
}