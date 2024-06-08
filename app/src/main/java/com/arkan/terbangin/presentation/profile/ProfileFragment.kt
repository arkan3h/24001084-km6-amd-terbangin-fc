package com.arkan.terbangin.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentProfileBinding
import com.arkan.terbangin.utils.navigateToEditProfile
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.navigateToSettingAccount
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setState()
        setClickListener()
    }

    private fun setState() {
        binding.fragmentProfileNonLogin.tvTitle.text = getString(R.string.title_tab_profile)
        binding.layoutProfileNonLogin.isVisible = viewModel.isLoggedIn == null
        binding.layoutProfile.isVisible = viewModel.isLoggedIn != null
    }

    private fun setClickListener() {
        binding.llMenuEditProfile.setOnClickListener {
            navigateToEditProfile()
        }
        binding.llMenuAccountSettings.setOnClickListener {
            navigateToSettingAccount()
        }
        binding.llMenuLogout.setOnClickListener {
            showAlertLogoutDialog()
        }
        binding.fragmentProfileNonLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun showAlertLogoutDialog() {
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null)
        val cancelBtn = dialogView.findViewById<Button>(R.id.btn_cancel)
        val logoutBtn = dialogView.findViewById<Button>(R.id.btn_logout)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(dialogView)

        val dialog = alertDialogBuilder.create()

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        logoutBtn.setOnClickListener {
            dialog.dismiss()
            viewModel.doLogout()
            navigateToLogin()
        }

        dialog.show()
    }
}
