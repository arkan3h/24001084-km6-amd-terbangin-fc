package com.arkan.terbangin.presentation.history.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arkan.terbangin.databinding.FragmentHistoryDetailBinding

class HistoryDetailFragment : Fragment() {
    private lateinit var binding: FragmentHistoryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}