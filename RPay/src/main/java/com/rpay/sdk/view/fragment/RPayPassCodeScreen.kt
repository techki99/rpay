package com.rpay.sdk.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rpay.sdk.R
import com.rpay.sdk.base.BaseFragment
import com.rpay.sdk.core.RPay
import com.rpay.sdk.databinding.RPayPassCodeScreenBinding
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.viewmodel.PaymentScreenViewModel

internal class RPayPassCodeScreen : BaseFragment() {

    private lateinit var binding: RPayPassCodeScreenBinding
    private lateinit var viewModel: PaymentScreenViewModel

    private var passcode: String = ""
    private var amountToPay: String = ""
    private var amountToPayCurrency: String = ""
    private var merchantImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        amountToPay = arguments?.getString("amount").toString()
        amountToPayCurrency = arguments?.getString("currency").toString()
        merchantImage = arguments?.getString("merchantImage").toString()

        initialize()
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this).get(PaymentScreenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RPayPassCodeScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
    }

    private fun setUpUi() {
        binding.apply {

            amountTextView.text = "$amountToPayCurrency $amountToPay"
            merchantNameTextView.text = RPay.getAppName()

            if (merchantImage.isNotEmpty()) {
                Glide.with(this@RPayPassCodeScreen).load(merchantImage).into(binding.merchantImageView)
            }else {
                //binding.merchantImageView.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_app_logo))
            }

            button0.setOnClickListener {
                passcode += "0"
                checkPassCode()
            }

            button1.setOnClickListener {
                passcode += "1"
                checkPassCode()
            }

            button2.setOnClickListener {
                passcode += "2"
                checkPassCode()
            }

            button3.setOnClickListener {
                passcode += "3"
                checkPassCode()
            }

            button4.setOnClickListener {
                passcode += "4"
                checkPassCode()
            }

            button5.setOnClickListener {
                passcode += "5"
                checkPassCode()
            }

            button6.setOnClickListener {
                passcode += "6"
                checkPassCode()
            }

            button7.setOnClickListener {
                passcode += "7"
                checkPassCode()
            }

            button8.setOnClickListener {
                passcode += "8"
                checkPassCode()
            }

            button9.setOnClickListener {
                passcode += "9"
                checkPassCode()
            }

            deleteButton.setOnClickListener {
                if (passcode.isNotEmpty()) {
                    passcode = passcode.substring(0, passcode.length - 1)
                    checkPassCode()
                }
            }

            closeButton.setOnClickListener {
                passcode = ""
                checkPassCode()
            }
        }
    }

    private fun allCodeEmpty() {
        binding.apply {
            codeImageView1.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.gray_round,
                    null
                )
            )
            codeImageView2.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.gray_round,
                    null
                )
            )
            codeImageView3.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.gray_round,
                    null
                )
            )
            codeImageView4.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.gray_round,
                    null
                )
            )
        }
    }

    private fun checkPassCode() {
        binding.apply {
            when {
                passcode.isEmpty() -> {
                    allCodeEmpty()
                }
                passcode.length == 1 -> {
                    codeImageView1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView2.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gray_round,
                            null
                        )
                    )
                    codeImageView3.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gray_round,
                            null
                        )
                    )
                    codeImageView4.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gray_round,
                            null
                        )
                    )
                }
                passcode.length == 2 -> {
                    codeImageView1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView2.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView3.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gray_round,
                            null
                        )
                    )
                    codeImageView4.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gray_round,
                            null
                        )
                    )
                }
                passcode.length == 3 -> {
                    codeImageView1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView2.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView3.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView4.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gray_round,
                            null
                        )
                    )
                }
                passcode.length == 4 -> {
                    codeImageView1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView2.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView3.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    codeImageView4.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.gradient_round,
                            null
                        )
                    )
                    showProgressDialog()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val headers = mapOf("secret_key" to RPay.getMerchantKey(), "auth_token" to RPay.getAuthToken())
                        val param = mapOf("pass_code" to passcode)
                        viewModel.verifyPasscode(headers, param).observe(viewLifecycleOwner, {
                            when (it) {
                                is NetworkResponse.Success -> {
                                    if (it.data?.success.equals("1", true)) {
                                        makePayment()
                                    }else {
                                        hideProgressDialog()
                                        passcode = ""
                                        checkPassCode()
                                        it.data?.message?.let { it1 -> showToast(it1) }
                                    }
                                }
                                is NetworkResponse.ErrorResponse -> {
                                    hideProgressDialog()
                                    passcode = ""
                                    checkPassCode()
                                    it.errorResponse?.message?.let { it1 -> showToast(it1) }
                                }
                            }
                        })
                    }, 400)
                }
            }
        }
    }

    private fun makePayment() {
        if (activity?.let { RPay.isNetConnected(it) } == false) {
            showNoInternetDialog()
        }else {
            val headers: HashMap<String, String> = HashMap()
            headers["secret_key"] = RPay.getMerchantKey()
            headers["auth_token"] = RPay.getAuthToken()
            val params: HashMap<String, String> = HashMap()
            params["amount"] = RPay.getTotalAmount()
            params["currency"] = RPay.getCurrencyCode()
            params["description"] = ""
            viewModel.capturePayment(headers, params).observe(viewLifecycleOwner, {
                val listener = RPay.getListener()
                when (it) {
                    is NetworkResponse.Success -> {
                        hideProgressDialog()
                        if (it.data?.data?.status.equals("success", true)){
                            it.data?.data?.TransactionId?.let { it1 ->
                                activity?.finishAndRemoveTask()
                                listener.onTransactionSuccess(it1)
                            }
                        }else {
                            activity?.finishAndRemoveTask()
                            listener.onTransactionFailure("Transaction failed")
                        }
                    }
                    is NetworkResponse.ErrorResponse -> {
                        hideProgressDialog()
                        activity?.finishAndRemoveTask()
                        listener.onTransactionFailure("Transaction failed")
                    }
                }
            })
        }
    }
}