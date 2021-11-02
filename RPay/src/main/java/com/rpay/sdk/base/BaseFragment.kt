package com.rpay.sdk.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import com.rpay.sdk.R

open class BaseFragment : Fragment() {

    private lateinit var progressDialog:Dialog

    fun showProgressDialog(){
        try {
            progressDialog = context?.let { Dialog(it) }!!
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.rpay_progressbar)
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.window?.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            progressDialog.setCancelable(false)
            if (!progressDialog.isShowing) progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressDialog(){
        try {
            progressDialog.dismiss()
        } catch (e: java.lang.Exception) {
        }
    }

    fun showToast(message: String){
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showNoInternetDialog() {
        val internetDialog = AlertDialog.Builder(context)
        internetDialog.setCancelable(false)
        internetDialog.setMessage("Please check your internet connection")
        internetDialog.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
        internetDialog.show()
    }
}