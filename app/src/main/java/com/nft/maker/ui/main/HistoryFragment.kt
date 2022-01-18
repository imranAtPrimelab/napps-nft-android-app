package com.nft.maker.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nft.maker.R
import com.nft.maker.extensions.viewBinding
import com.nft.maker.data.preference.SharePrefs
import com.nft.maker.databinding.FragmentHistoryBinding
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.ui.main.transaction.TransactionFragment
import com.nft.maker.ui.main.transaction.adapter.TransactionPagerAdapter
import com.nft.maker.ui.sendNFTDialog.SendNFTBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment(R.layout.fragment_history) {

    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private var tabLayoutMediator: TabLayoutMediator? = null
    private var viewPager: ViewPager2? = null

    @Inject
    lateinit var sharePrefs: SharePrefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {

        val types = listOf(
            TransactionFragment.ALL,
            TransactionFragment.SENT,
            TransactionFragment.RECV
        )

        val fragments = types.map { TransactionFragment.newInstance(it) }
        val titles = types.map {
            when (it) {
                TransactionFragment.ALL -> getString(R.string.transaction_all)
                TransactionFragment.SENT -> getString(R.string.transaction_sent)
                TransactionFragment.RECV -> getString(R.string.transaction_recv)
                else -> ""
            }
        }

        viewPager = binding.viewPager.apply {
            adapter = TransactionPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, fragments)
        }
        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.apply { attach() }

        binding.username.text = sharePrefs.userName

    }

    private fun initListeners() {
        binding.btnSendNft.setOnClickListener {
            val sendNft = SendNFTBottomSheetDialog()
            sendNft.show(childFragmentManager, sendNft.tag)
        }

        binding.username.setOnClickListener {
            findNavController().navigate(R.id.nav_setting)
        }
    }

    override fun onDestroyView() {
        tabLayoutMediator?.detach()
        tabLayoutMediator = null

        viewPager?.adapter = null
        super.onDestroyView()
    }
}