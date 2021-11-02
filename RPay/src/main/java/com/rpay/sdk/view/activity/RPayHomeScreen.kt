package com.rpay.sdk.view.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rpay.sdk.R
import com.rpay.sdk.core.RPay

class RPayHomeScreen : AppCompatActivity() {

    private lateinit var closeDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rpay_home_screen)

        initialize()
    }

    private fun initialize() {
        closeDialog = AlertDialog.Builder(this)
    }

    override fun onBackPressed() {
        showCloseDialog()
    }

    private fun showCloseDialog() {
        closeDialog.setCancelable(false)
        closeDialog.setMessage("Are you sure want to cancel this payment")
        closeDialog.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
            finish()
            val listener = RPay.getListener()
            listener.onTransactionCancelled()
        })
        closeDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        closeDialog.show()
    }
}