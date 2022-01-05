package com.nearlabs.nftmarketplace.ui.gift

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.databinding.FragmentGiftNftBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.util.adapters.ContactListAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat

import android.os.Build

import androidx.core.app.ActivityCompat.requestPermissions

import android.content.DialogInterface
import android.view.View
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentLoginBinding


@AndroidEntryPoint
class GiftFragment : BaseFragment(R.layout.fragment_gift_nft)  {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TO-DO
    }



}