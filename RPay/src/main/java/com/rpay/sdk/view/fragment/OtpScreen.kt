package com.rpay.sdk.view.fragment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.rpay.sdk.R
import com.rpay.sdk.base.BaseFragment
import com.rpay.sdk.core.RPay
import com.rpay.sdk.core.RPayHandler
import com.rpay.sdk.databinding.RpayOtpScreenBinding
import com.rpay.sdk.listener.OTPReceiverListener
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.utils.Pinview
import com.rpay.sdk.utils.SMSBroadcastReceiver
import com.rpay.sdk.viewmodel.OTPScreenViewModel


internal class OtpScreen : BaseFragment() {

    private lateinit var binding: RpayOtpScreenBinding
    private lateinit var viewModel: OTPScreenViewModel

    private var otp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otp = arguments?.getString("otp")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RpayOtpScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpUi()
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this).get(OTPScreenViewModel::class.java)
        activity?.let { LocalBroadcastManager.getInstance(it).registerReceiver(receiver, IntentFilter("OTP")) }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            binding.pinView.value = p1?.getStringExtra("otp")!!
        }
    }

    private fun setUpUi() {
        binding.apply {
            pinView.setTextColor(Color.BLACK)
            pinView.isPassword = true
            pinView.setTextSize(30)
            pinView.setInputType(Pinview.InputType.NUMBER)
            pinView.setPinViewEventListener(object : Pinview.PinViewEventListener {
                override fun onDataEntered(pin: Pinview?, fromUser: Boolean) {
                    if (pin != null) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (otp.equals(pin.value, true)){
                                if (context?.let { RPay.isNetConnected(it) } == false){
                                    showNoInternetDialog()
                                }else {
                                    val headers: HashMap<String, String> = HashMap()
                                    headers["secret_key"] = RPay.getMerchantKey()
                                    headers["auth_token"] = RPay.getAuthToken()
                                    val params: HashMap<String, String> = HashMap()
                                    params["otp"] = pin.value
                                    pinView.clearValue()
                                    pinView.clearFocus()
                                    val iMm =
                                        context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                    iMm.hideSoftInputFromWindow(view?.windowToken, 0)
                                    view?.clearFocus()
                                    viewModel.getOTPStatus(params, headers)
                                        .observe(viewLifecycleOwner, {
                                            when (it) {
                                                is NetworkResponse.Loading -> {
                                                    /**
                                                     * Show Progress Dialog
                                                     */
                                                    showProgressDialog()
                                                }
                                                is NetworkResponse.Success -> {
                                                    /**
                                                     * Handle Success Event
                                                     */
                                                    hideProgressDialog()
                                                    if (it.data?.success.equals("1")) {
                                                        showToast("OTP verified successfully")
                                                        findNavController().navigate(R.id.action_rPayOtpScreen_to_rPayPaymentScreen)
                                                    }
                                                }
                                                is NetworkResponse.ErrorResponse -> {
                                                    /**
                                                     * Handle Failure Event
                                                     */
                                                    hideProgressDialog()
                                                    it.errorResponse?.message?.let { it1 ->
                                                        showToast(
                                                            it1
                                                        )
                                                    }
                                                }
                                            }
                                        })
                                }
                            }else {
                                showToast("Incorrect OTP")
                                pinView.clearValue()
                                pinView.clearFocus()
                            }
                        },400)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            activity?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(receiver) }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}