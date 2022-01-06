package com.nearlabs.nftmarketplace.ui.main.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentHistoryBinding
import com.nearlabs.nftmarketplace.databinding.FragmentTransactionBinding
import com.nearlabs.nftmarketplace.domain.model.transaction.Transaction
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.main.transaction.adapter.TransactionAdapter
import com.nearlabs.nftmarketplace.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {
    val binding by viewBinding(FragmentTransactionBinding::bind)

    companion object {
        private const val KEY_TRANSACTION_DIRECTION = "key_transaction_direction"
        const val NONE = -1
        const val ALL = 0
        const val RECV = 1
        const val SENT = 2

        fun newInstance(type: Int): TransactionFragment {
            return TransactionFragment().apply {
                arguments = bundleOf(KEY_TRANSACTION_DIRECTION to type)
            }
        }
    }

    private val transactionViewModel by viewModels<TransactionViewModel>()
    private val type by lazy {
        arguments?.getInt(KEY_TRANSACTION_DIRECTION, NONE)
    }

    private val transactionAdapter by lazy {
        TransactionAdapter(
            { doOnItemClicked(it) },
            { }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.rvTransaction.adapter = transactionAdapter
    }

    private fun initObservers() {
        observeResultFlow(
            when (type) {
                ALL -> transactionViewModel.getTransactions()
                SENT -> transactionViewModel.getSentTransactions()
                RECV -> transactionViewModel.getRecvTransactions()
                else -> throw IllegalArgumentException("Unknown transaction type $type")
            },
            successHandler = {
                transactionAdapter.setData(it)
            }
        )
    }

    private fun doOnItemClicked(transaction: Transaction) {

    }
}