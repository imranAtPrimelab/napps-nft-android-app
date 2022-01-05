package com.nearlabs.nftmarketplace.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.data.networks.api.Login
import com.nearlabs.nftmarketplace.databinding.FragmentLoginBinding
import com.nearlabs.nftmarketplace.databinding.FragmentOtpBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class OTPFragment: BaseFragment(R.layout.fragment_otp) {

    private val binding by viewBinding(FragmentOtpBinding::bind)
    private lateinit var prefs : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences("NEAR", Context.MODE_PRIVATE)

        initListeners()
    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val json = JsonObject()
                json.addProperty("walletName", prefs.getString("walletName",""))
                json.addProperty("nonce", binding.etOtp.text.toString())
                val apiInterface = Login.create().verifyUser(json)
                apiInterface.enqueue( object : retrofit2.Callback<JsonObject> {

                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if(response.isSuccessful)
                            findNavController().navigate(R.id.toMain)
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    }
                })
            }
        }
    }
}