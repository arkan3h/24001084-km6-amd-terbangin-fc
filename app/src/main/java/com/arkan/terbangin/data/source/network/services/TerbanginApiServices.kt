package com.arkan.terbangin.data.source.network.services

import com.arkan.terbangin.BuildConfig
import com.arkan.terbangin.data.model.Response
import com.arkan.terbangin.data.source.network.model.airport.AirportData
import com.arkan.terbangin.data.source.network.model.auth.login.LoginData
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPData
import com.arkan.terbangin.data.source.network.model.auth.otp.verify_otp.VerifyOTPData
import com.arkan.terbangin.data.source.network.model.auth.register.RegisterData
import com.arkan.terbangin.data.source.network.model.auth.resetpassword.ResetPasswordResponse
import com.arkan.terbangin.data.source.network.model.booking2.BookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking2.BookingPayload
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingDataResponse
import com.arkan.terbangin.data.source.network.model.booking2.HelperBookingPayload
import com.arkan.terbangin.data.source.network.model.flight.FlightDataResponse
import com.arkan.terbangin.data.source.network.model.history.HelperBookingResponse
import com.arkan.terbangin.data.source.network.model.notification.NotificationData
import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponseData
import com.arkan.terbangin.data.source.network.model.payment.PaymentData
import com.arkan.terbangin.data.source.network.model.profile.ProfileData
import com.arkan.terbangin.data.source.network.model.seat.SeatData
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
    ): Response<RegisterData?>

    @Multipart
    @POST("/api/v1/auth/login")
    suspend fun doLogin(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
    ): Response<LoginData?>

    @Multipart
    @POST("/api/v1/verification/generate-link")
    suspend fun doResetPassword(
        @Part("email") email: RequestBody,
    ): ResetPasswordResponse

    @Multipart
    @POST("/api/v1/verification/generate-otp-email")
    suspend fun requestOTP(
        @Part("email") email: RequestBody,
    ): Response<RequestOTPData?>

    @Multipart
    @POST("api/v1/verification/verify-otp")
    suspend fun verifyOTP(
        @Part("email") email: RequestBody,
        @Part("otp") otp: RequestBody,
    ): Response<VerifyOTPData?>

    @GET("/api/v1/profile/id/{id}")
    suspend fun getProfile(
        @Path("id") id: String?,
    ): Response<ProfileData?>

    @Multipart
    @PATCH("/api/v1/profile/id/{id}")
    suspend fun updateProfile(
        @Path("id") id: String?,
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part picture: MultipartBody.Part?,
    ): Response<ProfileData?>

    @DELETE("/api/v1/profile/id/{id}")
    suspend fun deleteProfile(
        @Path("id") id: String,
    ): Response<ProfileData?>

    @GET("/api/v1/flight/flightfilter")
    suspend fun getAllFlight(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("key") key: String,
        @Query("value") date: String,
        @Query("filter") filter: String,
        @Query("order") order: String,
        @Query("seatType") seatType: String,
    ): Response<List<FlightDataResponse>?>

    @GET("/api/v1/flight/continent")
    suspend fun getFlightContinent(
        @Query("value") date: String,
        @Query("continent") continent: String,
    ): Response<List<FlightDataResponse>?>

    @GET("/api/v1/airport")
    suspend fun getAllAirport(): Response<List<AirportData>?>

    @POST("/api/v1/passanger")
    suspend fun createPassenger(
        @Body payload: PassengerPayload,
    ): Response<PassengerResponseData?>

    @GET("/api/v1/notification/user/{id}")
    suspend fun getNotificationByUID(
        @Path("id") id: String?,
    ): Response<List<NotificationData>?>

    @GET("/api/v1/seat/seat-with-flight-id/{id}")
    suspend fun getSeat(
        @Path("id") id: String?,
    ): Response<List<SeatData>?>

    @GET("/api/v1/notification/{id}")
    suspend fun getDetailNotification(
        @Path("id") id: String?,
    ): Response<NotificationData?>

    @Multipart
    @POST("/api/v1/payment/")
    suspend fun createPayment(
        @Part("totalPrice") totalPrice: Int,
    ): Response<PaymentData?>

    @PATCH("/api/v1/payment/id/{id}")
    suspend fun updatePayment(
        @Path("id") id: String?,
    ): Response<PaymentData?>

    @POST("/api/v1/booking")
    suspend fun createBooking(
        @Body payload: BookingPayload,
    ): Response<BookingDataResponse?>

    @POST("/api/v1/helper-booking")
    suspend fun createHelperBooking(
        @Body payload: HelperBookingPayload,
    ): Response<HelperBookingDataResponse?>

    @GET("/api/v1/helper-booking/user/{id}")
    suspend fun getHelperBookingById(
        @Path("id") id: String,
    ): HelperBookingResponse

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
