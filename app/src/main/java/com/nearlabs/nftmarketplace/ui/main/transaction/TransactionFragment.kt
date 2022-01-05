package com.nearlabs.nftmarketplace.ui.main.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayoutMediator
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentHistoryBinding
import com.nearlabs.nftmarketplace.databinding.FragmentTransactionBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment

class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {
    val binding by viewBinding(FragmentTransactionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}