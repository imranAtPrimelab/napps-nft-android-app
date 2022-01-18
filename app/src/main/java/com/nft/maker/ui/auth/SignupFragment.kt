package com.nft.maker.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nft.maker.R
import com.nft.maker.extensions.observeResultFlow
import com.nft.maker.extensions.popBack
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.FragmentSignupBinding
import com.nft.maker.ui.base.BaseFragment
import com.nft.maker.ui.base.activity.BaseActivity
import com.nft.maker.ui.detailnft.ClaimNFTFragment.Companion.CLIM_NFT_ID
import com.nft.maker.util.AppConstants
import com.nft.maker.util.AppConstants.SIGN_UP_CREATE_ACCOUNT_EVENT_NAME
import com.nft.maker.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class SignupFragment : BaseFragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val userViewModel: UserViewModel by activityViewModels()
    private var claimNFTId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userViewModel.currentEmail.isNotEmpty()) {
            binding.walletId.setText(userViewModel.currentEmail.split("@")[0].replace(".", ""), TextView.BufferType.EDITABLE)
        } else {
            binding.walletId.setText(userViewModel.currentPhone.replace("+", ""), TextView.BufferType.EDITABLE)
        }
        claimNFTId = arguments?.getString(CLIM_NFT_ID)
        Timber.i("From Claim NFT %s", claimNFTId)
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
            AppConstants.logAppsFlyerEvent(SIGN_UP_CREATE_ACCOUNT_EVENT_NAME, it.context)
            val pattern = Pattern.compile("^[a-z0-9._-]+$")

            var enteredWalletId = binding.walletId.text?.trim().toString().replace(AppConstants.ACCOUNT_NAME_NEAR_SUFFIX, "")

            if (!enteredWalletId.contains(".near")) {
                enteredWalletId += ".near"
            }

//            if(pattern.matcher(binding.walletId.text.toString()).matches()) {
            if (pattern.matcher(enteredWalletId).matches() && !binding.fullName.text?.trim()?.isEmpty()!!) {
                (this.activity as BaseActivity).showProgressDialog()
                observeResultFlow(
                    userViewModel.createUser(
                        name = binding.fullName.text.toString(),
                        walletId = enteredWalletId,
                        claimNFTID = claimNFTId
                    ), successHandler = {
                        if (claimNFTId != null) {
                            findNavController().navigate(R.id.toMain, arguments)
                        } else {
                            findNavController().navigate(R.id.toContactNFT)
                        }
                        (this.activity as BaseActivity).dismissProgressDialog()
                    }, errorHandler = {
                        Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                        (this.activity as BaseActivity).dismissProgressDialog()
                    }, httpErrorHandler = {
                        Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_SHORT).show()
                        (this.activity as BaseActivity).dismissProgressDialog()
                    }
                )
            }
            else if (binding.fullName.text?.trim()?.isEmpty() == true)
            {
                Toast.makeText(
                    requireContext(), getString(R.string.full_name_error), Toast.LENGTH_SHORT
                ).show()
            }
            else
                Toast.makeText(
                    requireContext(), getString(R.string.wallet_name_error), Toast.LENGTH_SHORT
                ).show()

        }

        binding.closeSignup.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.toAuth)
        })

        binding.termsText.makeLinks(Pair("Terms of Service", View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://terms.nftmakerapp.io/")
            this.requireActivity().startActivity(browserIntent)
        }), Pair("Privacy Policy", View.OnClickListener {
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