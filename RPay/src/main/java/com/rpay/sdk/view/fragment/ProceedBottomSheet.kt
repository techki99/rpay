package com.rpay.sdk.view.fragment

import android.app.FragmentManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rpay.sdk.core.RPay
import com.rpay.sdk.core.RPayLog
import com.rpay.sdk.databinding.ProceedBottomSheetBinding

internal class ProceedBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: ProceedBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProceedBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

}