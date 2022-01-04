package com.nearlabs.nftmarketplace.ui.setting.changewallet

import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.databinding.DialogChangeWalletBinding
import com.nearlabs.nftmarketplace.domain.model.Wallet
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.setting.SettingsViewModel
import com.nearlabs.nftmarketplace.ui.setting.adapter.SelectWalletAdapter


class ChangeWalletBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: DialogChangeWalletBinding

    private val viewModel by activityViewModels<SettingsViewModel>()

    private val walletAdapter by lazy {
        SelectWalletAdapter() {
            selectWallet(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogChangeWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        initObserve()
    }

    private fun initAdapter() {
        with(binding.recyclerView) {
            addItemDecoration(createDivider())
            adapter = walletAdapter
        }
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnAddNewWallet.setOnClickListener {

        }
    }

    private fun initObserve() {
        observeResultFlow(viewModel.getWallets(),
            successHandler = {
                walletAdapter.setData(it)
            })
    }

    private fun selectWallet(wallet: Wallet) {
        observeResultFlow(viewModel.selectWallet(wallet),
            successHandler = {
                popBack()
            })
    }

    private fun createDivider(): RecyclerView.ItemDecoration {
        val itemDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider) ?: ShapeDrawable()
        )
        return itemDecorator
    }
}