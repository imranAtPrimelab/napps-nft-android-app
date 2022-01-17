package com.nearlabs.nftmarketplace.ui.mynfts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.data.preference.SharePrefs
import com.nearlabs.nftmarketplace.databinding.FragmentMyNftsBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.mynfts.adapter.MyNFTAdapter
import com.nearlabs.nftmarketplace.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyNFTsFragment : BaseFragment(R.layout.fragment_my_nfts) {
    val binding by viewBinding(FragmentMyNftsBinding::bind)
    private val nftViewModel: NFTViewModel by viewModels()
    private val nftAdapter by lazy { MyNFTAdapter() }
    @Inject
    lateinit var sharePrefs: SharePrefs
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
                nftAdapter.context = this.context
                nftAdapter.setData(it)
            })
    }

    private fun initListeners() {
        binding.createnft.setOnClickListener {
            findNavController().navigate(R.id.toCreateNft)
        }
        binding.username.setOnClickListener {
            findNavController().navigate(R.id.nav_setting)
        }
    }

    private fun initViews() {
        binding.allNFTsrv.adapter = nftAdapter
        binding.username.text = sharePrefs.userName

    }
}