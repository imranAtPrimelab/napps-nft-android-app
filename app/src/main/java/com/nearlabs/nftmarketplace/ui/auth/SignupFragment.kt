package com.nearlabs.nftmarketplace.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.data.networks.api.Users
import com.nearlabs.nftmarketplace.databinding.FragmentSignupBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

@AndroidEntryPoint
class SignupFragment : BaseFragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private lateinit var prefs : SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences("NEAR", Context.MODE_PRIVATE)

        initListeners()

    }

    private fun initListeners() {

        binding.btnCreateAccount.setOnClickListener(View.OnClickListener {
            lifecycleScope.launch {
                val json = JsonObject()
                json.addProperty("fullName", binding.fullName.text.toString())
                json.addProperty("walletName", binding.accountId.text.toString())
                json.addProperty("email", prefs.getString("email",""))
                json.addProperty("phone", prefs.getString("phone",""))

                val apiInterface = Users.create().createUser(json)
                apiInterface.enqueue( object : retrofit2.Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        //Timber.e(response.errorBody().toString())
                        Log.e("body",response.body()!!.toString())

                        if (response.isSuccessful)
                            findNavController().navigate(R.id.toMain)
                        Timber.e(response.body()!!.toString())
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Timber.e("failure")
                    }
                })
            }
            binding.fullName.text
            binding.accountId.text
        })

        binding.loginbtn.setOnClickListener(View.OnClickListener {

        })

    }


}