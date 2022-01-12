package com.nearlabs.nftmarketplace.util.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemContactsBinding
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter

class ContactListAdapter(
    private val onItemClicked: ((Contact, Int) -> Unit)? = null
) :
    BaseSelectionAdapter<Contact, ItemContactHolder>() {
    inner class MyViewHolder(b: ItemContactsBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemContactsBinding = b
    }

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemContactHolder {
        return ItemContactHolder(parent.viewBinding(ItemContactsBinding::inflate),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemContactHolder, position: Int) {
        val item = getItemAtPosition(position) ?: return
        holder.bind(item, isSelected(position))
    }


}

class ItemContactHolder(
    private val binding: ItemContactsBinding,
    private val onItemClicked: ((Contact, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Contact, selected: Boolean) {
        binding.nameLong.text = data.firstName
        binding.username.text = data.phone?.firstOrNull()?.number ?: data.email?.firstOrNull()?.address ?: ""
    }
}
