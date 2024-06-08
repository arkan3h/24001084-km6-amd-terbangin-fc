package com.arkan.terbangin.presentation.flightdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arkan.terbangin.databinding.BottomSheetTicketSoldOutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TicketSoldOutBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetTicketSoldOutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetTicketSoldOutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
        binding.btnUbahPencarian.setOnClickListener {
            // TODO
            // navigateToFlightSearch()
        }
    }
}
