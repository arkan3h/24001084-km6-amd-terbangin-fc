package com.arkan.terbangin.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.FragmentProfileBinding
import com.arkan.terbangin.presentation.auth.login.LoginActivity

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

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
        setClickListener()
    }

    private fun setClickListener() {
        binding.llMenuLogout.setOnClickListener {
            showAlertLogoutDialog()
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
            navigateToLogin()
        }

        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(activity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
