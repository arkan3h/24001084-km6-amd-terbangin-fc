package com.arkan.terbangin.presentation.checkout.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arkan.terbangin.databinding.FragmentCheckoutDetailBinding

class CheckoutDetailFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCheckoutDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}