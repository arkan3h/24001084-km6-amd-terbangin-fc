package com.arkan.terbangin.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseFragment
import com.arkan.terbangin.databinding.FragmentHistoryBinding
import com.arkan.terbangin.presentation.history.adapter.HistoryDataItem
import com.arkan.terbangin.presentation.history.adapter.HistoryMonthHeaderItem
import com.arkan.terbangin.presentation.history.calendarfilterhistory.CalenderFilterHistoryBottomSheet
import com.arkan.terbangin.utils.formatMonthHeaderStringHistory
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.proceedWhen
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment() {
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
        if (viewModel.isLoggedIn != null) {
            getHistoryByUID(viewModel.getUserID()!!)
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

    private fun getHistoryByUID(id: String) {
        viewModel.getHistoryByUID(id).observe(viewLifecycleOwner) { it ->
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

                        val groupedData = data.groupBy { formatMonthHeaderStringHistory(it.monthHeader) }

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
//            gotoSearchFlightNumber()
        }
        binding.ibBtnFilter.setOnClickListener {
            CalenderFilterHistoryBottomSheet().show(childFragmentManager, null)
        }
        binding.fragmentHistoryNonLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }
}
