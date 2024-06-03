package com.arkan.terbangin.presentation.home.passengers_count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arkan.terbangin.databinding.BottomSheetPassengersCountBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassengersCountBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPassengersCountBinding
    private val viewModel: PassengersCountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetPassengersCountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        setQuantity()
    }

    private fun setQuantity() {
        viewModel.adultQty.observe(this) {
            binding.lAdult.tvQtyAdult.text = it.toString()
        }
        viewModel.childrenQty.observe(this) {
            binding.lChildren.tvQtyChildren.text = it.toString()
        }
        viewModel.babyQty.observe(this) {
            binding.lBaby.tvQtyBaby.text = it.toString()
        }
    }

    private fun onClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
        binding.lAdult.btnPlusAdult.setOnClickListener {
            viewModel.addQtyAdult()
        }
        binding.lAdult.btnMinusAdult.setOnClickListener {
            viewModel.removeQtyAdult()
        }
        binding.lChildren.btnPlusChildren.setOnClickListener {
            viewModel.addQtyChildren()
        }
        binding.lChildren.btnMinusChildren.setOnClickListener {
            viewModel.removeQtyChildren()
        }
        binding.lBaby.btnPlusBaby.setOnClickListener {
            viewModel.addQtyBaby()
        }
        binding.lBaby.btnMinusBaby.setOnClickListener {
            viewModel.removeQtyBaby()
        }
    }
}
