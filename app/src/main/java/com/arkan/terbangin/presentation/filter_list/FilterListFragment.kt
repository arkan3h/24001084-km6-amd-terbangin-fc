package com.arkan.terbangin.presentation.filter_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentFilterListBinding
import com.arkan.terbangin.model.FilterList
import com.arkan.terbangin.presentation.filter_list.adapter.FilterListAdapter
import com.arkan.terbangin.presentation.filter_list.adapter.OnClassItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterListFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterListBinding
    private lateinit var FilterListAdapter: FilterListAdapter
    private val FilterListViewModel: FilterListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setFullScreen()
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSaveButton()
        setupCloseButton()
    }

    private fun setFullScreen() {
        val bottomSheet: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
            addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(
                        bottomSheet: View,
                        newState: Int,
                    ) {
                    }

                    override fun onSlide(
                        bottomSheet: View,
                        slideOffset: Float,
                    ) {
                    }
                },
            )
        }
    }

    private fun setupRecyclerView() {
        FilterListAdapter =
            FilterListAdapter(
                listOf(
                    FilterList(getString(R.string.price_filter), getString(R.string.list_filter_1)),
                    FilterList(getString(R.string.departure_filter), getString(R.string.list_filter_2)),
                    FilterList(getString(R.string.departure_filter_2),getString(R.string.list_filer_3)),
                    FilterList(getString(R.string.arrival_filter), getString(R.string.list_filter_4)),
                    FilterList(getString(R.string.arrival_filter_2), getString(R.string.list_filter_5)),
                ),
                object : OnClassItemClickListener {
                    override fun onItemClick(filterList: FilterList) {
                        // Simpan kelas tiket yang dipilih ke dalam ViewModel
                        FilterListViewModel.saveSelectedFilterListClass(filterList)
                    }
                },
            )
        binding.rvListFilter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FilterListAdapter
        }
    }

    private fun setupSaveButton() {
        binding.btnSaveFilterList.setOnClickListener {
            FilterListViewModel.selectedFilterListClass.value?.let { selectedClass ->
                // Lakukan sesuatu dengan kelas tiket yang dipilih
                // Misalnya, tampilkan toast atau navigasi ke halaman lain
            }
        }
    }

    private fun setupCloseButton() {
        binding.ivCloseTabFilterList.setOnClickListener {
            dialog?.cancel()
        }
    }
}
