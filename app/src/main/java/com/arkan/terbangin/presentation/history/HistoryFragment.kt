package com.arkan.terbangin.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseFragment
import com.arkan.terbangin.data.model.StatusPayment
import com.arkan.terbangin.databinding.FragmentHistoryBinding
import com.arkan.terbangin.presentation.history.adapter.HistoryDataItem
import com.arkan.terbangin.presentation.history.adapter.HistoryMonthHeaderItem
import com.arkan.terbangin.presentation.history.filterhistory.FilterHistoryBottomSheet
import com.arkan.terbangin.presentation.history.searchhistory.HistorySearchBottomSheet
import com.arkan.terbangin.presentation.home.common.FilterStatusListener
import com.arkan.terbangin.utils.formatMonthHeaderStringHistory
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.proceedWhen
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment(), FilterStatusListener {
    private lateinit var binding: FragmentHistoryBinding
    private var groupAdapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setState()
        setOnClickListener()
    }

    private fun setState() {
        viewModel.resetFilterStatus()
        binding.tvFilter.text = getString(R.string.binding_filter)
        if (viewModel.isLoggedIn != null) {
            getHistoryByUUID(viewModel.getUserID()!!, "")
        }
        binding.fragmentHistoryNonLogin.tvTitle.text = getString(R.string.text_riwayat_pesanan)
        binding.layoutHistoryNonLogin.isVisible = viewModel.isLoggedIn == null
        binding.layoutHistory.isVisible = viewModel.isLoggedIn != null
        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.rvItemDataHistory.adapter = this@HistoryFragment.groupAdapter
        binding.rvItemDataHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getHistoryByUUID(
        id: String,
        query: String,
    ) {
        viewModel.getHistoryByUUID(id, query).observe(viewLifecycleOwner) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutStateHistory.pbLoading.isVisible = true
                    binding.layoutStateHistory.tvError1.isVisible = false
                    binding.layoutStateHistory.tvError2.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutStateHistory.pbLoading.isVisible = false
                    binding.layoutStateHistory.tvError1.isVisible = false
                    binding.layoutStateHistory.tvError2.isVisible = false
                    it.payload?.let { data ->
                        val items = mutableListOf<BindableItem<*>>()

                        val groupedData =
                            data.reversed().groupBy { history ->
                                formatMonthHeaderStringHistory(history.monthHeader)
                            }

                        groupedData.forEach { (monthYear, dataList) ->
                            items.add(HistoryMonthHeaderItem(monthYear))
                            dataList.forEach { data ->
                                items.add(HistoryDataItem(data))
                            }
                        }
                        groupAdapter.update(items)
                    }
                },
                doOnError = {
                    binding.layoutStateHistory.pbLoading.isVisible = false
                    binding.layoutStateHistory.tvError1.isVisible = true
                    binding.layoutStateHistory.tvError2.isVisible = true
                    it.exception?.let { e -> handleError(e) }
                },
            )
        }
    }

    private fun setOnClickListener() {
        binding.ibBtnSearch.setOnClickListener {
            gotoSearchFlightNumber()
        }
        binding.ibBtnFilter.setOnClickListener {
            selectFilter()
        }
        binding.fragmentHistoryNonLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun gotoSearchFlightNumber() {
        val searchHistoryFragment = HistorySearchBottomSheet()
        searchHistoryFragment.listener = this
        searchHistoryFragment.show(childFragmentManager, searchHistoryFragment.tag)
    }

    private fun selectFilter() {
        val filterHistoryFragment = FilterHistoryBottomSheet()
        filterHistoryFragment.listener = this
        filterHistoryFragment.show(childFragmentManager, filterHistoryFragment.tag)
    }

    override fun onFilterStatusSelected(status: StatusPayment) {
        viewModel.saveSelectedStatus(status)
        viewModel.getUserID()?.let { getHistoryByUUID(it, "") }
        viewModel.filter.observe(viewLifecycleOwner) {
            binding.tvFilter.text = it.statusPayment
        }
    }

    override fun onSearch(query: String) {
        viewModel.getUserID()?.let { getHistoryByUUID(it, query) }
    }
}
