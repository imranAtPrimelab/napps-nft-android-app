package com.nearlabs.nftmarketplace.ui.main.transaction.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TransactionPagerAdapter(fragmentManager: FragmentManager,
                              lifecycle: Lifecycle, private val childFragments: List<Fragment>) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = childFragments.size

    override fun createFragment(position: Int) = childFragments[position]

}