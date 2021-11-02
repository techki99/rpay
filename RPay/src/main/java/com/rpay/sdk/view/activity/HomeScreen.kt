package com.rpay.sdk.view.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.rpay.sdk.R
import com.rpay.sdk.core.RPay
import com.rpay.sdk.core.RPayHandler
import com.rpay.sdk.databinding.HomeScreenBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: HomeScreenBinding
    private lateinit var closeDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        setUpUi()
    }

    private fun setUpUi() {
        binding.apply {

        }
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