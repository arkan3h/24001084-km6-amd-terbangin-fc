package com.arkan.terbangin.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentNotificationBinding
import com.arkan.terbangin.utils.navigateToLogin
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
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
        binding.fragmentNotificationNonLogin.tvTitle.text = getString(R.string.txt_title_notification_fragment)
        binding.layoutNotificationNonLogin.isVisible = viewModel.isLoggedIn == null
        binding.layoutNotification.isVisible = viewModel.isLoggedIn != null
    }

    private fun setOnClickListener() {
        binding.fragmentNotificationNonLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }
}
