package com.arkan.terbangin.presentation.home.passengers_count

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arkan.terbangin.databinding.BottomSheetPassengersCountBinding
import com.arkan.terbangin.presentation.home.common.HomeSaveButtonClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassengersCountBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPassengersCountBinding
    private val viewModel: PassengersCountViewModel by viewModel(ownerProducer = { requireParentFragment() })
    var listener: HomeSaveButtonClickListener? = null

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
        binding.bSavePassengers.setOnClickListener {
            notifyListener()
            observeTotalQty()
            dialog?.dismiss()
        }
    }

    private fun observeTotalQty() {
        viewModel.totalQty.observe(this) {
            notifyListener()
        }
    }

    private fun notifyListener() {
        listener?.onPassengersCountUpdated(
            viewModel.adultQty.value ?: 0,
            viewModel.childrenQty.value ?: 0,
            viewModel.babyQty.value ?: 0,
            viewModel.totalQty.value ?: 0,
        )
    }

    private var dismissListener: (() -> Unit)? = null

    fun setOnDismissListener(listener: () -> Unit) {
        dismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.invoke()
    }
}
