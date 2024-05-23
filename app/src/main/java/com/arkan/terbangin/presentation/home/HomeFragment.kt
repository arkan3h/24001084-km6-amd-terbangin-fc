package com.arkan.terbangin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.databinding.FragmentHomeBinding

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
    }

    private fun setRoundTrip() {
        binding.layoutSearchHome.switchRoundTrip.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutSearchHome.layoutReturnSearch.layoutReturnSearch.isVisible = isChecked
        }
    }
}
