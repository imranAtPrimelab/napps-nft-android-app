package com.nearlabs.nftmarketplace.ui.setting.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ListItemWalletBinding
import com.nearlabs.nftmarketplace.domain.model.Wallet
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseAdapter

class SelectWalletAdapter(private val onItemClicked: ((Wallet) -> Unit)? = null, ) : BaseAdapter<Wallet, ItemWalletViewHolder>() {

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemWalletViewHolder {
        return ItemWalletViewHolder(
            parent.viewBinding(ListItemWalletBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemWalletViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item)
    }
}

class ItemWalletViewHolder(
    private val binding: ListItemWalletBinding,
    private val onItemClicked: ((Wallet) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Wallet) {
        binding.textWalletName.text = data.name
        binding.imageSelected.visibility = if (data.selected) View.VISIBLE else View.GONE
        binding.root.setOnClickListener { onItemClicked?.invoke(data) }
    }
}