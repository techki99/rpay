package com.rpay.sdk.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.rpay.sdk.R
import com.rpay.sdk.core.RPay
import com.rpay.sdk.core.RPay.isLoggedIn
import com.rpay.sdk.core.RPayLog
import com.rpay.sdk.listener.OTPReceiverListener
import com.rpay.sdk.utils.AppSignatureHelper
import com.rpay.sdk.utils.SMSBroadcastReceiver
import com.rpay.sdk.view.fragment.ProceedBottomSheet

internal class RPayHomeScreen : AppCompatActivity(), OTPReceiverListener {

    private lateinit var closeDialog: AlertDialog.Builder
    private lateinit var smsBroadcastReceiver: SMSBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rpay_home_screen)

        //setUpSignature()
        initialize()
    }

    private fun setUpSignature() {
        val appSignatureHelper = AppSignatureHelper(applicationContext)
        appSignatureHelper.appSignatures
    }

    private fun initialize() {
        closeDialog = AlertDialog.Builder(this)
        smsBroadcastReceiver = SMSBroadcastReceiver()
        smsBroadcastReceiver.initOTPListener(this)
        try {
            val client = SmsRetriever.getClient(this)
            val task = client.startSmsRetriever()
            task.addOnSuccessListener { aVoid ->
                registerReceiver(smsBroadcastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
            }
            task.addOnFailureListener { e ->
                RPayLog.message(e.toString())
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(smsBroadcastReceiver)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        try {
            val configuration = Configuration(newBase.resources?.configuration)
            configuration.fontScale = 1.0f
            configuration.densityDpi = newBase.resources.displayMetrics.xdpi.toInt()
            applyOverrideConfiguration(configuration)
        }catch (e: Exception){
            e.printStackTrace()
        }
        super.attachBaseContext(newBase)
    }

    override fun onOTPReceived(otp: String) {
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val localIntent = Intent("OTP")
            .putExtra("otp", otp)
        localBroadcastManager.sendBroadcast(localIntent)
    }

    override fun onOTPFailed(error: String) {
        RPayLog.message("Failed to detect OTP")
    }
}