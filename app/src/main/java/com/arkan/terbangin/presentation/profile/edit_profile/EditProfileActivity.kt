package com.arkan.terbangin.presentation.profile.edit_profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.databinding.ActivityEditProfileBinding
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {
    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
        viewModel.getUserID()?.let { uid -> getProfile(uid) }
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSaveProfile.setOnClickListener {
            updateProfile()
        }
    }

    private fun getProfile(id: String) {
        viewModel.getProfile(id).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.profileContent.isVisible = true
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data ->
                        bindProfileData(data)
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

    private fun bindProfileData(profile: Profile) {
        profile.let {
            // binding.ivProfileImage.load(it.picture) {
            //    crossfade(true)
            // }
            binding.tiEtName.setText(it.fullName)
            binding.tiEtEmail.setText(it.email)
            binding.tiEtPhoneNumber.setText(it.phoneNumber)
        }
    }

    private fun updateProfile() {
        val id = viewModel.getUserID().orEmpty()
        val fullName = binding.tiEtName.text.toString()
        val email = binding.tiEtEmail.text.toString()
        val phoneNumber = binding.tiEtPhoneNumber.text.toString()
        viewModel.updateProfile(id, fullName, email, phoneNumber, null).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.profileContent.isVisible = true
                },
                doOnSuccess = {
                    binding.profileContent.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    showAlertDialog("Profile updated successfully")
                },
                doOnError = {
                    binding.profileContent.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    showAlertDialog(it.exception?.message.orEmpty())
                },
            )
        }
    }
}
