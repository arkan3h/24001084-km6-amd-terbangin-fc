package com.arkan.terbangin.presentation.history.searchhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.databinding.BottomSheetHistorySearchBinding
import com.arkan.terbangin.presentation.history.searchhistory.adapter.HistoryListener
import com.arkan.terbangin.presentation.history.searchhistory.adapter.SearchHistoryAdapter
import com.arkan.terbangin.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistorySearchBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetHistorySearchBinding
    private val viewModel: HistorySearchViewModel by viewModel()
    private val historyAdapter: SearchHistoryAdapter by lazy {
        SearchHistoryAdapter(
            object : OnItemCLickedListener<SearchHistory> {
                override fun onItemClicked(item: SearchHistory) {
                    binding.svHistorySearch.setQuery(item.query, true)
                }
            },
            object : HistoryListener {
                override fun deleteHistory(item: SearchHistory) {
                    viewModel.deleteSearchHistory(item)
                    getHistoryData()
                }
            },
        )
    }

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
        getHistoryData()
    }

    private fun setClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
        binding.tvDeleteSearchHistory.setOnClickListener {
            viewModel.clearSearchHistory()
            getHistoryData()
        }
    }

    private fun setupUI() {
        binding.svHistorySearch.setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.saveSearchHistory(query).observe(viewLifecycleOwner) {
                        it.proceedWhen(
                            doOnSuccess = {},
                            doOnError = {
                                Toast.makeText(requireContext(), "gagal ${it.exception}", Toast.LENGTH_SHORT).show()
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

    private fun getHistoryData() {
        viewModel.getSearchHistory().observe(viewLifecycleOwner) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.toString()
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data ->
                        bindHistory(data)
                    }
                },
                doOnEmpty = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    historyAdapter.submitData(emptyList())
                },
            )
        }
    }

    private fun bindHistory(data: List<SearchHistory>) {
        binding.rvHistorySearchHistory.adapter = this@HistorySearchBottomSheet.historyAdapter
        historyAdapter.submitData(data)
    }
}
