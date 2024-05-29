package com.arkan.terbangin.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentHomeBinding
import com.arkan.terbangin.presentation.flightsearch.FlightSearchActivity
import com.arkan.terbangin.presentation.passengerscount.PassengersCountBottomSheet
import com.arkan.terbangin.presentation.terminalsearch.TerminalSearchBottomSheet

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for the button
        binding.btnOpenClassSheet.setOnClickListener {
            findNavController().navigate(R.id.action_menu_tab_home_to_classSheetFragment)
        }
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
        binding.layoutSearchHome.btnSearchFlight.setOnClickListener {
            navigateToFlightSearch()
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

    private fun navigateToFlightSearch() {
        startActivity(
            Intent(activity, FlightSearchActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
