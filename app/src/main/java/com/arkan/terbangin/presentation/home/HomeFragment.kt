package com.arkan.terbangin.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.databinding.FragmentHomeBinding
import com.arkan.terbangin.presentation.flight_search.FlightSearchActivity
import com.arkan.terbangin.presentation.home.class_sheet.ClassSheetFragment
import com.arkan.terbangin.presentation.home.passengers_count.PassengersCountBottomSheet
import com.arkan.terbangin.presentation.home.terminal_search.TerminalSearchBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()

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
        binding.layoutSearchHome.layoutSeatClassSearch.layoutSeatClassSearch.setOnClickListener {
            selectSeatClass()
        }
        binding.layoutSearchHome.btnSearchFlight.setOnClickListener {
            navigateToFlightSearch()
        }
        binding.layoutSearchHome.lUpDown.setOnClickListener {
            Toast.makeText(requireContext(), "${viewModel.getToken()}", Toast.LENGTH_SHORT).show()
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

    private fun selectSeatClass() {
        ClassSheetFragment().show(childFragmentManager, null)
    }

    private fun navigateToFlightSearch() {
        startActivity(
            Intent(activity, FlightSearchActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
