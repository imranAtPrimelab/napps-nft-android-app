package com.nearlabs.nftmarketplace.ui.base.activity

import android.app.AlertDialog
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.nearlabs.nftmarketplace.util.AlertDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId){

    private var progressDialog: AlertDialog? = null

    fun showProgressDialog(){
        if(progressDialog == null){
            progressDialog = AlertDialogUtil.getAndShowProgressDialog(this, false)
        } else {
            progressDialog?.show()
        }
    }

    fun dismissProgressDialog(){
        progressDialog?.dismiss()
        progressDialog = null
    }
}