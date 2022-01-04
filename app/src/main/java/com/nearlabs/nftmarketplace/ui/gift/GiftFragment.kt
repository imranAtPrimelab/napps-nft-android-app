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




@AndroidEntryPoint
class GiftFragment : BaseFragment(R.layout.fragment_gift_nft)  {
    lateinit var binding : FragmentGiftNftBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGiftNftBinding.inflate(layoutInflater)
        binding.contactList.adapter //= ContactListAdapter(,context)
    }



}