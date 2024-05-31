package com.arkan.terbangin.data.source.network.services

import com.arkan.terbangin.BuildConfig
import com.arkan.terbangin.data.source.network.model.login.LoginResponse
import com.arkan.terbangin.data.source.network.model.otp.request_otp.RequestOTPResponse
import com.arkan.terbangin.data.source.network.model.otp.verify_otp.VerifyOTPResponse
import com.arkan.terbangin.data.source.network.model.register.RegisterResponse
import com.arkan.terbangin.data.source.network.model.resetpassword.ResetPasswordResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

interface TerbanginApiServices {
    @Multipart
    @POST("api/v1/auth/register")
    suspend fun doRegister(
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("password") password: RequestBody,
    ): RegisterResponse

    @Multipart
    @POST("/api/v1/auth/login")
    suspend fun doLogin(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
    ): LoginResponse

    @Multipart
    @POST("/api/v1/verification/generate-link")
    suspend fun doResetPassword(
        @Part("email") email: RequestBody,
    ): ResetPasswordResponse

    @Multipart
    @POST("/api/v1/verification/generate-otp-email")
    suspend fun requestOTP(
        @Part("email") email: RequestBody,
    ): RequestOTPResponse

    @POST("api/v1/verification/verify-otp")
    suspend fun verifyOTP(
        @Part("email") email: RequestBody,
        @Part("code") otp: RequestBody,
    ): VerifyOTPResponse

    companion object {
        @JvmStatic
        operator fun invoke(): TerbanginApiServices {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
//                    .addInterceptor { chain ->
//                        val request =
//                            chain.request().newBuilder()
//                                .addHeader("accept", "application/json")
//                                .addHeader("x-cg-demo-api-key", BuildConfig.API_KEY)
//                        chain.proceed(request.build())
//                    }
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(TerbanginApiServices::class.java)
        }
    }
}
