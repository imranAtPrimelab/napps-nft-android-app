package com.nearlabs.nftmarketplace.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.databinding.ItemContactsBinding
import com.nearlabs.nftmarketplace.util.models.Contact


class ContactListAdapter(private val items: List<Contact>, private val context : Context) :
    RecyclerView.Adapter<ContactListAdapter.MyViewHolder>() {
    inner class MyViewHolder(b: ItemContactsBinding) :
        RecyclerView.ViewHolder(b.root) {
        var binding : ItemContactsBinding = b

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemContactsBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.nameShort.text = items[position].nameShort
        holder.binding.nameLong.text = items[position].nameLong
        holder.binding.username.text = items[position].username
        //holder.binding.checkStatus.setImageDrawable(text)

    }

    override fun getItemCount(): Int {
        return items.size
    }
}