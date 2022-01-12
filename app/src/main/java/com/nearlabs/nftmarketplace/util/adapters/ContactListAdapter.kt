package com.nearlabs.nftmarketplace.util.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ItemContactsBinding
import com.nearlabs.nftmarketplace.domain.model.Contact
import com.nearlabs.nftmarketplace.ui.base.adapter.BaseSelectionAdapter
import android.graphics.BitmapFactory

import android.provider.ContactsContract

import android.content.ContentUris
import android.content.Context

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.Nullable
import java.io.InputStream
import java.lang.Exception


class ContactListAdapter(
    private val context: Context,
    private val onItemClicked: ((Contact, Int) -> Unit)? = null
) :
    BaseSelectionAdapter<Contact, ItemContactHolder>() {
    inner class MyViewHolder(b: ItemContactsBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemContactsBinding = b
    }

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): ItemContactHolder {
        return ItemContactHolder(
            this.context,
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
    private val context: Context,
    private val binding: ItemContactsBinding,
    private val onItemClicked: ((Contact, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Contact, selected: Boolean) {
        val contactImageUri = data.imageUri
        val contactImgBmp  = getContactPhotoThumbnail(context = this.context, data.id ?: "")
        var nameShort = ""

        if (data.first_name?.length ?: 0 > 0) {
            nameShort = (data.first_name?.substring(0, 1) ?: "").uppercase()
        }

        if (data.last_name?.length ?: 0 > 0) {
            nameShort = nameShort + (data.last_name?.substring(0, 1) ?: "").uppercase()
        }

        binding.nameLong.text = "${data.first_name} ${data.last_name}"
        binding.username.text = data.phone?.firstOrNull()?.number ?: data.email?.firstOrNull()?.address ?: ""

//        if(contactImageUri != null) {
        if(contactImgBmp != null) {
//            binding.profileImage.setImageURI(contactImageUri)
            binding.profileImage.setImageBitmap(contactImgBmp)
            binding.nameShort.visibility = View.INVISIBLE
        } else {
            binding.profileImage.setImageResource(R.drawable.bg_contact)
            binding.nameShort.visibility = View.VISIBLE
            binding.nameShort.text = nameShort
        }

        binding.imageSelected.setImageResource(if (selected) R.drawable.ic_selected else R.drawable.ic_un_select)
        binding.root.setOnClickListener { onItemClicked?.invoke(data, adapterPosition) }
    }

    @Nullable
    fun getContactPhotoThumbnail(context: Context, contactIdString: String): Bitmap? {
        var contactId = -1L
        try{
            contactId = contactIdString.toLong()
        }catch (ex: Exception){ }
        if (contactId ==-1L) {
            return null
        } else {
            try{
                val contactUri: Uri =
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
                val `is`: InputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                    context.getContentResolver(),
                    contactUri
                )
                return BitmapFactory.decodeStream(`is`)
            } catch (ex: Exception){
                return null
            }
        }
    }
}
