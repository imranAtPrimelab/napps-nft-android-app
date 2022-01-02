package com.nearlabs.nftmarketplace.ui.main.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nearlabs.nftmarketplace.R
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


    }
}