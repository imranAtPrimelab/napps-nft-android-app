package com.nearlabs.nftmarketplace.ui.setting

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentSettingBinding
import com.nearlabs.nftmarketplace.domain.model.User
import com.nearlabs.nftmarketplace.ui.MainActivity
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment(R.layout.fragment_setting) {
    val binding by viewBinding(FragmentSettingBinding::bind)
    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserve()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)?.visibility = View.GONE
        viewModel.settingFragment = this
    }

    override fun onPause() {
        super.onPause()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)?.visibility = View.VISIBLE
    }

    private fun initListeners() {
        binding.cswvWallet.setOnClickListener {
            findNavController().navigate(R.id.toChangeWallet)
        }

        binding.csivName.setOnClickListener {
            findNavController().navigate(R.id.toChangeName)
        }

        binding.csivEmail.setOnClickListener {
            findNavController().navigate(R.id.toChangeEmail)
        }

        binding.csivPhone.setOnClickListener {
            findNavController().navigate(R.id.toChangePhone)
        }

        binding.btnBack.setOnClickListener {
            popBack()
        }

        binding.logoutButtonView.setOnClickListener {
            viewModel.clearPref()
            val intent = Intent(this.activity, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun initObserve() {
        observeResultFlow(
            viewModel.getUserProfile(), successHandler = {
                binding.cswvWallet.setWalletName(it.walletId)
                binding.csivName.setValue(it.name)
                binding.csivEmail.setValue(it.email)
                binding.csivPhone.setValue(it.phone)
            }, errorHandler = {
                Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
    }
}