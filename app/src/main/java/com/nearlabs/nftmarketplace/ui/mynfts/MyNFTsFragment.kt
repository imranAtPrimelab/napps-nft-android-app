package com.nearlabs.nftmarketplace.ui.mynfts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentMyNftsBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.mynfts.adapter.MyNFTAdapter
import com.nearlabs.nftmarketplace.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyNFTsFragment : BaseFragment(R.layout.fragment_my_nfts) {
    val binding by viewBinding(FragmentMyNftsBinding::bind)
    private val nftViewModel: NFTViewModel by viewModels()
    private val nftAdapter by lazy { MyNFTAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        observeResultFlow(
            nftViewModel.getAllNFTCollection(),
            successHandler = {
                nftAdapter.setData(it)
            })
    }

    private fun initListeners() {
        binding.createnft.setOnClickListener {
            findNavController().navigate(R.id.toCreateNft)
        }
    }

    private fun initViews() {
        binding.allNFTsrv.adapter = nftAdapter
    }
}