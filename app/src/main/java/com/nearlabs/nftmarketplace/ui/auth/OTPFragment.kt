package com.nearlabs.nftmarketplace.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentLoginBinding
import com.nearlabs.nftmarketplace.databinding.FragmentOtpBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.OTP_VERIFICATION_EVENT_NAME

class OTPFragment: BaseFragment(R.layout.fragment_otp) {

    private val binding by viewBinding(FragmentOtpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener {
            AppConstants.logAppsFlyerEvent(OTP_VERIFICATION_EVENT_NAME,requireContext())
            findNavController().navigate(R.id.toMain)
        }
    }
}