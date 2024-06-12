package com.arkan.terbangin.presentation.flightdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.arkan.terbangin.databinding.BottomSheetOrderNonLoginBinding
import com.arkan.terbangin.utils.navigateToLogin
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NonLoginBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetOrderNonLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetOrderNonLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setFullScreen()
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setFullScreen() {
        val bottomSheet: FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
            addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(
                        bottomSheet: View,
                        newState: Int,
                    ) {}

                    override fun onSlide(
                        bottomSheet: View,
                        slideOffset: Float,
                    ) {}
                },
            )
        }
    }

    private fun setClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
        binding.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }
}
