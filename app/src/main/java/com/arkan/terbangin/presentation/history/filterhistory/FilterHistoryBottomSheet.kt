package com.arkan.terbangin.presentation.history.filterhistory

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.StatusPayment
import com.arkan.terbangin.databinding.BottomSheetFilterHistoryBinding
import com.arkan.terbangin.presentation.history.filterhistory.adapter.FilterHistorySheetAdapter
import com.arkan.terbangin.presentation.history.filterhistory.adapter.OnFilterItemClickListener
import com.arkan.terbangin.presentation.home.common.FilterStatusListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterHistoryBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFilterHistoryBinding
    private lateinit var filterHistorySheetAdapter: FilterHistorySheetAdapter
    private val filterHistorySheetViewModel: FilterHistorySheetViewModel by viewModel(ownerProducer = { requireParentFragment() })
    var listener: FilterStatusListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetFilterHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setFullScreen()
        setupRecyclerView()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener {
            filterHistorySheetViewModel.selectedStatus.value?.let { selectedStatus ->
                listener?.onFilterStatusSelected(selectedStatus)
                dialog?.dismiss()
            }
        }
        binding.ivCloseTab.setOnClickListener {
            dialog?.cancel()
        }
    }

    private var dismissListener: (() -> Unit)? = null

    fun setOnDismissListener(listener: () -> Unit) {
        dismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.invoke()
    }

    private fun setupRecyclerView() {
        filterHistorySheetAdapter =
            FilterHistorySheetAdapter(
                listOf(
                    StatusPayment("Issued"),
                    StatusPayment("Unpaid"),
                    StatusPayment("Canceled"),
                ),
                object : OnFilterItemClickListener {
                    override fun onItemClick(status: StatusPayment) {
                        filterHistorySheetViewModel.saveSelectedStatus(status)
                    }
                },
            )
        binding.rvListFilterHistorySheet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filterHistorySheetAdapter
        }
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
