package com.nearlabs.nftmarketplace.ui.base.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class BaseLoadMoreAdapter<DATA, VH : RecyclerView.ViewHolder>(
    val items: MutableList<DATA?> = mutableListOf(),
    private val onLoadMoreListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val DEFAULT_PAGE_LIMIT = 20
        const val DEFAULT_THRESHOLDS = 3

        const val TYPE_PROGRESS = 1
    }

    var pageLimit = DEFAULT_PAGE_LIMIT

    open var isLoading = false
    open var thresholds = DEFAULT_THRESHOLDS
    open var isAllowLoadMore = true

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        initLoadMore(recyclerView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && items.lastOrNull() == null) TYPE_PROGRESS else super.getItemViewType(
            position
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PROGRESS -> LoadMoreViewHolder(
                LayoutInflater.from(parent.context).inflate(getProgressLayoutId(), parent, false)
            )
            else -> createViewHolderInternal(parent, viewType)
        }
    }

    override fun getItemCount(): Int = items.size

    @LayoutRes
    abstract fun getProgressLayoutId(): Int

    abstract fun createViewHolderInternal(parent: ViewGroup, viewType: Int): VH

    fun setPageLimit(pageLimit: Int): BaseLoadMoreAdapter<DATA, VH> {
        this.pageLimit = pageLimit
        return this
    }

    fun setThresholds(thresholds: Int): BaseLoadMoreAdapter<DATA, VH> {
        this.thresholds = thresholds
        return this
    }

    fun getItemAtPosition(position: Int): DATA? = items.getOrNull(position)

    fun removeItemAtPosition(position: Int): DATA? = items.removeAt(position)

    fun setData(newItems: List<DATA>?) {
        this.items.clear()
        this.items.addAll(newItems ?: emptyList())
        isLoading = false
        isAllowLoadMore = true
        notifyDataSetChanged()
    }

    fun addLoadMoreData(moreData: List<DATA>?) {
        isAllowLoadMore = moreData?.size == pageLimit
        isLoading = false
        removeProgressItem()

        moreData ?: return

        val currentCount = itemCount
        this.items.addAll(moreData)
        notifyItemRangeInserted(currentCount, itemCount)
    }

    open fun initLoadMore(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLoading || !isAllowLoadMore) return
                if (findLastVisibleItem(recyclerView) == itemCount - thresholds) {
                    invokeLoadMore()
                }
            }
        })
    }

    private fun findLastVisibleItem(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return 0

        return when (layoutManager) {
            is LinearLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
            is GridLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
            else -> 0
        }
    }

    open fun addProgressItem() {
        if (items.lastOrNull() != null) {
            items.add(null)
            notifyItemInserted(itemCount - 1)
        }
    }

    open fun removeProgressItem() {
        if (items.lastOrNull() == null) {
            items.removeAt(itemCount - 1)
            notifyItemRemoved(itemCount)
        }
    }

    open fun invokeLoadMore() {
        addProgressItem()
        isLoading = true
        onLoadMoreListener.invoke()
        Log.d("LoadMore", "trigger")
    }
}

class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)