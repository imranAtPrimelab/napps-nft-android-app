package com.nearlabs.nftmarketplace.ui.auth

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.data.networks.api.Login
import com.nearlabs.nftmarketplace.databinding.FragmentLoginBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private lateinit var prefs : SharedPreferences
    private lateinit var selected : Drawable
    private lateinit var notselected : Drawable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences("NEAR", MODE_PRIVATE)
        selected = ResourcesCompat.getDrawable(requireActivity().resources,
            R.drawable.box_selected_bg, null)!!
        notselected = ResourcesCompat.getDrawable(requireActivity().resources,
            R.drawable.bg_white_round_corner, null)!!
        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {

            lifecycleScope.launch {
                val json = JsonObject()
                json.addProperty("walletName", binding.etEmailPhoneLogin.text.toString())
                val apiInterface = Login.create().login(json)
                apiInterface.enqueue( object : retrofit2.Callback<JsonObject> {

                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if(response.isSuccessful)
                            prefs.edit().putString("walletName",
                                binding.etEmailPhoneLogin.text.toString()).apply()
                            findNavController().navigate(R.id.toOtp)
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    }
                })
            }
        }
        binding.tvPhoneLogin.setOnClickListener(View.OnClickListener {
            binding.etEmailPhone.hint = "Ex. (373) 378 8383"
            binding.tvPhoneLogin.background = selected
            binding.tvEmailLogin.background = notselected
        })
        binding.tvEmailLogin.setOnClickListener(View.OnClickListener {
            binding.etEmailPhone.hint = "Ex. username@yourdomain.com"
            binding.tvEmailLogin.background = selected
            binding.tvPhoneLogin.background = notselected
        })

        binding.btnGetStarted.setOnClickListener(View.OnClickListener {
            if(binding.tvEmailLogin.background == selected) {
                prefs.edit().putString(
                    "email",
                    binding.tvEmailLogin.text.toString()
                ).apply()
            }else{
                prefs.edit().putString("phone",
                    binding.tvPhoneLogin.text.toString()).apply()
            }
            findNavController().navigate(R.id.logintoSignup)
        })
    }
}