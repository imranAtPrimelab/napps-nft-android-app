package com.nearlabs.nftmarketplace.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentHomeBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter.SendNFTAdapter
import com.nearlabs.nftmarketplace.viewmodel.NFTViewModel
import dagger.hilt.android.AndroidEntryPoint

import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.domain.model.transaction.Transaction
import com.nearlabs.nftmarketplace.ui.create.CreateNftFragment
import com.nearlabs.nftmarketplace.ui.create.NftMintedSheetDialog
import com.nearlabs.nftmarketplace.ui.main.transaction.adapter.TransactionAdapter
import com.nearlabs.nftmarketplace.viewmodel.CreateNftViewModel
import com.nearlabs.nftmarketplace.viewmodel.TransactionViewModel
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val nftViewModel: NFTViewModel by viewModels()
    private val transactionViewModel: TransactionViewModel by viewModels()
    private val createNftViewModel: CreateNftViewModel by activityViewModels()

    private val transactionAdapter by lazy {
        TransactionAdapter(
            { doOnTransactionClicked(it) },
            { }
        )
    }

    private val nftAdapter by lazy { SendNFTAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        binding.myNFTsRv.adapter = nftAdapter
        binding.recentTransactionsRv.adapter = transactionAdapter
    }

    private fun initListeners() {
        binding.btnCreateNft.setOnClickListener {
            val newNftFragment = CreateNftFragment()
            newNftFragment.show(childFragmentManager, newNftFragment.tag)
        }

        binding.username.setOnClickListener {
            findNavController().navigate(R.id.nav_setting)
        }

        binding.seeAllNFTs.setOnClickListener {
            findNavController().navigate(R.id.toMyNFTs)
        }
    }

    private fun initObservers() {
        observeResultFlow(
            nftViewModel.getAllNFTCollection(),
            successHandler = {
                nftAdapter.setData(it)
            })

        observeResultFlow(
            transactionViewModel.getRecentTransactions(),
            successHandler = {
                transactionAdapter.setData(it)
            }
        )

        lifecycleScope.launchWhenStarted {
            createNftViewModel.minted.collect { minted ->
                if (minted) {
                    val mintedSheetDialog = NftMintedSheetDialog()
                    mintedSheetDialog.show(childFragmentManager, mintedSheetDialog.tag)
                }
            }
        }
    }

    private fun doOnTransactionClicked(transaction: Transaction) {

    }
}