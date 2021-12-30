package com.rpay.sdk.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rpay.sdk.R
import com.rpay.sdk.base.BaseFragment
import com.rpay.sdk.core.RPay
import com.rpay.sdk.core.RPayHandler
import com.rpay.sdk.databinding.RpayPaymentScreenBinding
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.viewmodel.PaymentScreenViewModel
import java.util.*
import kotlin.collections.HashMap


internal class PaymentScreen : BaseFragment() {

    private lateinit var binding: RpayPaymentScreenBinding
    private lateinit var viewModel: PaymentScreenViewModel

    private var walletStatus: String = "success"
    private var amountToPay: String = ""
    private var amountToPayCurrency: String = ""
    private var merchantImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RpayPaymentScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpUi()
        setUpObserver()
    }

    private fun setUpObserver() {
        val headers: HashMap<String, String> = HashMap()
        headers["secret_key"] = RPay.getMerchantKey()
        headers["auth_token"] = RPay.getAuthToken()
        val params: HashMap<String, String> = HashMap()
        params["amount"] = RPay.getTotalAmount()
        params["currency"] = RPay.getCurrencyCode()
        viewModel.getPaymentDetails(headers, params).observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResponse.Loading -> {
                    showProgressDialog()
                }
                is NetworkResponse.Success -> {
                    hideProgressDialog()
                    walletStatus = if (it.data?.message.equals("You dont have enough balance for this transaction.", true)) {
                        binding.errorTextView.visibility = View.VISIBLE
                        "failure"
                    }else {
                        binding.errorTextView.visibility = View.GONE
                        "success"
                    }
                    binding.amountTextView.text = RPay.getCurrencyCode() + " " + String.format(Locale.ENGLISH, "%.2f", RPay.getTotalAmount().toDouble())
                    binding.walletBalanceTextView.text = it.data?.data?.api_user_currency + " " + String.format(Locale.ENGLISH, "%.2f", it.data?.data?.api_user_balance?.toDouble())
                    binding.orderAmountTextView.text = it.data?.data?.deductable_currency + " " + it.data?.data?.deduct_amount
                    if (it.data?.data?.site_fee?.toDouble() != 0.0) {
                        binding.rPayFeeTextView.visibility = View.VISIBLE
                        binding.rPayFeeTitleTextView.visibility = View.VISIBLE
                        binding.rPayFeeTextView.text = "+ " + it.data?.data?.deductable_currency + " " + it.data?.data?.site_fee
                    }else {
                        binding.rPayFeeTextView.visibility = View.GONE
                        binding.rPayFeeTitleTextView.visibility = View.GONE
                    }
                    if (it.data?.data?.merchant_profile_image?.isNotEmpty() == true) {
                        merchantImage = it.data.data.merchant_profile_image
                        Glide.with(this).load(it.data.data.merchant_profile_image).into(binding.merchantImageView)
                    }else {
                        //binding.merchantImageView.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_app_logo))
                    }
                    amountToPay = it.data?.data?.deductable_amount.toString()
                    amountToPayCurrency = it.data?.data?.deductable_currency.toString()
                    binding.totalAmountTextView.text = it.data?.data?.deductable_currency + " " + it.data?.data?.deductable_amount
                }
                is NetworkResponse.ErrorResponse -> {
                    hideProgressDialog()
                }
            }
        })
    }

    private fun initialize() {
        viewModel = ViewModelProvider(this).get(PaymentScreenViewModel::class.java)
    }

    private fun setUpUi() {
        binding.apply {

            merchantNameTextView.text = RPay.getAppName()

            walletLayout.setOnClickListener {
                if (walletStatus.equals("success", true)) {
                    walletLayout.alpha = 1f
                    walletPayButton.visibility = View.VISIBLE
                    cardLayout.alpha = 0.6f
                    errorTextView.visibility = View.GONE
                }
            }

            RPay.setLoggedIn(true)

            cardLayout.setOnClickListener {
                walletLayout.alpha = 0.6f
                walletPayButton.visibility = View.GONE
                cardLayout.alpha = 1f
                val proceedToPay = ProceedBottomSheet()
                proceedToPay.show(childFragmentManager, "Proceed")
            }

            walletPayButton.setOnClickListener {
                if (context?.let { RPay.isNetConnected(it) } == false){
                    showNoInternetDialog()
                }else {
                    val bundle = Bundle()
                    bundle.putString("amount", amountToPay)
                    bundle.putString("currency", amountToPayCurrency)
                    bundle.putString("merchantImage", merchantImage)
                    findNavController().navigate(R.id.action_rPayPaymentScreen_to_RPayPassCodeScreen, bundle)
                }
            }

            logoutTextView.setOnClickListener {
                RPay.clearData()
                findNavController().popBackStack(R.id.rPayLoginScreen, false)
            }
        }
    }
}

