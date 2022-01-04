package com.nearlabs.nftmarketplace.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.common.extensions.viewBinding
import com.nearlabs.nftmarketplace.databinding.FragmentHistoryBinding
import com.nearlabs.nftmarketplace.ui.base.BaseFragment

class HistoryFragment : BaseFragment(R.layout.fragment_history) {
    val binding by viewBinding(FragmentHistoryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =childFragmentManager.findFragmentById(R.id.historyFragmentContainer) as NavHostFragment

        val navController = navHostFragment.navController

        binding.btnall.setOnClickListener{
            binding.btnall.setBackgroundResource(R.drawable.btn_bg_curve)
            binding.btnsent.setBackgroundResource(R.drawable.bg_white_round_corner)
            binding.btnrecieved.setBackgroundResource(R.drawable.bg_white_round_corner)

            navController.navigate(R.id.toAllHistory)
        }
        binding.btnrecieved.setOnClickListener{
            binding.btnrecieved.setBackgroundResource(R.drawable.btn_bg_curve)
            binding.btnsent.setBackgroundResource(R.drawable.bg_white_round_corner)
            binding.btnall.setBackgroundResource(R.drawable.bg_white_round_corner)
            navController.navigate(R.id.toRecievedHistory)
        }
        binding.btnsent.setOnClickListener{
            binding.btnsent.setBackgroundResource(R.drawable.btn_bg_curve)
            binding.btnall.setBackgroundResource(R.drawable.bg_white_round_corner)
            binding.btnrecieved.setBackgroundResource(R.drawable.bg_white_round_corner)
            navController.navigate(R.id.toSentHistory)
        }

    }
}