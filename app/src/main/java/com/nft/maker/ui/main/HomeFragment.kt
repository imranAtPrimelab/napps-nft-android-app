package com.nft.maker.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.viewBinding
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.databinding.FragmentHomeBinding
import com.nft.maker.model.transaction.Transaction
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.ui.detailnft.ClaimNFTFragment.Companion.CLIM_NFT_ID
import com.nft.maker.ui.main.transaction.adapter.TransactionAdapter
import com.nft.maker.ui.sendNFTDialog.adapter.myNftsAdapter
import com.nft.maker.viewmodel.NFTViewModel
import com.nft.maker.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val nftViewModel by activityViewModels<NFTViewModel>()
    private val transactionViewModel: TransactionViewModel by viewModels()
    private var climNftId: String? = null

    @Inject
    lateinit var sharePrefs: SharePrefs

    private val transactionAdapter by lazy {
        TransactionAdapter(
            { doOnTransactionClicked(it) },
            { }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        climNftId = arguments?.getString(CLIM_NFT_ID)

    }

    private val myNftsAdapter by lazy { myNftsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
        checkClimNft()

    }

    private fun checkClimNft() {
        if (!climNftId.isNullOrEmpty()) {
            Handler().postDelayed({
                climNftId = null
                findNavController().navigate(R.id.toClaimNFTFragment, arguments)
            }, 1000)

        }
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
        } else if (!isVisibleToUser) {
            Log.d("TAG", "Fragment not Visible ")
        }
    }
    private fun initViews() {
        binding.myNFTsRv.adapter = myNftsAdapter
        binding.recentTransactionsRv.adapter = transactionAdapter
        binding.username.text = sharePrefs.userName
    }


    private fun initListeners() {
        binding.btnCreateNft.setOnClickListener {
            findNavController().navigate(R.id.toCreateNft)
        }

        binding.username.setOnClickListener {
            findNavController().navigate(R.id.nav_setting)
        }

        binding.seeAllNFTs.setOnClickListener {
            findNavController().navigate(R.id.toMyNFTs)
        }

        binding.seeAllTransactions.setOnClickListener {
            findNavController().navigate(R.id.toHistoryFragment)
        }
    }

    private fun initObservers() {
        observeResultFlow(
            nftViewModel.getAllNFTCollection(),
            successHandler = {
                myNftsAdapter.setData(it)
            })

        observeResultFlow(
            transactionViewModel.getRecentTransactions(),
            successHandler = {
                transactionAdapter.setData(it)
            }
        )
        nftViewModel.shouldRefresh.observe(viewLifecycleOwner, Observer {
            Log.e("E/CALLED"," done")
            if(it){
                observeResultFlow(
                    nftViewModel.getAllNFTCollection(),
                    successHandler = {
                        myNftsAdapter.setData(it)
                    })
                myNftsAdapter.notifyDataSetChanged()
                nftViewModel.shouldRefresh.value = false
            }
        })




    }

    private fun doOnTransactionClicked(transaction: Transaction) {}

    override fun onResume() {
        observeResultFlow(
            nftViewModel.getAllNFTCollection(),
            successHandler = {
                myNftsAdapter.token = sharePrefs.accessToken
                myNftsAdapter.context = context
                myNftsAdapter.setData(it)
            })
        myNftsAdapter.notifyDataSetChanged()
        super.onResume()
    }

}