package com.nearlabs.nftmarketplace.ui.main.historySubFragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentRecievedHistoryBinding
import com.nearlabs.nftmarketplace.databinding.FragmentSentHistoryBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment
import com.nearlabs.nftmarketplace.ui.main.historySubFragments.adapter.HistoryTabsAdapter

class SentHistoryFragment : BaseFragment(R.layout.fragment_sent_history) {

    private val binding by viewBinding(FragmentSentHistoryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvsenthistory.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )

        val mData = ArrayList<String>()
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")
        mData.add("#123456")


        binding.rvsenthistory.adapter = HistoryTabsAdapter(mData)
        binding?.rvsenthistory?.adapter?.notifyDataSetChanged()

    }
}