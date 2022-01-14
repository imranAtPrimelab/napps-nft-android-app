package com.nearlabs.nftmarketplace.ui.sendNFTDialog.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemLayoutSendNftCellBinding
import com.nearlabs.nftmarketplace.domain.model.nft.NFT
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter

class myNftsAdapter(private val onItemClicked: ((NFT, Int) -> Unit)? = null) :
    BaseSelectionAdapter<NFT, ItemMyNFTViewHolder>() {

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemMyNFTViewHolder {
        return ItemMyNFTViewHolder(
            parent.viewBinding(ItemLayoutSendNftCellBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemMyNFTViewHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))

    }
}

class ItemMyNFTViewHolder(
    private val binding: ItemLayoutSendNftCellBinding,
    private val onItemClicked: ((NFT, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: NFT, selected: Boolean) {
        binding.tvTitleSendNFT.text = data.type.toString()
        binding.tvTitleSendNFT.text = data.name
        binding.tvUIDSendNFT.text = data.id.toString()

        binding.NftContainer.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("NftPosition",this.layoutPosition)
            Navigation.findNavController(itemView).navigate(R.id.detailsFragment,bundle)
        }

        //binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }
}