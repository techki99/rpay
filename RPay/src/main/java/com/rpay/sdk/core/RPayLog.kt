package com.rpay.sdk.core

import android.util.Log

internal class RPayLog {

    companion object {

        private var isLogVisible = false

        fun setLogVisible(status: Boolean) {
            isLogVisible = status
        }

        fun message(data: String) {
            if (isLogVisible) {
                Log.v("RPay SDK :: ", data)
            }
        }
    }

}