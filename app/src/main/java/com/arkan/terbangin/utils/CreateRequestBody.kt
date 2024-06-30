package com.arkan.terbangin.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun createPartFromString(descriptionString: String): RequestBody {
    return descriptionString.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun prepareFilePart(file: File): MultipartBody.Part {
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("picture", file.name, requestFile)
}
