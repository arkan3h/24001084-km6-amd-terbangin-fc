package com.arkan.terbangin.presentation.history.searchhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.arkan.terbangin.databinding.BottomSheetHistorySearchBinding
import com.arkan.terbangin.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistorySearchBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetHistorySearchBinding
    private val viewModel: HistorySearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetHistorySearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setFullScreen()
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        setupUI()
    }

    private fun setClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun setupUI() {
        binding.svHistorySearch.setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.saveSearchHistory(query).observe(viewLifecycleOwner) {
                        it.proceedWhen(
                            doOnSuccess = {
                                Toast.makeText(requireContext(), "berhasil", Toast.LENGTH_SHORT).show()
                            },
                            doOnError = {
                                Toast.makeText(requireContext(), "gagal", Toast.LENGTH_SHORT).show()
                            },
                        )
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }
            },
        )
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
}
