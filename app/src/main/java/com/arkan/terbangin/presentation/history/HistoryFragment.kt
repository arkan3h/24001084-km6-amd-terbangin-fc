package com.arkan.terbangin.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentHistoryBinding
import com.arkan.terbangin.presentation.history.calendarfilterhistory.CalenderFilterHistoryBottomSheet
import com.arkan.terbangin.presentation.history.searchhistory.HistorySearchBottomSheet
import com.arkan.terbangin.utils.navigateToLogin
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var listenerBookingCode: String? = null

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
        observeHistoryData()
        setOnClickListener()
    }

    private fun setState() {
        binding.fragmentHistoryNonLogin.tvTitle.text = getString(R.string.text_riwayat_pesanan)
        binding.layoutHistoryNonLogin.isVisible = viewModel.isLoggedIn == null
        binding.layoutHistory.isVisible = viewModel.isLoggedIn != null
        setUpAdapter()
    }

    private fun setUpAdapter() {
        binding.rvItemDataHistory.adapter = this@HistoryFragment.groupAdapter
        binding.rvItemDataHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeHistoryData() {

    }

    private fun setOnClickListener() {
        binding.ibBtnSearch.setOnClickListener {
            gotoSearchFlightNumber()
        }
        binding.ibBtnFilter.setOnClickListener {
            CalenderFilterHistoryBottomSheet().show(childFragmentManager, null)
        }
        binding.fragmentHistoryNonLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun gotoSearchFlightNumber() {
        val dialog = HistorySearchBottomSheet()
        dialog.show(childFragmentManager, dialog.tag)
        childFragmentManager.setFragmentResultListener(
            "bookingCode",
            this,
        ) { _, bundle ->
            listenerBookingCode = bundle.getString("data")
            observeSearchData(listenerBookingCode)
        }
    }

    private fun observeSearchData(bookingCode: String?) {

    }
}
