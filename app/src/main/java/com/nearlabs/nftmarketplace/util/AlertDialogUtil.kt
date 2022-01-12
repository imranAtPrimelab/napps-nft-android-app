package com.nearlabs.nftmarketplace.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import com.nearlabs.nftmarketplace.R

object AlertDialogUtil {

    fun getAndShowProgressDialog(context: Context?, cancelable: Boolean?): AlertDialog {

        val dialogBuilder = AlertDialog.Builder(context)

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dialogView = inflater.inflate(R.layout.custom_progress_dialog_layout, null)
        dialogBuilder.setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.setCanceledOnTouchOutside(cancelable!!)

        val width = (context.resources.displayMetrics.widthPixels * 0.17f).toInt()
        dialog.window!!.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}