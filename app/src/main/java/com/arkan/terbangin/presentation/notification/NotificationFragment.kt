package com.arkan.terbangin.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseFragment
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.databinding.FragmentNotificationBinding
import com.arkan.terbangin.presentation.notification.adapter.NotificationAdapter
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment() {
    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModel()

    private var notificationAdapter: NotificationAdapter? = null

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

        getNotificationByUID(viewModel.getUserID()!!)
        setState()
        setOnClickListener()
//        setupNotification()
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
        viewModel.getNotificationByUID(id).observe(viewLifecycleOwner) { it ->
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
                    it.exception?.let { e -> handleError(e) }
                },
            )
        }
    }

//    private fun setupNotification() {
//        binding.rvNotificationList.apply {
//            adapter = notificationAdapter
//        }
//    }

    private fun bindNotificationData(notification: List<Notification>) {
        notificationAdapter =
            NotificationAdapter(
                object : OnItemCLickedListener<Notification> {
                    override fun onItemClicked(item: Notification) {
                    }
                },
            )
        binding.rvNotificationList.adapter = this.notificationAdapter
        notificationAdapter?.submitData(notification)
    }
}
