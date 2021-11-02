package com.rpay.sdk.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.rpay.sdk.R
import com.rpay.sdk.adapter.CountryCodeAdapter
import com.rpay.sdk.base.BaseFragment
import com.rpay.sdk.core.RPay
import com.rpay.sdk.core.RPayHandler
import com.rpay.sdk.databinding.RpayLoginScreenBinding
import com.rpay.sdk.model.CountryListResponse
import com.rpay.sdk.utils.NetworkResponse
import com.rpay.sdk.utils.Session
import com.rpay.sdk.viewmodel.LoginViewModel


internal class LoginScreen : BaseFragment() {

    private lateinit var binding: RpayLoginScreenBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var session: Session

    private var checkNumber: Boolean = false
    private var firstRun: Int = 0
    private var previousCode: String = ""

    private val countryList: ArrayList<CountryListResponse.CountryList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RpayLoginScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpUi()
        setUpCountryList()
    }

    private fun setUpCountryList() {
        viewModel.countryList().observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResponse.Loading -> {
                    showProgressDialog()
                }
                is NetworkResponse.Success -> {
                    hideProgressDialog()
                    it.data?.data?.countryList?.let { it1 -> countryList.addAll(it1) }
                    val adapter = context?.let { it1 -> CountryCodeAdapter(it1, countryList) }
                    binding.numberCodeSpinner.adapter = adapter
                    binding.ccpPicker.setAutoDetectedCountry(false)

                    binding.numberCodeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            val customerPhoneCode: String =
                                countryList[position].country_code.trim()
                            if (customerPhoneCode.isNotEmpty()) {
                                if (firstRun != 0) {
                                    if (!previousCode.equals(customerPhoneCode, ignoreCase = true)) {
                                        binding.numberEditText.setText("")
                                        previousCode = customerPhoneCode
                                    }
                                } else {
                                    firstRun = 1
                                }
                                binding.ccpPicker.setDefaultCountryUsingPhoneCode(customerPhoneCode.replace("+", "").toInt())
                                binding.ccpPicker.resetToDefaultCountry()
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }

                }
                is NetworkResponse.ErrorResponse -> {
                    hideProgressDialog()
                }
            }
        })
    }

    private fun initialize() {
        session = activity?.let { Session.getInstance(it) }!!
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun setUpUi() {
        binding.apply {

            ccpPicker.registerCarrierNumberEditText(numberEditText)

            ccpPicker.setPhoneNumberValidityChangeListener { isValidNumber ->
                checkNumber = isValidNumber
            }

            loginButton.setOnClickListener {
                val phoneNumber = numberEditText.text.toString()
                val password = passwordLayout.editText?.text.toString()
                val passcode = passcodeLayout.editText?.text.toString()
                when {
                    phoneNumber.isEmpty() -> {
                        showToast("Please enter a mobile number")
                    }
                    password.isEmpty() -> {
                        showToast("Please enter a password")
                    }
                    passcode.isEmpty() -> {
                        showToast("Please enter a passcode")
                    }
                    context?.let { RPay.isNetConnected(it) } == false -> {
                        showNoInternetDialog()
                    }
                    else -> {
                        val params: HashMap<String, String> = HashMap()
                        params["phone_number"] = ccpPicker.fullNumber
                        params["password"] = password
                        params["pass_code"] = passcode
                        viewModel.login(params, RPay.getMerchantKey()).observe(viewLifecycleOwner, {
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
                                    if (it.data?.success.equals("1", true)){
                                        it.data?.data?.auth_token?.let { it1 ->
                                            RPay.storeAuthToken(it1)
                                        }
                                        val extraInfoForSharedElement = FragmentNavigatorExtras(
                                            binding.contentLayout to "content"
                                        )
                                        val otp = it.data?.data?.otp.toString()
                                        if (otp.isNotEmpty()){
                                            val bundle = Bundle()
                                            bundle.putString("otp", otp)
                                            findNavController().navigate(R.id.action_rPayLoginScreen_to_rPayOtpScreen, bundle, null, extraInfoForSharedElement)
                                        }
                                    }else {
                                        it.data?.message?.let { it1 -> showToast(it1) }
                                    }
                                }
                                is NetworkResponse.ErrorResponse -> {
                                    /**
                                     * Handle Failure Event
                                     */
                                    hideProgressDialog()
                                    it.errorResponse?.message?.let { it1 -> showToast(it1) }
                                }
                            }
                        })
                    }
                }
            }
        }
    }

}