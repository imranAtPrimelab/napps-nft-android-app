package com.nearlabs.nftmarketplace.ui.setting.changewallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.showKeyboard
import com.nearlabs.nftmarketplace.databinding.DialogChangeNameBinding
import com.nearlabs.nftmarketplace.ui.base.BaseBottomSheetDialogFragment
import com.nearlabs.nftmarketplace.ui.setting.SettingsViewModel


class ChangeNameBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: DialogChangeNameBinding

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        requestKeyboard()
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            popBack()
        }

        binding.btnAddNewWallet.setOnClickListener {
            val name = binding.editName.text.toString()
            observeResultFlow(viewModel.addWallet(name), successHandler = {
                popBack()
            })
        }
    }

    private fun requestKeyboard() {
        binding.editName.post { showKeyboard(binding.editName) }
    }
}