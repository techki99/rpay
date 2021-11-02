package com.rpay.sdk.listener

interface RPayListener {

    fun onTransactionSuccess(transactionId: String)

    fun onTransactionFailure(error: String)

    fun onTransactionCancelled()

}