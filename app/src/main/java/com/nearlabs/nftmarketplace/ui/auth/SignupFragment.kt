package com.nearlabs.nftmarketplace.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.observeResultFlow
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentOtpBinding
import com.nearlabs.nftmarketplace.databinding.FragmentSignupBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.viewmodel.TransactionViewModel
import com.nearlabs.nftmarketplace.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {

        binding.fullName.doAfterTextChanged {
            checkContinue()
        }
        binding.walletId.doAfterTextChanged {
            checkContinue()
        }


        binding.btnCreateAccount.setOnClickListener {
            observeResultFlow(
                userViewModel.createUser(
                    binding.fullName.text.toString(),
                    binding.walletId.text.toString()
                ), successHandler = {
                    findNavController().navigate(R.id.toMain)
                }, errorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                })
        }

    }

    private fun checkContinue() {
        binding.btnCreateAccount.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                if (binding.fullName.text.isNullOrBlank() || binding.walletId.text.isNullOrBlank()) R.color.btndisabled_color else R.color.blue
            )
        )
    }
}