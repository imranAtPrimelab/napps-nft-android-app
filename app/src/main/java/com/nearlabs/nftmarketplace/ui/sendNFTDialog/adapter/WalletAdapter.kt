package com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemConsentWalletBinding
import com.nearlabs.nftmarketplace.domain.model.Wallet


class WalletAdapter(context: Context, private val wallets: List<Wallet>) :
    ArrayAdapter<Wallet>(context, R.layout.support_simple_spinner_dropdown_item, wallets) {

    override fun getCount(): Int {
        return wallets.size
    }

    override fun getItem(position: Int): Wallet {
        return wallets[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = parent.viewBinding(ItemConsentWalletBinding::inflate)
        binding.tvWalletName.text = getItem(position).name
        return binding.root
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {
        val binding = parent.viewBinding(ItemConsentWalletBinding::inflate)
        binding.tvWalletName.text = getItem(position).name
        return binding.root
    }
}