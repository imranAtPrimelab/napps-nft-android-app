package com.nearlabs.nftmarketplace.ui.main.transaction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.ListItemTransactionBinding
import com.nearlabs.nftmarketplace.domain.model.transaction.Transaction
import com.nearlabs.nftmarketplace.domain.model.transaction.TransactionDirection

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transaction) {
            binding.tvOrderNumber.text = item.identifier()
            binding.tvDesc.text = when (item.direction) {
                is TransactionDirection.Incoming -> "Receive from ${item.sender.address}"
                is TransactionDirection.Outgoing -> "Sent to ${item.receiver.name}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewBinding(ListItemTransactionBinding::inflate))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size

}