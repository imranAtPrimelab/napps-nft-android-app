package com.nearlabs.nftmarketplace.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.nearlabs.nftmarketplace.R
import com.nearlabs.nftmarketplace.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy { findNavController(R.id.nav_host) }
    private val authViewModel by viewModels<AuthViewModel>()

    val PERMISSION_REQUEST_CONTACT = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this@MainActivity,
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
            1);
        askForContactPermission()
        initNavGraph()
    }

    private fun initNavGraph() {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_launch)
        if (authViewModel.isLoggedIn()) {
            navGraph.startDestination = R.id.nav_main
            navController.graph = navGraph
        } else {
            navGraph.startDestination = R.id.nav_auth
            navController.graph = navGraph
        }
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Contacts access needed")
        builder.setPositiveButton(android.R.string.ok, null)
        builder.setMessage("please confirm Contacts access")
        builder.setOnDismissListener(DialogInterface.OnDismissListener { // Only call the permission request api on Android M
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSION_REQUEST_CONTACT
                )
            }
        })
        builder.show()
    }

    fun askForContactPermission() {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    showDialog()
                }
            }

    }

}