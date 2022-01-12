package com.nearlabs.nftmarketplace.util.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
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
        return ItemContactHolder(
            parent.viewBinding(ItemContactsBinding::inflate),
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
        val contactImageUri = data.imageUri
        var nameShort = ""

        if (data.first_name?.length ?: 0 > 0) {
            nameShort = (data.first_name?.substring(0, 1) ?: "").uppercase()
        }

        if (data.last_name?.length ?: 0 > 0) {
            nameShort = nameShort + (data.last_name?.substring(0, 1) ?: "").uppercase()
        }

        binding.nameLong.text = "${data.first_name} ${data.last_name}"
        binding.username.text = data.phone?.firstOrNull()?.number ?: data.email?.firstOrNull()?.address ?: ""
        if(contactImageUri != null) {
            binding.profileImage.setImageURI(contactImageUri)
            binding.nameShort.visibility = View.INVISIBLE
        } else {
            binding.profileImage.setImageResource(R.drawable.bg_contact)
            binding.nameShort.visibility = View.VISIBLE
            binding.nameShort.text = nameShort
        }
    }
}
