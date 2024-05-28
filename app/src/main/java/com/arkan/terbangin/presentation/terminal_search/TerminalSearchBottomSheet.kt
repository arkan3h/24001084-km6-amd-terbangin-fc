package com.arkan.terbangin.presentation.terminal_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.arkan.terbangin.databinding.BottomSheetTerminalSearchBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TerminalSearchBottomSheet(
    private val location: String,
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetTerminalSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetTerminalSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setFullScreen()
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        bindTitle()
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

    private fun bindTitle() {
        binding.tvSearchTerminalTitle.text = location
    }

    private fun onClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
    }
}
