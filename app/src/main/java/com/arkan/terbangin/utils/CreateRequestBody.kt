package com.arkan.terbangin.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun createPartFromString(descriptionString: String): RequestBody {
    return descriptionString.toRequestBody("text/plain".toMediaTypeOrNull())
}
