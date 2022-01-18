package com.nft.maker.ui.main.transaction.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nft.maker.R
import com.nft.maker.ui.base.adapter.BaseLoadMoreInsideNestedScrollAdapter
import com.nft.maker.extensions.viewBinding
import com.nft.maker.databinding.ListItemTransactionBinding
import com.nft.maker.model.transaction.Transaction
import com.nft.maker.model.transaction.TransactionDirection

class TransactionAdapter(
    private val onItemClicked: ((Transaction) -> Unit)? = null,
    onLoadMoreListener: () -> Unit
) : BaseLoadMoreInsideNestedScrollAdapter<Transaction, TransactionViewHolder>(
    onLoadMoreListener = onLoadMoreListener
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TransactionViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getProgressLayoutId(): Int = R.layout.list_item_progress

    override fun createViewHolderInternal(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(parent.viewBinding(ListItemTransactionBinding::inflate), onItemClicked)
    }
}

class TransactionViewHolder(
    private val binding: ListItemTransactionBinding,
    private val onItemClicked: ((Transaction) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Transaction?) {
        binding.root.setOnClickListener { item?.let { onItemClicked?.invoke(it) } }
        binding.tvOrderNumber.text = item?.identifier()
        binding.tvTimestamp.text = item?.getPrettyTime()
        binding.tvDesc.text = when (item?.direction) {
            is TransactionDirection.Incoming -> "Receive from ${item.counterParty?.name}"
            is TransactionDirection.Outgoing -> "Sent to ${item.counterParty?.name}"
            else -> ""
        }
    }
}