package com.arkan.terbangin.presentation.home.terminal_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.databinding.BottomSheetTerminalSearchBinding
import com.arkan.terbangin.presentation.home.common.HomeSaveButtonClickListener
import com.arkan.terbangin.presentation.home.terminal_search.adapter.HistoryTerminalListener
import com.arkan.terbangin.presentation.home.terminal_search.adapter.TerminalAdapter
import com.arkan.terbangin.presentation.home.terminal_search.adapter.TerminalHistoryAdapter
import com.arkan.terbangin.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TerminalSearchBottomSheet(
    private val location: String,
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetTerminalSearchBinding
    private val viewModel: TerminalSearchViewModel by viewModel()
    private var adapter: TerminalAdapter? = null
    private var citySelectedListener: HomeSaveButtonClickListener? = null
    private val historyAdapter: TerminalHistoryAdapter by lazy {
        TerminalHistoryAdapter(
            object : OnItemCLickedListener<SearchHistory> {
                override fun onItemClicked(item: SearchHistory) {
                    binding.sbTerminalSearch.setQuery(item.query, true)
                }
            },
            object : HistoryTerminalListener {
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
        setupSearch()
        getHistoryData()
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

    fun setCitySelectedListener(listener: HomeSaveButtonClickListener) {
        citySelectedListener = listener
    }

    private fun onClickListener() {
        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
        binding.tvDeleteSearchHistory.setOnClickListener {
            viewModel.clearSearchHistory()
            getHistoryData()
        }
    }

    private fun setupSearch() {
        binding.sbTerminalSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        getAirportCityData()
                        viewModel.searchCities(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        getAirportCityData()
                        viewModel.searchCities(it)
                    }
                    return true
                }
            },
        )
    }

    private fun getAirportCityData() {
        viewModel.searchResults.observe(viewLifecycleOwner) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvTerminalSearchResult.isVisible = false
                    binding.lSearchHistory.isVisible = false
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.rvTerminalSearchResult.isVisible = false
                    binding.lSearchHistory.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvTerminalSearchResult.isVisible = true
                    binding.lSearchHistory.isVisible = false
                    it.payload?.let { data ->
                        bindAirportCityList(data)
                    }
                },
                doOnEmpty = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.rvTerminalSearchResult.isVisible = false
                    binding.lSearchHistory.isVisible = false
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
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data ->
                        bindAirportCityHistory(data)
                    }
                },
                doOnEmpty = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                },
            )
        }
    }

    private fun bindAirportCityList(data: List<Airport>) {
        adapter =
            TerminalAdapter(
                context = requireContext(),
                listener =
                    object : OnItemCLickedListener<Airport> {
                        override fun onItemClicked(item: Airport) {
                            viewModel.saveSearchHistory(item.city).observe(viewLifecycleOwner) {
                                it.proceedWhen(
                                    doOnSuccess = {},
                                    doOnError = {
                                        Toast.makeText(requireContext(), "gagal ${it.exception}", Toast.LENGTH_SHORT).show()
                                    },
                                )
                            }
                            citySelectedListener?.onCitySelected(item, location)
                            dialog?.dismiss()
                        }
                    },
            )
        binding.rvTerminalSearchResult.adapter = this@TerminalSearchBottomSheet.adapter
        adapter?.submitData(data)
    }

    private fun bindAirportCityHistory(data: List<SearchHistory>) {
        binding.rvTerminalSearchHistory.adapter = this@TerminalSearchBottomSheet.historyAdapter
        historyAdapter.submitData(data)
    }
}
