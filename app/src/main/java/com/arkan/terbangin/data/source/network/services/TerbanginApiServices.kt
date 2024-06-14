package com.arkan.terbangin.data.source.network.services

import com.arkan.terbangin.BuildConfig
import com.arkan.terbangin.data.source.network.model.airport.AirportResponse
import com.arkan.terbangin.data.source.network.model.auth.login.LoginResponse
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPResponse
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPResponse
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterResponse
import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
import com.arkan.terbangin.data.source.network.model.flight.FlightResponse
import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponse
import com.arkan.terbangin.data.source.network.model.profile.ProfileResponse
import com.arkan.terbangin.data.source.pref.UserPreference
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
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

    @Multipart
    @POST("api/v1/verification/verify-otp")
    suspend fun verifyOTP(
        @Part("email") email: RequestBody,
        @Part("otp") otp: RequestBody,
    ): VerifyOTPResponse

    @GET("/api/v1/profile/id/{id}")
    suspend fun getProfile(
        @Path("id") id: String?,
    ): ProfileResponse

    @Multipart
    @PATCH("/api/v1/profile/id/{id}")
    suspend fun updateProfile(
        @Path("id") id: String?,
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part picture: MultipartBody.Part?,
    ): ProfileResponse

    @DELETE("/api/v1/profile/id/{id}")
    suspend fun deleteProfile(
        @Path("id") id: String,
    ): ProfileResponse

    @GET("/api/v1/flight/flightfilter")
    suspend fun getAllFlight(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("filter") filter: String = "priceEconomy",
        @Query("order") order: String = "asc",
    ): FlightResponse

    @GET("/api/v1/airport")
    suspend fun getAllAirport(): AirportResponse

    @POST("/api/v1/passanger")
    suspend fun createPassenger(
        @Body payload: PassengerPayload,
    ): PassengerResponse

    companion object {
        @JvmStatic
        operator fun invoke(preference: UserPreference): TerbanginApiServices {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor { chain ->
                        val request =
                            chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer ${preference.getToken()}")
                        chain.proceed(request.build())
                    }
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
