package com.arkan.terbangin.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arkan.terbangin.databinding.FragmentHistoryBinding
import com.arkan.terbangin.presentation.calenderfilterhistory.Example4Fragment
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
//            val intent = Intent(requireContext(), Example4Fragment::class.java)
//            startActivity(intent)
        }
    }

    private fun gotoSearchFlightNumber() {
        HistorySearchBottomSheet().show(childFragmentManager, null)
    }
}
