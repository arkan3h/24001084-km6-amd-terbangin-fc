package com.arkan.terbangin.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arkan.terbangin.databinding.FragmentHistoryBinding
import com.arkan.terbangin.presentation.calenderfilterhistory.CalenderFilterHistoryBottomSheet
import com.arkan.terbangin.presentation.historysearch.HistorySearchBottomSheet

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

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
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ibBtnSearch.setOnClickListener {
            gotoSearchFlightNumber()
        }
        binding.ibBtnFilter.setOnClickListener {
            CalenderFilterHistoryBottomSheet().show(childFragmentManager, null)
        }
    }

    private fun gotoSearchFlightNumber() {
        HistorySearchBottomSheet().show(childFragmentManager, null)
    }
}
