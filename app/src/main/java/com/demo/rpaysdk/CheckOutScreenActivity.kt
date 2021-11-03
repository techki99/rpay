package com.demo.rpaysdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.demo.rpaysdk.databinding.ActivityCheckoutScreenBinding
import com.rpay.sdk.core.RPay
import com.rpay.sdk.databinding.RpayPaymentScreenBinding
import com.rpay.sdk.listener.RPayListener

class CheckOutScreenActivity : AppCompatActivity(), RPayListener {

    private lateinit var binding: ActivityCheckoutScreenBinding

    private val merchantKey: String = "$2y$10\$GTExec3NXk7nXaRu5jxJWuXr77jLw6BdgQJY.AHZEImULr4kEh-SO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        setUpUi()
    }

    private fun setUpUi() {
        binding.apply {
            payButton.setOnClickListener {
                val amount = amountEditText.text.toString()
                val currency = currencyEditText.text.toString()
                when {
                    amount.isEmpty() -> {
                        Toast.makeText(this@CheckOutScreenActivity, "Enter amount", Toast.LENGTH_SHORT).show()
                    }
                    currency.isEmpty() -> {
                        Toast.makeText(this@CheckOutScreenActivity, "Enter currency", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        RPay.init(this@CheckOutScreenActivity, merchantKey)
                        RPay.settings(logVisible = true, appName = "ComeNEat")
                        RPay.setPaymentListener(this@CheckOutScreenActivity)
                        RPay.makePayment(amount = amount.toDouble(), currencyCode = currency)
                    }
                }
            }
        }
    }

    private fun initialize() {

    }

    override fun onTransactionSuccess(transactionId: String) {
        Toast.makeText(this, "Payment Success $transactionId", Toast.LENGTH_LONG).show()
    }

    override fun onTransactionFailure(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionCancelled() {
        Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show()
    }
}