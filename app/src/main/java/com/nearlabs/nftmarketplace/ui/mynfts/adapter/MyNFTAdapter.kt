package com.nearlabs.nftmarketplace.ui.mynfts.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemCustomNftCellBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter

class MyNFTAdapter(private val onItemClicked: ((NFT, Int) -> Unit)? = null) :
    BaseSelectionAdapter<NFT, ItemSendNFTViewHolder>() {

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemSendNFTViewHolder {
        return ItemSendNFTViewHolder(
            parent.viewBinding(ItemCustomNftCellBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemSendNFTViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))
    }
}

class ItemSendNFTViewHolder(
    private val binding: ItemCustomNftCellBinding,
    private val onItemClicked: ((NFT, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: NFT, selected: Boolean) {
        binding.tvTitle.text = data.name
        binding.tvUID.text = data.id.toString()
        binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }
}