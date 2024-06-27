package com.arkan.terbangin.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arkan.terbangin.presentation.auth.login.LoginActivity
import com.arkan.terbangin.presentation.auth.register.RegisterActivity
import com.arkan.terbangin.presentation.auth.reset_password.ResetPasswordActivity
import com.arkan.terbangin.presentation.main.MainActivity
import com.arkan.terbangin.presentation.profile.edit_profile.EditProfileActivity
import com.arkan.terbangin.presentation.profile.setting_account.SettingAccountActivity

/** ACTIVITY */
fun AppCompatActivity.navigateToLogin() {
    startActivity(
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        },
    )
}

fun AppCompatActivity.navigateToMain() {
    startActivity(
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        },
    )
}

fun AppCompatActivity.navigateToRegister() {
    startActivity(
        Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        },
    )
}

fun AppCompatActivity.navigateToResetPassword() {
    startActivity(
        Intent(this, ResetPasswordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        },
    )
}

/** FRAGMENT */
fun Fragment.navigateToLogin() {
    startActivity(
        Intent(activity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        },
    )
}

fun Fragment.navigateToSettingAccount() {
    startActivity(Intent(activity, SettingAccountActivity::class.java))
}

fun Fragment.navigateToEditProfile() {
    startActivity(Intent(activity, EditProfileActivity::class.java))
}

fun Fragment.navigateToResetPassword() {
    startActivity(
        Intent(activity, ResetPasswordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        },
    )
}
