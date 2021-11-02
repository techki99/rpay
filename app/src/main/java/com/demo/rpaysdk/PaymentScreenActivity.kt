package com.demo.rpaysdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.demo.rpaysdk.databinding.ActivityPaymentScreenBinding
import com.rpay.sdk.core.RPay
import com.rpay.sdk.listener.RPayListener

class PaymentScreenActivity : AppCompatActivity(), RPayListener {

    private lateinit var binding: ActivityPaymentScreenBinding

    private val merchantKey: String = "$2y$10\$GTExec3NXk7nXaRu5jxJWuXr77jLw6BdgQJY.AHZEImULr4kEh-SO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentScreenBinding.inflate(layoutInflater)
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
                        Toast.makeText(this@PaymentScreenActivity, "Enter amount", Toast.LENGTH_SHORT).show()
                    }
                    currency.isEmpty() -> {
                        Toast.makeText(this@PaymentScreenActivity, "Enter currency", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        RPay.init(this@PaymentScreenActivity, merchantKey)
                        RPay.settings(logVisible = true, appName = "ComeNEat")
                        RPay.setPaymentListener(this@PaymentScreenActivity)
                        RPay.makePayment(amount = amount, currencyCode = currency)
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