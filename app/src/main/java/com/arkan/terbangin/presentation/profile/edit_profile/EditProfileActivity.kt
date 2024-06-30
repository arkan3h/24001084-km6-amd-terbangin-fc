package com.arkan.terbangin.presentation.profile.edit_profile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import coil.load
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseActivity
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.databinding.ActivityEditProfileBinding
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileActivity : BaseActivity() {
    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModel()
    private lateinit var email: String

    private lateinit var imagePickLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var profileImage: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setClickListener()
        editProfileImage()
        viewModel.getUserID()?.let { uid -> getProfile(uid) }
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSaveProfile.setOnClickListener {
            updateProfile()
        }
        binding.tvResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun editProfileImage() {
        // Register the image picker launcher
        imagePickLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val fileUri: Uri? = result.data?.data
                    fileUri?.let { uri ->
                        val file = uri.path?.let { File(it) }
                        profileImage = file!!
                    }
                    binding.ivProfileImage.load(fileUri) {
                        crossfade(true)
                    }
                } else if (result.resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(result.data), Toast.LENGTH_SHORT).show()
                }
            }

        // Register the permission request launcher
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted ->
                if (isGranted) {
                    pickImage()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnEditPhoto.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES,
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickImage()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
        }
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent -> imagePickLauncher.launch(intent) }
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
                    it.exception?.let { e -> handleError(e) }
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                },
            )
        }
    }

    private fun bindProfileData(profile: Profile) {
        profile.let {
            binding.ivProfileImage.load(it.picture) {
                crossfade(true)
            }
            binding.tiEtName.setText(it.fullName)
            binding.tiEtEmail.setText(it.email)
            binding.tiEtPhoneNumber.setText(it.phoneNumber)
            email = it.email
        }
    }

    private fun updateProfile() {
        val id = viewModel.getUserID().orEmpty()
        val fullName = binding.tiEtName.text.toString()
        val email = binding.tiEtEmail.text.toString()
        val phoneNumber = binding.tiEtPhoneNumber.text.toString()
        viewModel.updateProfile(id, fullName, email, phoneNumber, profileImage).observe(this) { it ->
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
                    it.exception?.let { e -> handleError(e) }
                    binding.profileContent.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                },
            )
        }
    }

    private fun resetPassword() {
        viewModel.doResetPassword(email).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    resetPasswordDialog()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    it.exception?.let { e -> handleError(e) }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                },
            )
        }
    }

    private fun resetPasswordDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_reset_password)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val backBtn: Button = dialog.findViewById(R.id.btn_back_reset_password_dialog)
        backBtn.setOnClickListener {
            dialog.dismiss()
            onBackPressedDispatcher.onBackPressed()
        }
        dialog.show()
    }
}
