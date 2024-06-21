package com.arkan.terbangin.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.databinding.FragmentNotificationBinding
import com.arkan.terbangin.presentation.notification.adapter.NotificationAdapter
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModel()

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter {
            getNotificationByUID(viewModel.getUserID()!!)
        }
    }

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
        setupNotification()
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

    private fun getNotificationByUID(id: String) {
        viewModel.getNotificationByUID(id).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data ->
                        bindNotificationData(data)
                    }
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    showAlertDialog(it.exception?.message.orEmpty())
                },
            )
        }
    }

    private fun setupNotification() {
        binding.rvNotificationList.apply {
            adapter = notificationAdapter
        }
    }

    private fun bindNotificationData(notification: List<Notification>) {
        notificationAdapter.submitData(notification)
    }
}
