package com.nearlabs.nftmarketplace.ui.auth

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentLoginBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.toOtp)
        }

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }

        binding.tvPhoneLogin.setOnClickListener {
            Toast.makeText(requireContext(),"Phone Click", Toast.LENGTH_SHORT).show()
        }
    }
}