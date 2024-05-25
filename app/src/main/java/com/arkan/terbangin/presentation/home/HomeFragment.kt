package com.arkan.terbangin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.databinding.FragmentHomeBinding
import com.arkan.terbangin.presentation.passengerscount.PassengersCountBottomSheet
import com.arkan.terbangin.presentation.terminalsearch.TerminalSearchBottomSheet

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setRoundTrip()
        onClickListener()
    }

    private fun onClickListener() {
        binding.layoutSearchHome.tvFlightFrom.setOnClickListener {
            openSearch("Pilih Lokasi Awal")
        }
        binding.layoutSearchHome.tvFlightTo.setOnClickListener {
            openSearch("Pilih Tujuan")
        }
        binding.layoutSearchHome.layoutPassengersSearch.layoutPassengersSearch.setOnClickListener {
            selectPassengers()
        }
    }

    private fun setRoundTrip() {
        binding.layoutSearchHome.switchRoundTrip.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutSearchHome.layoutReturnSearch.layoutReturnSearch.isVisible = isChecked
        }
    }

    private fun openSearch(location: String) {
        TerminalSearchBottomSheet(location).show(childFragmentManager, null)
    }

    private fun selectPassengers() {
        PassengersCountBottomSheet().show(childFragmentManager, null)
    }
}
