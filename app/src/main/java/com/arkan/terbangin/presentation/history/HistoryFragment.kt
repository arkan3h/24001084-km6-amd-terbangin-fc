package com.arkan.terbangin.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentHistoryBinding
import com.arkan.terbangin.presentation.auth.login.LoginActivity
import com.arkan.terbangin.presentation.history.calendarfilterhistory.CalenderFilterHistoryBottomSheet
import com.arkan.terbangin.presentation.calendarfilterhistory.CalenderFilterHistoryBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

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
        binding.fragmentHistoryNonLogin.tvTitle.text = getString(R.string.text_riwayat_pesanan)
        binding.layoutHistoryNonLogin.isVisible = viewModel.isLoggedIn == null
        binding.layoutHistory.isVisible = viewModel.isLoggedIn != null
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
        HistorySearchBottomSheet().show(childFragmentManager, null)
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
