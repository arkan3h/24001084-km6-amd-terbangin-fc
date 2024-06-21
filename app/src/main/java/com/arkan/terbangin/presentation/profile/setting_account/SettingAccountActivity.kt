package com.arkan.terbangin.presentation.profile.setting_account

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivitySettingAccountBinding
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingAccountActivity : AppCompatActivity() {
    private val binding: ActivitySettingAccountBinding by lazy {
        ActivitySettingAccountBinding.inflate(layoutInflater)
    }

    private val settingAccountViewModel: SettingAccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_setting_account)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSwitchListener()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.llDeleteAccount.setOnClickListener {
            showAlertDeleteAccountDialog()
        }
    }

    private fun applyUiMode() {
        val isUsingDarkMode = settingAccountViewModel.isUsingDarkMode()
        AppCompatDelegate.setDefaultNightMode(
            if (isUsingDarkMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            },
        )
        binding.swDarkMode.isChecked = isUsingDarkMode
    }

    private fun setSwitchListener() {
        binding.swDarkMode.setOnCheckedChangeListener { btn, isChecked ->
            settingAccountViewModel.setUsingDarkMode(isChecked)
            applyUiMode()
        }
    }

    private fun showAlertDeleteAccountDialog() {
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_delete_account, null)
        val cancelBtn = dialogView.findViewById<Button>(R.id.btn_cancel)
        val deleteBtn = dialogView.findViewById<Button>(R.id.btn_delete_acc)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)

        val dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        deleteBtn.setOnClickListener {
            dialog.dismiss()
            settingAccountViewModel.getUserID()?.let { uid ->
                deleteProfile(uid)
            }
        }

        dialog.show()
    }

    private fun deleteProfile(id: String) {
        settingAccountViewModel.deleteProfile(id).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    showAlertDialog("Profile deleted successfully")
                    settingAccountViewModel.doLogout()
                    navigateToLogin()
                },
                doOnError = {
                    showAlertDialog(it.exception?.message.orEmpty())
                },
            )
        }
    }
}
