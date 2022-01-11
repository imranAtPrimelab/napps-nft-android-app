package com.nearlabs.nftmarketplace.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
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
import com.nearlabs.nftmarketplace.common.extensions.popBack
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentOtpBinding
import com.nearlabs.nftmarketplace.databinding.FragmentSignupBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.AppConstants
import com.nearlabs.nftmarketplace.util.AppConstants.SIGN_UP_CREATE_ACCOUNT_EVENT_NAME
import com.nearlabs.nftmarketplace.viewmodel.TransactionViewModel
import com.nearlabs.nftmarketplace.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!userViewModel.currentEmail.isEmpty()){
            binding.walletId.hint = userViewModel.currentEmail
        }else{
            binding.walletId.hint = userViewModel.currentPhone
        }

        initListeners()
    }

    @SuppressLint("ResourceAsColor")
    private fun initListeners() {

        binding.fullName.doAfterTextChanged {
            checkContinue()
        }

        binding.walletId.doAfterTextChanged {
            checkContinue()
        }

        binding.fullName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                binding.fullNameText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            else
                binding.fullNameText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }

        binding.walletId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                binding.accountIdText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            else
                binding.accountIdText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }

        binding.btnCreateAccount.setOnClickListener {
            AppConstants.logAppsFlyerEvent(SIGN_UP_CREATE_ACCOUNT_EVENT_NAME,it.context)
            val accountId = binding.walletId.text.toString().apply {
                this.replace(" ","")
                this.toLowerCase()
                
            }
            observeResultFlow(
                userViewModel.createUser(
                    binding.fullName.text.toString(),
                    accountId
                ), successHandler = {
                    findNavController().navigate(R.id.toContactNFT)
                }, errorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                }, httpErrorHandler = {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            )
        }

        binding.closeSignup.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.toAuth)
        })

        binding.termsText.makeLinks(Pair("Terms of Service",View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://terms.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }), Pair("Privacy Policy",View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://privacy.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }))

        binding.loginbtn.setOnClickListener {
            popBack()
        }

    }

    private fun checkContinue() {
        binding.btnCreateAccount.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                if (binding.fullName.text.isNullOrBlank() || binding.walletId.text.isNullOrBlank()) R.color.btndisabled_color else R.color.black
            )
        )
    }

}