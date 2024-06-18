package com.arkan.terbangin.utils

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.io.IOException
import java.net.UnknownHostException

class ErrorInterceptor : Interceptor {
    private var successMessage: String? = null
        private set

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response =
            try {
                chain.proceed(request)
            } catch (e: UnknownHostException) {
                throw NoInternetException("No Internet Connection")
            }

        val rawBody = response.body?.string()
        val bufferedBody = rawBody?.toByteArray()?.toResponseBody(response.body?.contentType())

        if (response.isSuccessful) {
            if (response.code == 200) {
                successMessage = parseSuccessMessage(rawBody)
                // Optionally, handle the success message here or log it
            }
        } else {
            val errorMessage = parseErrorMessage(response.code, rawBody)
            throw HttpException(response.code, errorMessage)
        }

        return response.newBuilder()
            .body(bufferedBody)
            .build()
    }

    private fun parseSuccessMessage(responseBody: String?): String {
        return try {
            val jsonObject = JSONObject(responseBody ?: "")
            jsonObject.getString("message")
        } catch (e: Exception) {
            "Unknown success message"
        }
    }

    private fun parseErrorMessage(
        statusCode: Int,
        errorBody: String?,
    ): String {
        return try {
            val jsonObject = JSONObject(errorBody ?: "")
            when (statusCode) {
                400 -> jsonObject.getString("message")
                500 -> "Internal Server Error"
                else -> "Unknown error"
            }
        } catch (e: Exception) {
            "Unknown error"
        }
    }

    private fun String.toResponseBody(contentType: okhttp3.MediaType?): ResponseBody {
        return this.toByteArray().toResponseBody(contentType)
    }

    class HttpException(val code: Int, message: String) : IOException(message)

    class NoInternetException(message: String) : IOException(message)
}
