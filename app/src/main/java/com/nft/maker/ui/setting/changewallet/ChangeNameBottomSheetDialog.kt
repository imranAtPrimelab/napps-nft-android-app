package com.nft.maker.ui.setting.changewallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.extensions.showKeyboard
import com.nft.maker.databinding.DialogChangeNameBinding
import com.nft.maker.ui.base.BaseBottomSheetDialogFragment
import com.nft.maker.ui.setting.SettingsViewModel


class ChangeNameBottomSheetDialog : BaseBottomSheetDialogFragment() {
    override fun getTheme() = R.style.BottomSheetTransparentDialog

    private lateinit var binding: DialogChangeNameBinding
    var currentEmail = ""
    var currentPhone = ""
    var currentName = ""
    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCancelable(false)
        initObserve()
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
            if (name.equals(currentName))
            {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.name_error_same),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                viewModel.settingFragment?.binding?.csivName?.setValue(name)
                observeResultFlow(
                    viewModel.changeName(name, currentPhone, currentEmail),
                    successHandler = {
                        popBack()
                    })
            }
        }
    }

    private fun requestKeyboard() {
        binding.editName.post { showKeyboard(binding.editName) }
    }

    private fun initObserve() {
        observeResultFlow(
            viewModel.getUserProfile(), successHandler = {
                binding.editName.setText(it.name, TextView.BufferType.EDITABLE)
                currentPhone = it.phone
                currentEmail = it.email
                currentName = it.name
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
    }
}