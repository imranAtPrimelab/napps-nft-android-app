package com.nft.maker.ui.mynfts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.viewBinding
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.databinding.FragmentMyNftsBinding
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.ui.mynfts.adapter.MyNFTAdapter
import com.nft.maker.viewmodel.NFTViewModel
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